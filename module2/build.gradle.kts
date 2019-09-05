plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

kotlin {
    targets {
        jvm()
        js()
    }
}

dependencies {
    commonMainImplementation("org.jetbrains.kotlin:kotlin-stdlib")
    "jvmMainImplementation"("org.jetbrains.kotlin:kotlin-stdlib")
}