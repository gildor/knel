/*
import java.nio.file.Files
import java.nio.file.Paths

plugins {
 `maven-publish`
}
//apply plugin: 'com.jfrog.bintray'
//apply from: project.rootProject.file('gradle/pom.gradle')

// Create empty jar for sources classifier to satisfy maven requirements
tasks.register<Jar>("stubSources") {
    archiveClassifier.set() = 'sources'
}

// Create empty jar for javadoc classifier to satisfy maven requirements
task stubJavadoc(type: Jar) {
    archiveClassifier = 'javadoc'
}

// Configure publishing
publishing {
    // TODO: I have no idea about this
    repositories {
        maven {
            url = "https://${project.bintrayOrg}.bintray.com/${project.bintrayRepository}"
        }
    }

    // Process each publication we have in this project
    publications.all { publication ->
        // apply changes to pom.xml files, see pom.gradle
        pom.withXml(configureMavenCentralMetadata)

        if (publication.name == 'kotlinMultiplatform') {
            // for our root metadata publication, set artifactId with a package and project name
            publication.artifactId = "$bintrayPackage-$project.name"
        } else {
            // for targets, set artifactId with a package, project name and target name (e.g. iosX64)
            publication.artifactId = "$bintrayPackage-$project.name-$publication.name"
        }
    }
}*/
