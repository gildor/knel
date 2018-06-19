plugins {
    id("konan")
}

konanArtifacts {
    library("knel") {
      enableMultiplatform(true)
    }

    program("knelTest") {
        srcDir("src/test/kotlin")
        commonSourceSets("test")
        libraries {
            artifact("knel")
        }
        extraOpts("-tr")
    }
}

tasks.create("test") {
    dependsOn("runKnelTest")
}


dependencies {
    expectedBy(project(":common"))
}
