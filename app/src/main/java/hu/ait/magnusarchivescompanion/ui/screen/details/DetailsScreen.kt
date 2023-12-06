package hu.ait.magnusarchivescompanion.ui.screen.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.ait.magnusarchivescompanion.Episode.Episode
import hu.ait.magnusarchivescompanion.ui.screen.episodes.EpisodeViewModel
import hu.ait.magnusarchivescompanion.ui.screen.episodes.EpisodesScreenUIState

@Composable
fun DetailsScreen(title: String, description: String, narrator: String, season: String) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = title,
        )
        Text(
            text = description,
        )
        Text(
            text = narrator,
        )
        Text(
            text = season,
        )
//        Text( //too lazy to show whole list - just for testing
//            text = entities!![0],
//        )
    }

}