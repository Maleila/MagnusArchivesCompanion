package hu.ait.magnusarchivescompanion.ui.screen.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.ait.magnusarchivescompanion.Episode.Episode
import hu.ait.magnusarchivescompanion.ui.screen.episodes.EpisodeViewModel
import hu.ait.magnusarchivescompanion.ui.screen.episodes.EpisodesScreenUIState

@Composable
fun DetailsScreen(
    title: String,
    detailsViewModel: DetailsViewModel = viewModel()) {

    val episodeListState = detailsViewModel.episodesList().collectAsState(
        initial = DetailsScreenUIState.Init
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start) {

        if (episodeListState.value == DetailsScreenUIState.Init) {
            Text(text = "Init...")
        } else {
            episodeDetails(
                episode = detailsViewModel.getEpisodeByTitle(
                    title,
                    (episodeListState.value as DetailsScreenUIState.Success).episodesList
                )
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun episodeDetails(episode: Episode) {
    Text(
        text = episode.title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(10.dp)
    )
    Spacer(modifier = Modifier.fillMaxHeight(0.04f))
    Text(
        text = "Season " + episode.season,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(10.dp)
    )
    Text(
        text = episode.description,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(10.dp)
    )
    Text(
        text = "Narrated by " + episode.narrator,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(10.dp)
    )
    FlowRow(modifier = Modifier.padding(10.dp)) {
        for(entity in episode.entities!!) {
                Button(onClick = { },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black,
                        containerColor = Color.White
                    ),
                    modifier = Modifier.padding(
                        end = 8.dp
                    ),
                    //enabled = false
                ) {
                    Text(entity)
                }
            }
        }
    }
