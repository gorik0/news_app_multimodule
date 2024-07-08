plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlinSerialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies{
    api(libs.retrofit)
    implementation(libs.kotlinx.coroutines.core)
//    implementation(libs.kotlinx.coroutines.android)
    api(libs.kotlinx.serialization.json)
    implementation(libs.androidx.annotation)
    implementation( libs.retrofit.adapters.result)
    implementation( libs.retrofit.adapters.result)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)

}