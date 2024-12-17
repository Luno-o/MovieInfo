


plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
//    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
//    id("de.mannodermaus.android-junit5")
}
tasks.named<Test>("test"){
    useJUnitPlatform()
    maxHeapSize = "1G"
    testLogging{
        events("passed")
    }
}


//java {
//    sourceCompatibility = JavaVersion.VERSION_1_8
//    targetCompatibility = JavaVersion.VERSION_1_8
//}

dependencies{
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation ("com.google.dagger:dagger:2.51.1")
    ksp ("com.google.dagger:dagger-compiler:2.51.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.9.1")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("de.mannodermaus.junit5:android-test-core:1.3.0")
    testRuntimeOnly("de.mannodermaus.junit5:android-test-runner:1.3.0")

    testImplementation("org.mockito:mockito-core:4.0.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.mockito:mockito-inline:4.0.0")
    //hilt
//    implementation ("com.google.dagger:hilt-android:2.51.1")
//    ksp ("com.google.dagger:hilt-compiler:2.51.1")
    val  coroutinesVersion = "1.6.4"
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
}