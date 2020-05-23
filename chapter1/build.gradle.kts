plugins {
  java
  application
}

repositories {
  mavenCentral()
  maven {
    url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
  }
}

dependencies {
  implementation("io.vertx:vertx-core:3.9.2-SNAPSHOT")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
}

application {
  mainClassName = "chapter1.firstapp.VertxEcho"
}

tasks.wrapper {
  gradleVersion = "6.3"
}
