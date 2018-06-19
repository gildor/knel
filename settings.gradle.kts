rootProject.name = "knel"

enableFeaturePreview("GRADLE_METADATA")

pluginManagement {
    repositories {
        gradlePluginPortal()
        jcenter()
        maven("https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
    }
    // This way we can map plugin to standard maven artifact
    // for maven repositories without plugin descriptor
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("kotlin-platform")) {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.50")
            }
            if (requested.id.id == "konan") {
                useModule("org.jetbrains.kotlin:kotlin-native-gradle-plugin:0.7.1")
            }
        }
    }
}

include(":common")
include(":jvm")
include(":js")
include(":native")