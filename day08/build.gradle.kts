plugins {
    kotlin("jvm") version "1.7.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.20")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.5.4")
    testImplementation("io.kotest:kotest-assertions-json-jvm:5.5.4")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.5.4")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
