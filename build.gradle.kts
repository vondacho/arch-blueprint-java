import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.springframework.cloud.contract.verifier.config.TestMode

plugins {
    id("java")
    id("org.springframework.boot") version "3.0.6"
    id("org.springframework.cloud.contract") version "3.1.6"
    id("au.com.dius.pact") version "4.5.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("io.qameta.allure-aggregate-report") version "2.11.2"
    id("net.saliman.properties") version "1.5.2"
    id("com.appland.appmap") version "1.1.1"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

sourceSets {
    create("acceptanceTest") {
        java.srcDir("src/acceptanceTest/java")
        resources.srcDir("src/acceptanceTest/resources")
        compileClasspath += sourceSets.main.get().compileClasspath + sourceSets.test.get().compileClasspath
        annotationProcessorPath += sourceSets.test.get().annotationProcessorPath
    }
}

repositories {
    mavenCentral()
}

ext {
    set("allure.version", "2.21.0")
    set("problem.spring.web.version", "0.27.0")
    set("liquibase.version", "4.20.0")
    set("atlassian.validator.version", "2.33.1")
    set("cucumber.version", "7.11.2")
    set("spring.cloud.contract.version", "3.1.6")
    set("embedded-database-spring-test.version", "2.2.0")
    set("junit.platform.version", "5.7.1")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.zalando:problem-spring-web-starter:${property("problem.spring.web.version")}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.liquibase:liquibase-core:${property("liquibase.version")}")
    implementation("com.atlassian.oai:swagger-request-validator-springmvc:${property("atlassian.validator.version")}")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")

    // testing
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "junit", module = "junit")
    }
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("io.zonky.test:embedded-database-spring-test:${property("embedded-database-spring-test.version")}") {
        exclude("net.java.dev.jna", "jna")
    }
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    testRuntimeOnly("org.postgresql:postgresql")
    testImplementation("io.qameta.allure:allure-junit5:${property("allure.version")}")

    // AT testing
    testImplementation("io.cucumber:cucumber-java:${property("cucumber.version")}")
    testImplementation("io.cucumber:cucumber-spring:${property("cucumber.version")}")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:${property("cucumber.version")}")
    testImplementation("io.qameta.allure:allure-cucumber7-jvm:${property("allure.version")}")

    // CDC testing
    testImplementation("au.com.dius.pact.consumer:junit5:4.5.5") {
        exclude("org.apache.groovy", "groovy")
    }
    testImplementation("au.com.dius.pact.provider:junit5:4.5.5") {
        exclude("org.apache.groovy", "groovy")
    }
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier:${property("spring.cloud.contract.version")}")
    testImplementation("org.springframework.cloud:spring-cloud-contract-spec-kotlin:${property("spring.cloud.contract.version")}")
    testImplementation("org.hibernate:hibernate-validator:5.2.5.Final")
}

contracts {
    testMode.set(TestMode.MOCKMVC)
    testFramework.set(org.springframework.cloud.contract.verifier.config.TestFramework.JUNIT5)
    contractsDslDir.set(file("$rootDir/src/contractTest/resources/customer/cdc/spring/contracts"))
    baseClassForTests.set("edu.obya.blueprint.customer.cdc.spring.provider.CustomerVerifierBase")
}

allure {
}

appmap {
    configFile.set(file("$projectDir/appmap.yml"))
    outputDirectory.set(file("$buildDir/appmap"))
    isSkip = false
    debug = "info"
    debugFile.set(file("$buildDir/appmap/agent.log"))
    eventValueSize = 1024
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        }
    }
    contractTest {
        useJUnitPlatform()
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        }
    }
    val acceptanceTest by registering(Test::class) {
        description = "Runs the acceptance tests"
        group = "verification"
        testClassesDirs = sourceSets["acceptanceTest"].output.classesDirs
        classpath += sourceSets["acceptanceTest"].runtimeClasspath
        useJUnitPlatform()
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        }
    }
    named<Copy>("processAcceptanceTestResources") {
        duplicatesStrategy = DuplicatesStrategy.WARN
    }
    check {
        dependsOn(acceptanceTest)
    }
}

