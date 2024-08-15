/*
 * Copyright (c) 2024-2024 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file Jenkinsfile is part of dokka-plugins
 * Last modified on 15-08-2024 03:59 p.m.
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

pipeline {
    agent any

    tools {
        jdk 'Temurin Java 8'
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Setup Gradle') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew'
            }
        }

        stage('Build dokka-script-plugin') {
            steps {
                withGradle {
                    sh './gradlew :dokka-script-plugin:build'
                }
            }

            post {
                success {
                    archiveArtifacts artifacts: 'dokka-script-plugin/build/libs/*.jar', fingerprint: true, onlyIfSuccessful: true

                    javadoc javadocDir: 'dokka-script-plugin/build/dokka/html/', keepAll: true
                }
            }
        }

        stage('Build dokka-style-tweaks-plugin') {
            steps {
                withGradle {
                    sh './gradlew :dokka-style-tweaks-plugin:build'
                }
            }

            post {
                success {
                    archiveArtifacts artifacts: 'dokka-style-tweaks-plugin/build/libs/*.jar', fingerprint: true, onlyIfSuccessful: true

                    javadoc javadocDir: 'dokka-style-tweaks-plugin/build/dokka/html/', keepAll: true
                }
            }
        }

        stage('Deploy to Snapshots Repositories') {
            when {
                not {
                    buildingTag()
                }
            }

            steps {
                withCredentials([
                        string(credentialsId: 'maven-signing-key', variable: 'ORG_GRADLE_PROJECT_signingKey'),
                        // string(credentialsId: 'maven-signing-key-id', variable: 'ORG_GRADLE_PROJECT_signingKeyId'),
                        string(credentialsId: 'maven-signing-key-password', variable: 'ORG_GRADLE_PROJECT_signingPassword'),
                        usernamePassword(
                                credentialsId: 'solo-studios-maven',
                                passwordVariable: 'ORG_GRADLE_PROJECT_SoloStudiosPassword',
                                usernameVariable: 'ORG_GRADLE_PROJECT_SoloStudiosUsername'
                        )
                ]) {
                    withGradle {
                        sh './gradlew publishAllPublicationsToSoloStudiosRepository'
                    }
                }
            }

        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
