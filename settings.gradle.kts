pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.vanutp.dev/main") {
            content {
                includeGroup("site.siredvin")
                includeGroupByRegex("site.siredvin.*")
            }
        }
        maven("https://maven.fabricmc.net/") {
            content {
                includeGroup("net.fabricmc")
                includeGroup("fabric-loom")
            }
        }
        maven("https://maven.parchmentmc.org/") {
            content {
                includeGroup("org.parchmentmc")
                includeGroup("org.parchmentmc.feather")
                includeGroup("org.parchmentmc.data")
            }
        }
        maven("https://maven.minecraftforge.net/releases") {
            content {
                includeGroup("net.minecraftforge")
                includeGroup("net.minecraftforge.gradle")
            }
        }
        maven("https://repo.spongepowered.org/repository/maven-public") {
            content {
                includeGroup("org.spongepowered")
            }
        }
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.spongepowered.mixin") {
                useModule("org.spongepowered:mixingradle:${requested.version}")
            }
        }
    }
}

val minecraftVersion: String by settings
rootProject.name = "Turtlematic $minecraftVersion"

include(":core")
include(":forge")
include(":fabric")


for (project in rootProject.children) {
    project.projectDir = file("projects/${project.name}")
}
