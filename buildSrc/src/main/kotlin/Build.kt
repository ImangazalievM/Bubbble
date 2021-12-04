import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

fun Project.baseScript(name: String) {
    apply(from = rootProject.file("gradle/${name}.gradle.kts"))
}

fun Project.baseScriptFrom(path: String) {
    apply(from = rootProject.file(path))
}

object BuildScript {
    const val appConfig = "gradle/base/app-config.gradle.kts"
    const val baseConfig = "gradle/base/base-config.gradle.kts"
    const val moduleConfig = "gradle/base/module-config.gradle.kts"
    private const val mvpFeatureModuleConfig = "gradle/mvp-feature-module.gradle.kts"
    private const val uiTestsConfig = "gradle/tests/ui-tests-config.gradle.kts"
    private const val unitTestsConfig = "gradle/tests/unit-tests-config.gradle.kts"

    const val hiltConfig = "gradle/libs/hilt-config.gradle.kts"

    private val moduleConfigs: Map<String, List<String>> = mapOf(
        module(
            Modules.AppMvp.app,
            appConfig,
            uiTestsConfig
        ),
        module(
            Modules.AppMvp.coreUi,
            moduleConfig,
            hiltConfig
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
            Modules.AppMvp.featureShotsSearch,
            mvpFeatureModuleConfig
        ),
        module(
            Modules.AppMvp.featureUserProfile,
            mvpFeatureModuleConfig
        ),
        //core
        module(
            Modules.Core.core,
            moduleConfig,
            hiltConfig
        ),

        module(
            Modules.Core.ui,
            moduleConfig
        ),
        module(
            Modules.Core.data,
            moduleConfig,
            hiltConfig,
            unitTestsConfig
        ),
        module(
            Modules.Core.di,
            moduleConfig,
            hiltConfig
        ),
        module(
            Modules.Core.network,
            moduleConfig,
            hiltConfig
        ),
        module(
            Modules.Core.models,
            moduleConfig
        ),
        module(
            Modules.tests,
            moduleConfig,
            hiltConfig
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