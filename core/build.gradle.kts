version = "0.1.1"

plugins {
    id("java-library")
}

dependencies {
    api(project(":api"))
    maven(mavenCentral(), "org.graalvm.js:js:23.0.0")
    maven(mavenCentral(), "org.graalvm.regex:regex:23.0.0")
    maven(mavenCentral(), "org.graalvm.truffle:truffle-api:23.0.0")
    maven(mavenCentral(), "org.graalvm.sdk:graal-sdk:23.0.0")
    maven(mavenCentral(), "com.ibm.icu:icu4j:72.1")
    maven(mavenCentral(), "commons-io:commons-io:2.13.0")

    // If you want to use external libraries, you can do that here.
    // The dependencies that are specified here are loaded into your project but will also
    // automatically be downloaded by labymod, but only if the repository is public.
    // If it is private, you have to add and compile the dependency manually.
    // You have to specify the repository, there are getters for maven central and sonatype, every
    // other repository has to be specified with their url. Example:
    // maven(mavenCentral(), "org.apache.httpcomponents:httpclient:4.5.13")
}

labyModProcessor {
    referenceType = net.labymod.gradle.core.processor.ReferenceType.DEFAULT
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}