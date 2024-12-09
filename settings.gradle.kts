pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        flatDir {
            dirs("libs")
        }
    }
}

rootProject.name = "MovieInfo"
include(":app")
include(":data")
include(":domain")
