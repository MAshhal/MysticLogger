plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "com.mystic.util.prettylogger"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

}

val mockitoAgent = configurations.create("mockitoAgent")

dependencies {
    implementation(libs.androidx.annotation)

    testImplementation(libs.junit)
    testImplementation(libs.truth)
    testImplementation(libs.mockito)
    testImplementation(libs.mockk)
    testImplementation(libs.roboelectric)
    // https://mvnrepository.com/artifact/org.json/json
    testImplementation(libs.json)


    mockitoAgent(libs.mockito) { isTransitive = false }
}

tasks.withType<Test> { jvmArgs("-javaagent:${mockitoAgent.asPath}") }

publishing {
    repositories {
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/mashhal/mysticlogger")
            // username and password (a personal Github access token) should be specified as
            // `githubPackagesUsername` and `githubPackagesPassword` Gradle properties or alternatively
            // as `ORG_GRADLE_PROJECT_githubPackagesUsername` and `ORG_GRADLE_PROJECT_githubPackagesPassword`
            // environment variables
            credentials(PasswordCredentials::class)
        }
    }
}

mavenPublishing {
    coordinates("io.github.mashhal", "mystic-logger", "1.0.0")
}