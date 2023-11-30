package hu.ait.magnusarchivescompanion.navigation
sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Details : Screen("details")
}