package hu.ait.magnusarchivescompanion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.ait.magnusarchivescompanion.ui.screen.details.DetailsScreen
import hu.ait.magnusarchivescompanion.ui.screen.episodes.EpisodesScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            EpisodesScreen(
                onNavigateToDetailsScreen = { episode ->
                    navController.navigate("details/$episode")
                },
            )
        }
        composable("details/{episode}",
            arguments = listOf(
                navArgument("episode") { type = NavType.StringType }
            )) {
            val episode = it.arguments?.getString("episode")
            if (episode != null) {
                DetailsScreen(episode)
            }
        }
    }
}
