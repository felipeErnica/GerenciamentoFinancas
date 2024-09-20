plugins {
    id("java")
}

group = "br.com.santacarolina"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

configurations.all() {
    resolutionStrategy {
        dependencySubstitution {
            substitute(module("org.eclipse.platform:org.eclipse.swt.\${osgi.platform}:3.127.0"))
                    .using(module("org.eclipse.platform:org.eclipse.swt.win32.win32.x86_64:3.127.0"))
        }
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.logging.log4j:log4j-api:2.24.0")
    implementation("org.apache.logging.log4j:log4j-core:2.24.0")
    implementation("net.coderazzi:tablefilter-swing:5.5.4")
    implementation("org.netbeans.external:AbsoluteLayout:RELEASE220")
    implementation("com.formdev:flatlaf:3.5.1")
    implementation("com.formdev:flatlaf-extras:3.5.1")
    implementation("com.formdev:flatlaf-fonts-roboto:2.137")
    runtimeOnly("com.github.weisj:jsvg:1.6.1")
    implementation("com.fasterxml.woodstox:woodstox-core:7.0.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.17.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("org.swinglabs:swingx:1.6.1")
    implementation("org.eclipse.platform:org.eclipse.swt.win32.win32.x86_64:3.127.0")
    implementation("com.miglayout:miglayout-swing:11.4.2")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("com.github.steos:jnafilechooser:1.1.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
