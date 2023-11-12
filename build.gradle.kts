import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.20"
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
    implementation("org.litote.kmongo:kmongo:4.10.0")

    implementation("com.xenomachina:kotlin-argparser:2.0.7")
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    exclude(
        "**/*.kotlin_metadata",
        "**/*.kotlin_builtins",
        "META-INF/"
    )

    archiveFileName.set(
        "scala-banresetter.jar"
    )
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}
