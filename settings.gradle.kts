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

        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            // Do not change the username below. It should always be "mapbox" (not your username).
            credentials.username = "mapbox"
            // Replace this with your secret token
            // In production (real life), you should never expose your secret token
            // Instead, follow instructions on the Mapbox website for how to hide this in a secure way
            credentials.password = "sk.eyJ1IjoiMTAxMDM1OTc5bGMiLCJhIjoiY2xweXdjczdwMGpuYTJrbzVsam1tdzFqOCJ9.EbUHMdYziZkY0W7oTc5GAA"


            authentication.create<BasicAuthentication>("basic")
        }
    }
}

rootProject.name = "LostAndFound"
include(":app")
 