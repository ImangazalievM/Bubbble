dependencies {
   implementation(project(Modules.Core.data))
   implementation(project(Modules.AppMvp.coreUi))

   implementation(Dependencies.materialDrawer) {
       isTransitive = true
       exclude(group = "com.android.support")
   }
}