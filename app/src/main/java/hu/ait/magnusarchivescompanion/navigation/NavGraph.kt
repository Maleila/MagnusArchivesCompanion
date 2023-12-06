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
                onNavigateToDetailsScreen = { title, description, narrator, season ->
                    navController.navigate("details/$title/$description/$narrator/$season")
                },
            )
        }
        composable("details/{title}/{description}/{narrator}/{season}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType },
                navArgument("narrator") { type = NavType.StringType },
                navArgument("season") { type = NavType.StringType }
            )) {
            val title = it.arguments?.getString("title")
            val description = it.arguments?.getString("description")
            val narrator = it.arguments?.getString("narrator")
            val season = it.arguments?.getString("season")
            if (title != null && description != null && narrator != null && season != null) {
                DetailsScreen(title, description, narrator, season)
            }
        }
    }
}
