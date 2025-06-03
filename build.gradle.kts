plugins {
    kotlin("jvm") version "2.1.20"
    application
}

group = "dev.jamiecraane.vibe"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

val composeVersion = "1.6.0"

dependencies {
    // Compose dependencies
    implementation("org.jetbrains.compose.ui:ui-desktop:$composeVersion")
    implementation("org.jetbrains.compose.foundation:foundation:$composeVersion")
    implementation("org.jetbrains.compose.material:material:$composeVersion")

    // PDF generation library
    implementation("org.apache.pdfbox:pdfbox:2.0.27")

    // Kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Testing
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("dev.jamiecraane.vibe.compose.pdf.MainKt")
}
