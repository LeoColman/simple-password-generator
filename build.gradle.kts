/*
 * Copyright 2022 Leonardo Colman Lopes, Luiz Neves Porto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either press or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.lang.System.getenv

plugins {
  kotlin("jvm") version "1.9.20"
  `maven-publish`
  signing
  id("org.jetbrains.dokka") version "1.9.10"
  id("io.gitlab.arturbosch.detekt").version("1.23.3")
  id("org.jetbrains.kotlinx.kover") version "0.7.4"
}

group = "br.com.colman"
version = getenv("RELEASE_VERSION") ?: "local"

repositories {
  mavenCentral()
}

dependencies {
  // Kotest
  testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
  testImplementation("io.kotest:kotest-property:5.7.2")
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
  useJUnitPlatform()
}

kotlin {
  explicitApi()
}

detekt {
   buildUponDefaultConfig = true
}

val sourcesJar by tasks.registering(Jar::class) {
  archiveClassifier.set("sources")
  from(sourceSets.getByName("main").allSource)
}

val javadocJar by tasks.registering(Jar::class) {
  dependsOn("dokkaHtml")
  archiveClassifier.set("javadoc")
  from("$buildDir/dokka")
}


publishing {
  repositories {

    maven("https://oss.sonatype.org/service/local/staging/deploy/maven2") {
      credentials {
        username = getenv("OSSRH_USERNAME")
        password = getenv("OSSRH_PASSWORD")
      }
    }
  }

  publications {

    register("mavenJava", MavenPublication::class) {
      from(components["java"])
      artifact(sourcesJar.get())
      artifact(javadocJar.get())

      pom {
        groupId = "br.com.colman"
        name.set("simple-password-generator")
        description.set("Simple Password Generator")
        url.set("https://www.github.com/LeoColman/simple-password-generator")


        scm {
          connection.set("scm:git:http://www.github.com/LeoColman/simple-password-generator")
          developerConnection.set("scm:git:http://github.com/LeoColman/simple-password-generator")
          url.set("https://www.github.com/LeoColman/simple-password-generator")
        }

        licenses {
          license {
            name.set("Apache-2.0")
            url.set("https://opensource.org/licenses/Apache-2.0")
          }
        }

        developers {
          developer {
            id.set("LeoColman")
            name.set("Leonardo Colman Lopes")
            email.set("leonardo.dev@colman.com.br")
          }

          developer {
            id.set("LuizPorto")
            name.set("Luiz Neves Porto")
            email.set("dev@luiz.nevesporto.com.br")
          }
        }
      }
    }
  }
}

val signingKey: String? by project
val signingPassword: String? by project

signing {
  useGpgCmd()
  if (signingKey != null && signingPassword != null) {
    useInMemoryPgpKeys(signingKey, signingPassword)
  }

  sign(publishing.publications["mavenJava"])
}
