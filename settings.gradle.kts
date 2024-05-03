pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MarbleLib"

val javaLibPath  = "Java"
val javaLib = arrayOf(
    "JavaUtil"
    ,"async"
)
javaLib.forEach {lib ->
    include(lib)
    project(":${lib}").projectDir = file(javaLibPath+"/${lib}")
}

val androidLibPath  = "Android"
val androidLib = arrayOf(
    "widgets/PairView"
    ,"widgets/LabelView"

    ,"common/AndroidUtil"
)
androidLib.forEach {lib ->
    val libName = lib.removePrefix("widgets/").removePrefix("common/")
    include(libName)
    project(":${libName}").projectDir = file(androidLibPath+"/${lib}")
}

//include("JavaDemo")
//project(":JavaDemo").projectDir = file(javaLibPath+"/demo")

include("AndroidDemo")
project(":AndroidDemo").projectDir = file(androidLibPath+"/demo")