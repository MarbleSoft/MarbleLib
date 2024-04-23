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
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Demo"
include(":demo")

val widgets = arrayOf(
    "PairView"
    ,"LabelView"
//    ,"FlowLayout"
)
widgets.forEach {widget ->
    include(widget)
    project(":${widget}").projectDir = file("../widget/${widget}")
}

include("JavaUtil")
project(":JavaUtil").projectDir = file("../../Java/JavaUtil")

include("AndroidUtil")
project(":AndroidUtil").projectDir = file("../AndroidUtil")
//include(":PairView")
