import io.gitlab.arturbosch.detekt.extensions.DetektExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler)  apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.kapt) apply false
alias(libs.plugins.androidx.room) apply  false
alias(libs.plugins.detekt) apply  true


}



allprojects.onEach { proj->
    proj.afterEvaluate{

        with(proj.plugins) {
            if (hasPlugin(libs.plugins.jetbrains.kotlin.android.get().pluginId)
                || hasPlugin(libs.plugins.jetbrains.kotlin.jvm.get().pluginId)
            ) {
                apply(libs.plugins.detekt.get().pluginId)


                proj.extensions.configure<DetektExtension> {
                    config.setFrom(rootProject.files("default-detekt-config.yml"))
                }


                proj.dependencies.add("detektPlugins", libs.detect.formatting.get().toString())
            }

        }
    }
}