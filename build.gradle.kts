plugins {
    java
    id("io.quarkus")
    kotlin("jvm")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-smallrye-jwt")
    implementation("io.quarkus:quarkus-smallrye-jwt-build")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("io.quarkus:quarkus-arc")
//    implementation("io.quarkus:quarkus-jdbc-h2")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.quarkus:quarkus-security")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.nimbusds:nimbus-jose-jwt:9.15")
    implementation("io.quarkus:quarkus-elytron-security-jdbc")
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}
kotlin {
    jvmToolchain(17)
}