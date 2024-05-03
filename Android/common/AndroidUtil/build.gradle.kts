plugins {
    alias(libs.plugins.androidLibrary)
}

android {
    namespace = "com.mar.lib.util"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)

//    implementation fileTree(dir: 'libs', include: ['*.jar'])
//    implementation deps.constraintlayout
      api(project(":JavaUtil"))
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.appcompat)
}