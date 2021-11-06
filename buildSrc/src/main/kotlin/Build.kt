import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

fun Project.baseScript(name: String) {
    apply(from = rootProject.file("gradle/${name}.gradle.kts"))
}

object BuildScript {
    private const val appConfig = "gradle/app-config.gradle.kts"
    private const val moduleConfig = "gradle/module-config.gradle.kts"
    private const val mvpFeatureModuleConfig = "gradle/mvp-feature-module.gradle.kts"
    private const val uiTestsConfig = "gradle/ui-tests-config.gradle.kts"
    private const val unitTestsConfig = "gradle/unit-tests-config.gradle.kts"

    private val moduleConfigs: Map<String, List<String>> = mapOf(
        module(
            Modules.AppMvp.app,
            appConfig,
            uiTestsConfig
        ),
        module(
            Modules.AppMvp.coreUi,
            moduleConfig
        ),
        module(
            Modules.AppMvp.featureShots,
            mvpFeatureModuleConfig
        ),
        module(
            Modules.AppMvp.featureShotDetails,
            mvpFeatureModuleConfig
        ),
        module(
            Modules.AppMvp.featureShotZoom,
            mvpFeatureModuleConfig
        ),
        module(
            Modules.AppMvp.featureShotSearch,
            mvpFeatureModuleConfig
        ),
        module(
            Modules.AppMvp.featureUserProfile,
            mvpFeatureModuleConfig
        ),

        module(
            Modules.Core.core,
            moduleConfig
        ),
        module(
            Modules.Core.data,
            moduleConfig,
            unitTestsConfig
        ),
        module(
            Modules.Core.di,
            moduleConfig
        ),
        module(
            Modules.Core.network,
            moduleConfig
        ),
        module(
            Modules.Core.models,
            moduleConfig
        ),

        module(
            Modules.tests,
            moduleConfig
        )
    )

    fun getScriptPath(moduleName: String): List<String> {
        return if (moduleConfigs.containsKey(moduleName)) {
            moduleConfigs[moduleName]!!
        } else emptyList()
    }

    private fun module(
        module: String,
        vararg scripts: String
    ): Pair<String, List<String>> {
        return module to scripts.toList()
    }

}