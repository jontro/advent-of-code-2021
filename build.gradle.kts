plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.30"
    idea
    application
    kotlin("plugin.serialization") version "1.4.0"
}

repositories {

    mavenCentral()
    mavenLocal()

    maven("https://oss.jfrog.org/artifactory/oss-snapshot-local")
   
}
dependencies {
    implementation(kotlin("stdlib", org.jetbrains.kotlin.config.KotlinCompilerVersion.VERSION))
    

}
val main_class by extra( "AppKt")

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "${JavaVersion.VERSION_11}"
}
tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = "${JavaVersion.VERSION_11}"
}
application {
    mainClassName = main_class
}