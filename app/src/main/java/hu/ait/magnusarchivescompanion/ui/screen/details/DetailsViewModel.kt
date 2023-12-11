package hu.ait.magnusarchivescompanion.ui.screen.details

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.magnusarchivescompanion.Episode.Episode
import hu.ait.magnusarchivescompanion.Episode.EpisodeWithId
import hu.ait.magnusarchivescompanion.ui.screen.episodes.EpisodeViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class DetailsViewModel: ViewModel() {

    companion object {
        const val COLLECTION_EPISODES = "episodes"  //where to look in the database
    }

    fun episodesList() = callbackFlow {
        val snapshotListener =
            FirebaseFirestore.getInstance().collection(EpisodeViewModel.COLLECTION_EPISODES)
                .addSnapshotListener() { snapshot, e ->
                    val response = if (snapshot != null) {
                        val episodeList = snapshot.toObjects(Episode::class.java)
                        var episodeWithIdList = mutableListOf<EpisodeWithId>()

                        episodeList.forEachIndexed { index, episode ->
                            episodeWithIdList.add(
                                EpisodeWithId(
                                    snapshot.documents[index].id,
                                    episode
                                )
                            )
                        }

                        DetailsScreenUIState.Success(
                            episodeWithIdList
                        )
                    } else {
                        DetailsScreenUIState.Error(e?.message.toString())
                    }

                    trySend(response)
                }
        awaitClose {
            snapshotListener.remove()
        }
    }

    fun getEpisodeByTitle(title: String, episodes: List<EpisodeWithId>): Episode {
        for(e in episodes) {
            if(e.episode.title == title) {
                return e.episode
            }
        }

        return Episode("", "", "","", "", listOf<String>(""))
    }
}

sealed interface DetailsScreenUIState {
    object Init : DetailsScreenUIState

    data class Success(val episodesList: List<EpisodeWithId>) : DetailsScreenUIState
    data class Error(val error: String?) : DetailsScreenUIState
}
