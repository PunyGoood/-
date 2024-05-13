// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}


buildscript {
    repositories {
        google()
        maven { url =uri("https://maven.aliyun.com/nexus/content/groups/public/") } // 注意圆括号和引号
        maven { url =uri("https://jitpack.io") } // 注意圆括号和引号
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:4.1.0") // 或者使用你需要的 Gradle 插件的版本
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        /*classpath ("com.github.arcadefire:nice-spinner:1.3.1")*/
    }
}




