import org.gradle.internal.impldep.org.apache.maven.Maven

plugins {
    kotlin("multiplatform") version "1.3.41"
    `maven-publish`
}

group = "ru.gildor.knel"
version = 0.3

repositories {
    mavenCentral()
}

kotlin {
    targets {
        jvm()
        js {
            browser()
            nodejs()
        }
        linuxX64()
        linuxArm32Hfp()
        macosX64()
        mingwX64()
        iosArm64()
        iosArm32()
        iosX64()
    }
}

dependencies {
    commonMainImplementation("org.jetbrains.kotlin:kotlin-stdlib")
    commonTestImplementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
    commonTestImplementation("org.jetbrains.kotlin:kotlin-test-common")

    "jvmMainImplementation"("org.jetbrains.kotlin:kotlin-stdlib")
    "jvmTestImplementation"("org.jetbrains.kotlin:kotlin-test-junit")

    "jsMainImplementation"("org.jetbrains.kotlin:kotlin-stdlib-js")
    "jsTestImplementation"("org.jetbrains.kotlin:kotlin-test-js")
}

publishing {
    repositories {
        val bintrayUser: String? by project
        bintrayUser ?: return@repositories
        val bintrayKey: String by project
        val bintrayRepo: String by project
        val bintrayProject = project.name
        val bintrayAutoRelease: Boolean? by project
        maven {
            name = "bintray"
            url = uri("https://api.bintray.com/maven/$bintrayUser/$bintrayRepo/$bintrayProject;publish=${if (bintrayAutoRelease == true) 1 else 0}")
            credentials {
                username = bintrayUser
                password = bintrayKey
            }
        }
    }
}

