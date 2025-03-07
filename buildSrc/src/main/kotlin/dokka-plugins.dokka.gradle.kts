/*
 * Copyright (c) 2023-2025 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file dokka-plugins.dokka.gradle.kts is part of dokka-plugins
 * Last modified on 06-03-2025 11:35 p.m.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * DOKKA-PLUGINS IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import com.sass_lang.embedded_protocol.OutputStyle
import groovy.text.SimpleTemplateEngine
import io.freefair.gradle.plugins.sass.SassCompile
import java.io.StringWriter
import java.time.Year
import org.apache.tools.ant.filters.ReplaceTokens
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
import org.jetbrains.dokka.gradle.tasks.DokkaGenerateTask

plugins {
    id("org.jetbrains.dokka")
    id("ca.solo-studios.nyx")
    id("pl.allegro.tech.build.axion-release")
    id("io.freefair.sass-base")
}

nyx {
    compile {
        withJavadocJar()
    }
}

sass {
    omitSourceMapUrl = true
    outputStyle = OutputStyle.COMPRESSED
    sourceMapContents = false
    sourceMapEmbed = false
    sourceMapEnabled = false
}

val rootDokkaDirectory = rootProject.projectDir.resolve("dokka")
val dokkaDirectories = if (project.rootProject != project)
    listOf(rootDokkaDirectory, project.projectDir.resolve("dokka"))
else
    listOf(rootDokkaDirectory)
val dokkaBuildDir = dokka.basePublicationsDirectory

val processDokkaIncludes by tasks.register<ProcessResources>("processDokkaIncludes") {
    val projectInfo = mapOf(
            "group" to nyx.info.group,
            "module" to nyx.info.module.get(),
            "version" to nyx.info.version
                           )

    inputs.properties(projectInfo)

    from(dokkaDirectories.map { it.resolve("includes") }) {
        exclude { it.name.startsWith("_") }

        val dependencyInformation = processTemplate(rootDokkaDirectory.resolve("includes/_dependency.md"), mapOf("project" to projectInfo))
        filter<ReplaceTokens>(
                "tokens" to mapOf("dependencies" to dependencyInformation),
                "beginToken" to "{{",
                "endToken" to "}}"
                             )
    }

    into(dokkaBuildDir.dir("includes"))
    group = JavaBasePlugin.DOCUMENTATION_GROUP
}

val compileDokkaSass by tasks.registering(SassCompile::class) {
    group = BasePlugin.BUILD_GROUP
    source = fileTree(rootDokkaDirectory.resolve("styles"))
    destinationDir = dokkaBuildDir.dir("styles")
}

dokka {
    moduleName = nyx.info.name
    moduleVersion = nyx.info.version
    dokkaSourceSets.configureEach {
        includes.from(processDokkaIncludes.outputs.files.asFileTree)
        reportUndocumented = true
        documentedVisibilities = setOf(VisibilityModifier.Public, VisibilityModifier.Protected)

        sourceLink {
            localDirectory = projectDir.resolve("src")
            remoteUrl = nyx.info.repository.projectUrl.map {
                uri("$it/blob/${scmVersion.scmPosition.revision}/${projectDir.relativeTo(rootDir).invariantSeparatorsPath}/src")
            }
            remoteLineSuffix = "#L"
        }
    }

    pluginsConfiguration {
        html {
            homepageLink = nyx.info.repository.projectUrl
            footerMessage = "© ${Year.now()} Copyright solo-studios"
            separateInheritedMembers = false
            customStyleSheets.from(fileTree(compileDokkaSass.flatMap { it.destinationDir }))
        }
    }
}

tasks {
    withType<DokkaGenerateTask>().configureEach {
        inputs.files(dokkaBuildDir.dir("styles"), dokkaDirectories)

        dependsOn(compileDokkaSass, processDokkaIncludes)
    }
}

fun processTemplate(templateFile: File, templateProperties: Map<String, Any?>): String {
    val engine = SimpleTemplateEngine()
    val template = engine.createTemplate(templateFile)
    val writer = StringWriter()

    // SimpleTemplateEngine expects to be able to mutate the map internally.
    template.make(templateProperties.toMutableMap())
            .writeTo(writer)
    return writer.toString()
}
