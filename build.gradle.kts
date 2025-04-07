plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.2.0"
}

group = "ru.TanaxXt"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(23)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}


openApiGenerate {
    generatorName.set("java")
    inputSpec.set("$rootDir/openApi.yaml") // Файл OpenAPI
    outputDir.set("$buildDir/generated") // Папка для генерации
    apiPackage.set("ru.tanaxxt.currencysystem.api") // Пакет для API-интерфейсов
    modelPackage.set("ru.tanaxxt.currencysystem.model") // Пакет для моделей
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "serializationLibrary" to "gson" // Можно поменять на "moshi" или "jackson"
        )
    )
}