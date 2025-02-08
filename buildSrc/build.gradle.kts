/*
 * Copyright (c) 2023-2025 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file build.gradle.kts is part of dokka-plugins
 * Last modified on 06-03-2025 08:16 p.m.
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

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    // for kotlin-dsl plugin
    gradlePluginPortal()

    maven("https://maven.solo-studios.ca/releases/") {
        name = "Solo Studios"
    }
    maven("https://maven.solo-studios.ca/snapshots/") {
        name = "Solo Studios"
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
        languageVersion = KotlinVersion.KOTLIN_2_0
        apiVersion = KotlinVersion.KOTLIN_2_0
    }
}

dependencies {
    implementation(libs.dokka.base)

    implementation(gradlePlugin(libs.plugins.dokka, libs.versions.dokka))
    implementation(gradlePlugin(libs.plugins.kotlin.jvm, libs.versions.kotlin))

    implementation(gradlePlugin(libs.plugins.ksp, libs.versions.ksp.asProvider()))

    implementation(gradlePlugin(libs.plugins.axion.release, libs.versions.axion.release))

    implementation(gradlePlugin(libs.plugins.nyx, libs.versions.nyx))

    // https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(kotlin("script-runtime"))
}

fun gradlePlugin(id: Provider<PluginDependency>, version: Provider<String>): String {
    val pluginId = id.get().pluginId
    return "$pluginId:$pluginId.gradle.plugin:${version.get()}"
}
