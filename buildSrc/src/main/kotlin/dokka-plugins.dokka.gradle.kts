/*
 * Copyright (c) 2023-2023 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file dokka-plugins.dokka.gradle.kts is part of dokka-plugins
 * Last modified on 15-09-2023 09:50 p.m.
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
import org.jetbrains.dokka.DokkaConfiguration.Visibility
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.AbstractDokkaTask
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial

plugins {
    id("org.jetbrains.dokka")
}

dependencies {
    dokkaPlugin("ca.solo-studios:dokka-script-plugin:0.1.0-SNAPSHOT")
}

repositories {
    mavenLocal()
}

tasks {
    val rootDokkaDirectory = rootProject.projectDir.resolve("dokka")
    val dokkaDirectories = if (project.rootProject != project)
        listOf(rootDokkaDirectory, project.projectDir.resolve("dokka"))
    else
        listOf(rootDokkaDirectory)

    val processDokkaIncludes by register<ProcessResources>("processDokkaIncludes") {
        from(dokkaDirectories.map { it.resolve("includes") })

        doFirst {
            val projectInfo = ProjectInfo(
                    group = project.group.toStringOrEmpty(),
                    module = project.name,
                    version = project.version.toStringOrEmpty(),
                                         )
            expand("project" to projectInfo)
        }

        into(layout.buildDirectory.file("dokka/includes"))
        group = JavaBasePlugin.DOCUMENTATION_GROUP
    }

    withType<AbstractDokkaTask>().configureEach {
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            footerMessage = "© ${Year.now()} Copyright solo-studios"
            separateInheritedMembers = false
            customStyleSheets = rootDokkaDirectory.resolve("styles").listFiles()?.toList().orEmpty()
            customAssets = rootDokkaDirectory.resolve("assets").listFiles()?.toList().orEmpty()
        }

        moduleName = project.name
        moduleVersion = project.version.toStringOrEmpty()
    }

    withType<DokkaMultiModuleTask>().configureEach {
        if (project.rootProject == project)
            moduleName = project.name.formatAsName()
        else
            isEnabled = false
    }


    withType<DokkaTask>().configureEach {
        dependsOn(processDokkaIncludes)

        inputs.files(dokkaDirectories)

        dokkaSourceSets.configureEach {
            includes.from(processDokkaIncludes.outputs.files.asFileTree)

            reportUndocumented = true
            documentedVisibilities = setOf(Visibility.PUBLIC, Visibility.PROTECTED)
        }
    }

    withType<DokkaTaskPartial>().configureEach {
        dependsOn(processDokkaIncludes)

        inputs.files(dokkaDirectories)

        dokkaSourceSets.configureEach {
            includes.from(processDokkaIncludes.outputs.files.asFileTree)

            reportUndocumented = true
        }
    }
}
