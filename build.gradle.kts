plugins {
    id("java")
    id("org.springframework.boot") version "2.7.10"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("io.qameta.allure") version "2.11.2"
    id("net.saliman.properties") version "1.5.2"
    id("ru.vyarus.mkdocs") version "3.0.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java", "src-c4/main/java")
        }
    }
    test {
        java {
            srcDirs("src/test/java", "src-at/test/java")
        }
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
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(kotlin("stdlib-jdk8"))
}

allure {
    adapter.aspectjWeaver.set(true)
    adapter.autoconfigure.set(true)
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}

mkdocs {
    sourcesDir = "$rootDir"
}
