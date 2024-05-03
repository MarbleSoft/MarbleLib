plugins {
    alias(libs.plugins.androidLibrary)
}

android {
    namespace = "com.marble.lib.widget.pairview"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

//        versionCode = 1
//        versionName = "1.0.0"
        version = "1.0.0"
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

    /*
    libraryVariants.all{ variant ->
        variant.outputs.all {
            val fileName:String = "marblelib-pairview-" + libs.versions.libPairView.get()
//            outputFileName = fileName
            outputFile.name  = fileName
            return true
        }
    }
     */
    libraryVariants.forEach { variant ->
        variant.outputs.forEach { out ->
            val fileName = "marblelib-pairview-" + libs.versions.libPairView.get() + ".aar"
            System.out.println(fileName + ",outName:"+ out.name)
            out.outputFile.renameTo(File(out.outputFile.parent,fileName))
        }
    }
}

dependencies {
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
    implementation(libs.androidx.annotation)
}