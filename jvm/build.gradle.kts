plugins {
    id("kotlin-platform-jvm")
}

dependencies {
    compile(kotlin("stdlib"))
    expectedBy(project(":common"))
    testCompile(kotlin("test"))
    testCompile(kotlin("test-junit"))
}
