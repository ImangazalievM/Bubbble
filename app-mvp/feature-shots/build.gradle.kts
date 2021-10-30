plugins {
    kotlin("kapt")
}

dependencies {
   implementation(project(Modules.Core.data))
   implementation(project(Modules.AppMvp.coreUi))
   kapt(Dependencies.moxyCompiler)

   implementation(Dependencies.materialDrawer) {
       isTransitive = true
       exclude(group = "com.android.support")
   }

   implementation(Dependencies.dagger)
   kapt(Dependencies.daggerCompiler)
}