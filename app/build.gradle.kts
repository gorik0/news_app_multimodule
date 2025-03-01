plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kapt)
//
}

android {
    namespace = "com.gorik.newmulti"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gorik.newmulti"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


        buildConfigField("String","NEWS_API_KEY","\"155ae65d7264461397c901103488c01e\"")
        buildConfigField("String","NEWS_API_BASE_URL","\"https://newsapi.org/v2/\"")
    }


    buildTypes {
        release {
            isMinifyEnabled =    false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeCompiler {
        enableStrongSkippingMode = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.dagger.hilt.android)
    debugImplementation(libs.okhttpLoggingIntercpetor)
    kapt(libs.dagger.hilt.compiler)


    implementation(project(":newsapi"))
    implementation(project(":database"))
    implementation(project(":news-data"))
    implementation(project(":news-ui"))
    implementation(project(":features:news-main"))
    implementation(project(":news-common"))

}