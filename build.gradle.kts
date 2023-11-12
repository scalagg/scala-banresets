import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

group = "gg.tropic.banresets"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("org.litote.kmongo:kmongo-serialization:4.10.0")

    implementation("com.xenomachina:kotlin-argparser:2.0.7")
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    archiveFileName.set(
        "scala-banresetter.jar"
    )
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}
