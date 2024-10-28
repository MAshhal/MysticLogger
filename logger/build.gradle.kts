import com.android.build.gradle.internal.scope.getDirectories

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
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
    testImplementation(libs.roboelectric)

    mockitoAgent(libs.mockito) { isTransitive = false }
}

tasks.withType<Test> { jvmArgs("-javaagent:${mockitoAgent.asPath}") }