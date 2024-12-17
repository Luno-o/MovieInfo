// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.2.1")
    }
}
plugins {
    id("com.android.application") version "8.6.1" apply false
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
    id("com.android.library") version "8.6.1" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20" apply false
    id("com.google.devtools.ksp").version("2.0.20-1.0.24") apply false
    id ("androidx.room") version "2.6.1" apply false
    id("org.jetbrains.kotlin.jvm") version "2.0.20" apply false
}