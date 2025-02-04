plugins {
    id("org.jetbrains.kotlin.android")
    id("androidx.room")
    id("com.android.library")
    id("com.google.devtools.ksp")
}
tasks.withType<Test>{
    useJUnitPlatform()
    maxHeapSize = "1G"
    testLogging{
        events("passed","failed")
    }
}
room {
    schemaDirectory("$projectDir/schemas")
}
android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.0")

    testImplementation("junit:junit:4.13.2")
    implementation(project(":domain"))
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    //room
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    ksp ("androidx.room:room-compiler:2.6.1")
    //network
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.squareup.moshi:moshi:1.15.1")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.8.0")
    //date time
    implementation("com.jakewharton.threetenabp:threetenabp:1.2.4")
    //logs
    implementation ("com.jakewharton.timber:timber:5.0.1")    //Dagger
    implementation ("com.google.dagger:dagger:2.51.1")
    ksp ("com.google.dagger:dagger-compiler:2.51.1")
    //hilt
    implementation ("com.google.dagger:hilt-android:2.51.1")
    ksp ("com.google.dagger:hilt-compiler:2.51.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.9.1")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-params:5.9.1")
    testRuntimeOnly("org.junit.platform:junit-platform-suite:1.9.1")


    testImplementation("org.mockito:mockito-core:4.0.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.mockito:mockito-inline:4.0.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    //date time
    testImplementation("com.jakewharton.threetenabp:threetenabp:1.2.4")
    testImplementation( "org.threeten:threetenbp:1.3.1")
}
