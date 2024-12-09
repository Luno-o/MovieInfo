plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")

}


java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies{
    implementation ("com.google.dagger:dagger:2.51.1")
    ksp ("com.google.dagger:dagger-compiler:2.51.1")
    //hilt
//    implementation ("com.google.dagger:hilt-android:2.51.1")
//    ksp ("com.google.dagger:hilt-compiler:2.51.1")
    val  coroutinesVersion = "1.6.4"
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
}