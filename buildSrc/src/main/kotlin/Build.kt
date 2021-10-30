import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

fun Project.baseScript(name: String) {
    apply(from=rootProject.file("gradle/${name}.gradle.kts"))
}

object BuildScript {
    private const val appConfig = "gradle/app-config.gradle.kts"
    private const val moduleConfig = "gradle/module-config.gradle.kts"
    private const val mvpFeatureModuleConfig = "gradle/mvp-feature-module.gradle.kts"

    private val moduleConfigs = mapOf(
        Modules.AppMvp.app to appConfig,
        Modules.AppMvp.coreUi to null,
        Modules.AppMvp.featureShots to mvpFeatureModuleConfig,
        Modules.AppMvp.featureShotDetails to mvpFeatureModuleConfig,
        Modules.AppMvp.featureShotZoom to mvpFeatureModuleConfig,
        Modules.AppMvp.featureShotSearch to mvpFeatureModuleConfig,
        Modules.AppMvp.featureShotUserProfile to mvpFeatureModuleConfig,

        Modules.Core.core to null,
        Modules.Core.data to null,
        Modules.Core.di to null,
        Modules.Core.network to null,
        Modules.Core.models to null
    )

    fun getScriptPath(moduleName: String): String? {
        return if (moduleConfigs.containsKey(moduleName)) {
            moduleConfigs[moduleName] ?: moduleConfig
        } else null
    }

}