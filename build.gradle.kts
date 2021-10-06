import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.2.5.RELEASE"
  id("io.spring.dependency-management") version "1.0.9.RELEASE"
  id("org.jlleitschuh.gradle.ktlint") version "9.1.1"
  id("com.google.cloud.tools.jib") version "2.1.0"
  kotlin("jvm") version "1.3.61"
  kotlin("plugin.spring") version "1.3.61"
}

group = "commonmarvel.githubmergebot"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
  mavenCentral()
  maven("https://dl.bintray.com/puni-tw/maven")
}

apply(plugin = "org.jlleitschuh.gradle.ktlint")

dependencies {
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("com.squareup.okhttp3:okhttp:4.1.0")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}

jib {
  from {
    image = "openjdk:11-jre-slim"
  }
  to {
    image = "registry.hub.docker.com/commonmarvel/${project.name}:latest"
  }
  container {
    creationTime = "USE_CURRENT_TIMESTAMP"
    mainClass = "commonmarvel.githubmergebot.GithubMergeBotApplicationKt"
    jvmFlags = listOf(
      "-Xms512m",
      "-Xmx512m"
    )
  }
  setAllowInsecureRegistries(true)
}

ktlint {
  enableExperimentalRules.set(true)
}
