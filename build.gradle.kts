plugins {
	kotlin("jvm") version "1.8.0"
	application
}

group = "org.example"
version = "0.0.0-DEV"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.json:json:20230227")
	testImplementation(kotlin("test"))
}

tasks.test {
	useJUnitPlatform()
}

kotlin {
	jvmToolchain(11)
}

application {
	mainClass.set("MainKt")
}