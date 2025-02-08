/*
 * Copyright (c) 2023-2025 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file build.gradle.kts is part of dokka-plugins
 * Last modified on 06-03-2025 06:13 p.m.
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

plugins {
    `dokka-plugins`.versioning
    `dokka-plugins`.repositories
    `dokka-plugins`.compilation
    `dokka-plugins`.ksp.service
    `dokka-plugins`.tasks
    `dokka-plugins`.publishing
    `dokka-plugins`.dokka
}

nyx.info {
    description = """
        A dokka plugin that adds various style tweaks to dokka.
    """.trimIndent()
}

dependencies {
    implementation(libs.kotlin.stdlib)

    compileOnly(libs.dokka.core)
    implementation(libs.dokka.base)

    implementation(libs.freemarker)

    testImplementation(kotlin("test-junit"))
    testImplementation(libs.dokka.test.api)
    testImplementation(libs.dokka.test.utils)
}
