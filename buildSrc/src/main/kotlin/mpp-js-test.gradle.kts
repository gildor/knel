import com.moowork.gradle.node.npm.NpmTask
import com.moowork.gradle.node.task.NodeTask
import org.apache.tools.ant.taskdefs.condition.Os
import org.apache.tools.ant.taskdefs.condition.Os.isFamily
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilationToRunnableFiles

plugins {
    id("com.moowork.node")
    kotlin("multiplatform")
}

// Javascript test configuration

// https://github.com/srs/gradle-node-plugin/issues/301
repositories.whenObjectAdded {
    if (this is IvyArtifactRepository) {
        metadataSources {
            artifact()
        }
    }
}

node {
    version = "8.14.0"
    download = true
}

kotlin {
    targets {
        js {}
    }
}

dependencies {
    "jsTestImplementation"("org.jetbrains.kotlin:kotlin-test-js")
}

val jsCompilations = kotlin.targets["js"].compilations

tasks {
    val installMocha by registering(NpmTask::class) {
        setArgs(listOf(
            "install", "mocha@5.2.0",
            //publish.gradle.kts for some reason node.nodeModulesDir doesn't work
            "--prefix", "$buildDir"
        ))
    }

    val populateNodeModules by registering {
        doLast {
            copy {
                from("$buildDir/npm/node_modules")
                from(jsCompilations["main"].output.allOutputs)
                (jsCompilations["test"] as KotlinCompilationToRunnableFiles).runtimeDependencyFiles.forEach {
                    if (it.exists() && !it.isDirectory) {
                        from(zipTree(it.absolutePath).matching { include("*.js") })
                    }
                    into("$buildDir/node_modules")
                }
            }
        }
    }

    val runMocha by registering(NodeTask::class) {
        dependsOn(jsCompilations["test"].compileKotlinTaskName, installMocha, populateNodeModules)
        setScript(file("$buildDir/node_modules/mocha/bin/mocha"))
        setArgs(
            listOf(
                "--timeout",
                "15000",
                relativePath("${jsCompilations["test"].output.classesDirs.first()}/${project.name}_test.js")
            )
        )
    }

    // Only run JS tests if not in windows
    if (!isFamily(Os.FAMILY_WINDOWS)) {
        named("jsTest") {
            dependsOn(runMocha)
        }
    }
}
