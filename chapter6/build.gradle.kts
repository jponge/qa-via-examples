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
  implementation("io.vertx:vertx-core:3.9.4-SNAPSHOT")
  implementation("io.vertx:vertx-codegen:3.9.4-SNAPSHOT")
  implementation("io.vertx:vertx-service-proxy:3.9.4-SNAPSHOT")

  annotationProcessor("io.vertx:vertx-service-proxy:3.9.4-SNAPSHOT")
  annotationProcessor("io.vertx:vertx-codegen:3.9.4-SNAPSHOT:processor")

  implementation("io.vertx:vertx-rx-java2:3.9.4-SNAPSHOT")
  annotationProcessor("io.vertx:vertx-rx-java2-gen:3.9.4-SNAPSHOT")

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
  testImplementation("io.vertx:vertx-junit5:3.9.4-SNAPSHOT")
  testImplementation("org.assertj:assertj-core:3.11.1")

  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.1")
}

tasks.named<Test>("test") {
  useJUnitPlatform()
}

tasks.getByName<JavaCompile>("compileJava") {
  options.annotationProcessorGeneratedSourcesDirectory = File("$projectDir/src/main/generated")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
}

application {
  mainClassName = "chapter6.RxProxyClient"
}

tasks.wrapper {
  gradleVersion = "6.6.1"
}
