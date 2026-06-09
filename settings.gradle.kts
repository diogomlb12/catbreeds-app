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
    }
}

rootProject.name = "CatBreeds"

include(
    ":app",
    ":core:network",
    ":core:database",
    ":core:domain",
    ":core:ui",
    ":feature:breeds",
    ":feature:detail",
    ":feature:favourites"
)