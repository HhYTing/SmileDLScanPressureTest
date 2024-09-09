plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.smiledlScanpressuretext"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.smiledlScanpressuretext"
        minSdk = 24
        targetSdk = 26
        versionCode = 1
        versionName = "1.0.4_H10T"

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

    buildFeatures.viewBinding = true

    aaptOptions {
        noCompress(".flac", ".pcm")  //表示不让aapt压缩的文件后缀
    }

    packagingOptions {
        exclude ("META-INF/DEPENDENCIES")
        exclude ("META-INF/NOTICE")
        exclude ("META-INF/LICENSE")
        exclude ("META-INF/LICENSE.txt")
        exclude ("META-INF/NOTICE.txt")
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar", "*.jar"))))
    implementation("com.alibaba:fastjson:1.1.54.android")
    implementation("com.google.protobuf:protobuf-java:3.5.1")
    implementation("com.elvishew:xlog:1.10.1")




}