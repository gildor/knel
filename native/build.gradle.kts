plugins {
    id("konan")
}

konanArtifacts {
    library("knel") {
      enableMultiplatform(true)
    }
}

dependencies {
    expectedBy(project(":common"))
}
