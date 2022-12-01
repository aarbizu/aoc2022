import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    kotlin("jvm") version "1.6.10"

    // Apply the application plugin to add support for building a CLI application.
    application

    // Apply the idea plugin
    idea

    // spotless
    id("com.diffplug.spotless") version "5.15.0"

    id("com.github.ben-manes.versions") version "0.39.0"
}

repositories {
    maven("https://jitpack.io")
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.google.guava:guava:31.1-jre")
    testImplementation("com.github.stefanbirkner:system-rules:1.19.0")
    testImplementation("com.google.truth:truth:1.1.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testRuntimeOnly("org.junit.platform:junit-platform-console:1.8.2")
    implementation("com.xenomachina:kotlin-argparser:2.0.7")
}

group = "org.aarbizu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_14
java.targetCompatibility = JavaVersion.VERSION_14

tasks {

    compileKotlin {
        kotlinOptions.jvmTarget = "14"
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "14"
    }

}

application {
    // Define the main class for the application.
    mainClass.set("org.aarbizu.aoc2022.MainKt")
}

spotless {
    kotlin {
        ktlint()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events = setOf(PASSED, FAILED, SKIPPED)
    }
}
