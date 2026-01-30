plugins {
    id("java")
    id("io.qameta.allure") version "3.0.1"
}

group = "guru.qa"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

allure {
    val allureVersion = "2.32.0"

    report {
        version.set(allureVersion)
    }
    adapter {
        aspectjWeaver.set(true)
        frameworks {
            junit5 {
                adapterVersion.set(allureVersion)
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    val csvVersion = "5.12.0"
    val datafakerVersion = "2.5.3"
    val jacksonVersion = "3.0.3"
    val logbackVersion = "1.5.26"
    val pdfTestVersion = "2.1.0"
    val poiVersion = "5.5.1"
    val slf4jVersion = "2.0.17"
    val xlsTestVersion = "1.7.2"
    val allureSelenideVersion = "2.32.0"
    val assertjVersion = "3.27.6"
    val junitVersion = "6.0.2"
    val ownerVersion = "1.0.12"
    val selenideVersion = "7.13.0"
    val webdrivermanagerVersion = "6.3.3"

    implementation("com.opencsv:opencsv:$csvVersion")
    implementation("net.datafaker:datafaker:$datafakerVersion")
    implementation("tools.jackson.core:jackson-databind:$jacksonVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("com.codeborne:pdf-test:$pdfTestVersion")
    implementation("org.apache.poi:poi:$poiVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("com.codeborne:xls-test:$xlsTestVersion")
    testImplementation("io.qameta.allure:allure-selenide:$allureSelenideVersion")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("org.aeonbits.owner:owner:$ownerVersion")
    testImplementation("com.codeborne:selenide:$selenideVersion")
    testImplementation("io.github.bonigarcia:webdrivermanager:$webdrivermanagerVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.withType<JavaExec>().configureEach {
    systemProperty("file.encoding", "UTF-8")
    systemProperty("sun.jnu.encoding", "UTF-8")
    systemProperty("sun.stdout.encoding", "UTF-8")
}

tasks.test {
    useJUnitPlatform()

    val configFile = System.getProperty("config.file")
    if (configFile != null) {
        systemProperty("config.file", configFile)
    }

    val params = setOf(
        "config.file",
        "browser.name",
        "browser.version",
        "browser.size",
        "remote.condition",
        "remote.url",
        "remote.username",
        "remote.password",
        "enable.vnc",
        "enable.video",
        "base.url",
        "timeout",
        "headless"
    )

    val properties = System.getProperties()
        .filter { (key, _) -> params.contains(key.toString()) }
        .map { it.key.toString() to it.value }
        .toMap()

    systemProperties = properties

    testLogging {
        events("started", "skipped", "failed", "standard_error", "standard_out")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.SHORT
    }

}