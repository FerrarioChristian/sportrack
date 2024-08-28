buildscript {
    repositories {
        google()
    }
    dependencies {
        val navVersion = "2.7.7"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}
plugins {
    id("com.android.application") version "8.5.2" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}