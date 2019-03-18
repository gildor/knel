plugins {
    kotlin("multiplatform")
    id("com.jfrog.bintray") version "1.8.4"
    `mpp-js-test`
}

group = "ru.gildor.knel"
version = 0.3

repositories {
    jcenter()
}

kotlin {
    targets {
        jvm()
        js {
            compilations.all {
                kotlinOptions {
                    sourceMap = true
                    metaInfo = true
                    moduleKind = "umd"
                }
            }
        }
        iosArm64()
        macosX64()
        mingwX64()
        linuxX64()
    }
}

dependencies {
    commonMainImplementation("org.jetbrains.kotlin:kotlin-stdlib")
    commonTestImplementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
    commonTestImplementation("org.jetbrains.kotlin:kotlin-test-common")

    "jvmTestImplementation"("org.jetbrains.kotlin:kotlin-test-junit")

    "jsMainImplementation"("org.jetbrains.kotlin:kotlin-stdlib-js")
}
