plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.test1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.test1"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    sourceSets {
        named("main") {
            jniLibs.srcDirs("libs")
        }
    }

}





dependencies {

    implementation(files("libs/Msc.jar"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(files("libs\\Msc.jar"))
    implementation(libs.material)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.preference:preference:1.1.1")
    implementation ("androidx.cardview:cardview:1.0.0")
    //网络访问
    implementation ("com.squareup.okhttp3:okhttp:4.2.2")
    //下拉框

    //卡片视图
/*    implementation ("androidx.cardview:cardview:1.0.0")*/
    //GSON解析
    implementation ("com.squareup.retrofit2:converter-gson:2.4.0")

    //图片翻译
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.alibaba:fastjson:1.2.75")
}
/*
dependencies {
    implementation ("com.github.arcadefire:nice-spinner:1.4")
}
dependencies {
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.angmarch.views:nice-spinner:2.2.2")
}*/
