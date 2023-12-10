package hu.ait.magnusarchivescompanion.ui.screen.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.ait.magnusarchivescompanion.Episode.Episode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesScreen(
    episodesViewModel: EpisodeViewModel = viewModel(),
    onNavigateToDetailsScreen: (String) -> Unit,
    onReload: () -> Unit
) {

    var showSearchDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val episodeListState = episodesViewModel.episodesList().collectAsState(
        initial = EpisodesScreenUIState.Init
    )

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("The", style = MaterialTheme.typography.titleMedium)
                Text("Magnus Archives", style = MaterialTheme.typography.titleLarge)
                Text("Listening Companion", style = MaterialTheme.typography.titleMedium)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showSearchDialog = true
                },
                containerColor = Color.Gray,
                shape = RoundedCornerShape(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search",
                    tint = Color.Black,
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            if (episodesViewModel.searchParams.narrator != "Any" || episodesViewModel.searchParams.entity != "Any" || episodesViewModel.searchParams.season != "Any") {
                Text(text = "Filter by: " + episodesViewModel.searchParams.searchString,
                    modifier = Modifier.padding(10.dp))
                Button(modifier = Modifier.padding(horizontal = 10.dp).padding(bottom = 10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black,
                        containerColor = Color.White
                    ),
                    onClick = {
                        episodesViewModel.searchParams.reset()
                        episodesViewModel.searchParams.updateList()
                        episodesViewModel.searchParams.updateList()
                        onReload()
                    }) {
                    Text(text = "Clear filters")
                }
            }

            if (episodeListState.value == EpisodesScreenUIState.Init) {
                Text(text = "Loading",
                    modifier = Modifier.padding(10.dp))
            } else if (episodeListState.value is EpisodesScreenUIState.Success) {
                if (episodesViewModel.filter((episodeListState.value as EpisodesScreenUIState.Success).episodesList)
                        .isEmpty()
                ) {
                    Text(text = "No episodes matching search terms - try another search!",
                        modifier = Modifier.padding(10.dp))
                } else {
                    LazyColumn() {
                        items(episodesViewModel.filter((episodeListState.value as EpisodesScreenUIState.Success).episodesList)) {
                            EpisodeCard(episode = it.episode,
                                onCardClicked = { onNavigateToDetailsScreen(it.episode.title) })
                        }
                    }
                }
            }
            if (showSearchDialog) {
                SearchDialogue(
                    episodesViewModel,
                    { showSearchDialog = false })
            }
        }
    }
}

@Composable
fun EpisodeCard(
    episode: Episode,
    onCardClicked: () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(5.dp)
            .clickable { onCardClicked() }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = episode.title,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = episode.description,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row { //TODO change this to a lazy row if time
                        for((buttons, entity) in episode.entities!!.withIndex()) {
                            if(buttons <= 2) {
                                Button(onClick = { },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color.White,
                                        containerColor = Color.Black
                                    ),
                                    modifier = Modifier.padding(
                                        end = 8.dp
                                    ),
                                ) {
                                    Text(entity,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchDialogue(
    episodesViewModel: EpisodeViewModel,
    onDialogDismiss: () -> Unit = {}
) {

    Dialog(
        onDismissRequest = onDialogDismiss
    ) {

        var season by rememberSaveable {
            mutableStateOf("Any")
        }

        var narrator by rememberSaveable {
            mutableStateOf("Any")
        }

        var entity by rememberSaveable {
            mutableStateOf("Any")
        }

        Column(
            Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(10.dp)
        ) {
            Text(text = "Season", modifier = Modifier.padding(horizontal = 10.dp))
            SpinnerSample(
                listOf("Any", "1", "2", "3", "4", "5"),
                preselected = "Any",
                onSelectionChanged = {
                    season = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp))
            Text(text = "Narrator", modifier = Modifier.padding(horizontal = 10.dp))
            SpinnerSample(
                listOf("Any", "Jon", "Martin", "Gertrude", "Melanie", "Basira", "Other"), //TODO double check I forget
                preselected = "Any",
                onSelectionChanged = {
                    narrator = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp))
            Text(text = "Entity", modifier = Modifier.padding(horizontal = 10.dp))
            SpinnerSample(
                listOf("Any",
                    "The Buried",
                    "The Corruption",
                    "The Dark",
                    "The Desolation",
                    "The End",
                    "The Eye",
                    "The Flesh",
                    "The Hunt",
                    "The Lonely",
                    "The Slaughter",
                    "The Spiral",
                    "The Stranger",
                    "The Vast",
                    "The Web",
                    "The Extinction"),
                preselected = "Any",
                onSelectionChanged = {
                    entity = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp))
            Row {
                Button(modifier = Modifier.padding(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black,
                        containerColor = Color.White
                    ),
                    onClick = {
                    episodesViewModel.searchParams.narrator = narrator
                    episodesViewModel.searchParams.entity = entity
                    episodesViewModel.searchParams.season = season
                    episodesViewModel.searchParams.updateList()
                    onDialogDismiss()
                }) {
                    Text(text = "Search")
                }

            }
        }
    }
}

@Composable
fun SpinnerSample(
    list: List<String>,
    preselected: String,
    onSelectionChanged: (myData: String) -> Unit,
    modifier: Modifier = Modifier
){
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) }
    OutlinedCard(
        modifier = modifier.clickable {
            expanded = !expanded
        } ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = selected,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp))
            Icon(Icons.Outlined.ArrowDropDown, null, modifier = Modifier.padding(8.dp))
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }, modifier = Modifier.fillMaxWidth()
            ) {
                list.forEach { listEntry ->
                    DropdownMenuItem(
                        onClick = {
                            selected = listEntry
                            expanded = false
                            onSelectionChanged (selected)
                        },
                        text = {
                            Text(
                                text = listEntry,
                                modifier = Modifier
                            )
                        },
                    )
                }
            }
        }
    }
}
