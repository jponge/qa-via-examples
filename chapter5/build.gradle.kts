import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  kotlin("jvm") version "1.3.61"
}

repositories {
  jcenter()
  maven {
    url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
  }
}

dependencies {
  implementation("io.vertx:vertx-core:3.9.2-SNAPSHOT")
  implementation("io.vertx:vertx-rx-java2:3.9.2-SNAPSHOT")
  implementation("io.vertx:vertx-web-client:3.9.2-SNAPSHOT")

  implementation("io.vertx:vertx-lang-kotlin:3.9.2-SNAPSHOT")
  implementation("io.vertx:vertx-lang-kotlin-coroutines:3.9.2-SNAPSHOT")
  implementation(kotlin("stdlib-jdk8"))

  implementation("ch.qos.logback:logback-classic:1.2.3")
}

tasks.create<JavaExec>("run") {
  main = project.properties.getOrDefault("mainClass", "chapter5.callbacks.Main") as String
  classpath = sourceSets["main"].runtimeClasspath
  systemProperties["vertx.logger-delegate-factory-class-name"] = "io.vertx.core.logging.SLF4JLogDelegateFactory"
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.8"

tasks.wrapper {
  gradleVersion = "6.3"
}
