/*
 * Copyright (c) 2023-2025 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file dokka-plugins.dokka.gradle.kts is part of dokka-plugins
 * Last modified on 06-03-2025 07:54 p.m.
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
 * KT-FUZZY IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.time.Year
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
import org.jetbrains.dokka.gradle.tasks.DokkaGenerateTask

plugins {
    id("org.jetbrains.dokka")
    id("ca.solo-studios.nyx")
    id("pl.allegro.tech.build.axion-release")
}

nyx {
    compile {
        withJavadocJar()
    }
}

val rootDokkaDirectory = rootProject.projectDir.resolve("dokka")
val dokkaDirectories = if (project.rootProject != project)
    listOf(rootDokkaDirectory, project.projectDir.resolve("dokka"))
else
    listOf(rootDokkaDirectory)

val processDokkaIncludes by tasks.register<ProcessResources>("processDokkaIncludes") {
    val projectInfo = mapOf(
            "group" to nyx.info.group,
            "module" to nyx.info.module.get(),
            "version" to nyx.info.version
                           )

    inputs.properties(projectInfo)

    from(dokkaDirectories.map { it.resolve("includes") }) {
        expand("project" to projectInfo)
    }

    into(layout.buildDirectory.file("dokka/includes"))
    group = JavaBasePlugin.DOCUMENTATION_GROUP
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
            remoteUrl = nyx.info.repository.projectUrl.map { uri("$it/blob/${scmVersion.scmPosition.revision}/src") }
            remoteLineSuffix = "#L"
        }
    }

    pluginsConfiguration {
        html {
            homepageLink = nyx.info.repository.projectUrl
            footerMessage = "© ${Year.now()} Copyright solo-studios"
            separateInheritedMembers = false
        }
    }
}

tasks {
    withType<DokkaGenerateTask>().configureEach {
        inputs.files(dokkaDirectories)

        dependsOn(processDokkaIncludes)
    }
}
