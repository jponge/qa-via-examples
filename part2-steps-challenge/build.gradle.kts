import com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA

plugins {
  id("com.github.ben-manes.versions") version "0.29.0"
  id("com.adarshr.test-logger") version "2.1.0"
  id("com.github.johnrengelman.shadow") version "6.0.0" apply false
  id("com.moowork.node") version "1.3.1" apply false
  id("org.gradle.test-retry") version "1.1.9" apply false
}

allprojects {
  extra["vertxVersion"] = if (project.hasProperty("vertxVersion")) project.property("vertxVersion") else "3.9.5-SNAPSHOT"
  extra["junit5Version"] = "5.6.1"
  extra["restAssuredVersion"] = "4.3.1"
  extra["logbackClassicVersion"] = "1.2.3"
  extra["assertjVersion"] = "3.17.0"
  extra["testContainersVersion"] = "1.14.3"
}

subprojects {

  repositories {
    mavenCentral()
    maven {
      url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
  }

  apply(plugin = "java")
  apply(plugin = "application")
  apply(plugin = "com.github.johnrengelman.shadow")
  apply(plugin = "com.adarshr.test-logger")

  tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
  }

  tasks.named<Test>("test") {
    reports.html.isEnabled = false
  }

  testlogger {
    theme = MOCHA
    slowThreshold = 5000
    showStandardStreams = true
    showFullStackTraces = true
  }
}

tasks.register<TestReport>("testReport") {
  description = "Aggregate all test results as a HTML report"
  group = "Documentation"
  destinationDir = file("$buildDir/reports/allTests")
  reportOn(subprojects.map { it.tasks["test"] })
}

tasks.wrapper {
  distributionType = Wrapper.DistributionType.ALL
  gradleVersion = "6.6.1"
}
