plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    jcenter()
}

dependencies {
    "implementation"("org.jetbrains.kotlin.multiplatform:org.jetbrains.kotlin.multiplatform.gradle.plugin:1.3.21")
    "implementation"("com.moowork.node:com.moowork.node.gradle.plugin:1.2.0")
}