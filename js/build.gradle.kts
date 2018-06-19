import com.moowork.gradle.node.npm.NpmTask
import com.moowork.gradle.node.task.NodeTask
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import java.io.File

plugins {
    id("kotlin-platform-js")
    id("com.moowork.node") version "1.2.0"

}

val compileKotlin2Js: Kotlin2JsCompile by tasks
compileKotlin2Js.kotlinOptions {
    outputFile = "$buildDir/node_modules/knel.js"
}
val compileTestKotlin2Js: Kotlin2JsCompile by tasks

project.convention.findPlugin(BasePluginConvention::class.java)?.archivesBaseName

tasks.withType<Kotlin2JsCompile> {
    kotlinOptions {
        moduleKind = "umd"
        sourceMap = true
        metaInfo = true
    }
}

dependencies {
    compile(kotlin("stdlib-js"))
    expectedBy(project(":common"))
    testCompile(kotlin("test-js"))
}


val populateNodeModules = task<Copy>("populateNodeModules") {
    dependsOn(compileKotlin2Js)
    from(compileKotlin2Js.destinationDir)

    configurations.testCompile.forEach {
        from(zipTree(it.absolutePath).matching { include("*.js") })
    }

    into("$buildDir/node_modules")
}

node {
    version = "4.7.2"
//    download = true
}

val installMocha = task<NpmTask>("installMocha") {
    setArgs(listOf("install", "mocha"))
}

val runMocha = task<NodeTask>("runMocha") {
    dependsOn(compileTestKotlin2Js, populateNodeModules, installMocha)
    setScript(file("node_modules/mocha/bin/mocha"))
    setArgs(listOf(compileTestKotlin2Js.outputFile))
}

tasks["test"].dependsOn(runMocha)

val sourcesJar = task<Jar>("sourcesJar") {
    classifier = "sources"
    //TODO
    //from(sourceSets.main.kotlin)
}

//TODO
//artifacts {
//    add(sourcesJar)
//}