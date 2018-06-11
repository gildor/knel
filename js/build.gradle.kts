plugins {
    id("kotlin-platform-js")
}

dependencies {
    compile(kotlin("stdlib-js"))
    expectedBy(project(":common"))
    testCompile(kotlin("test-junit"))
    testCompile(kotlin("test-js"))
}

