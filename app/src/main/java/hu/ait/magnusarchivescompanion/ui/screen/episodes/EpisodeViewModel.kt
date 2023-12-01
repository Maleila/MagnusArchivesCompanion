package hu.ait.magnusarchivescompanion.ui.screen.episodes

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.magnusarchivescompanion.Episode.Episode
import hu.ait.magnusarchivescompanion.Episode.EpisodeWithId
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class EpisodeViewModel: ViewModel() {

    companion object {
        const val COLLECTION_EPISODES = "episodes"  //I think this is where to find it in the database?
    }
    fun episodesList() = callbackFlow {
        val snapshotListener =
            FirebaseFirestore.getInstance().collection(COLLECTION_EPISODES)
                .addSnapshotListener() { snapshot, e ->
                    val response = if (snapshot != null) {
                        System.out.println("getting the list")
                        val episodeList = snapshot.toObjects(Episode::class.java)
                        //System.out.println("num episodes: ${episodesList.size()}")
                        var episodeWithIdList = mutableListOf<EpisodeWithId>()

                        episodeList.forEachIndexed { index, episode ->
                            episodeWithIdList.add(EpisodeWithId(snapshot.documents[index].id, episode))
                            System.out.println("adding: " + episode.title)
                        }

                        System.out.println("num episodes: ${episodeWithIdList.size}")

                        EpisodesScreenUIState.Success(
                            episodeWithIdList
                        )
                    } else {
                        EpisodesScreenUIState.Error(e?.message.toString())
                    }

                    trySend(response) // emit this value through the flow
                }
        awaitClose {
            snapshotListener.remove()
        }
    }
}

sealed interface EpisodesScreenUIState {
    object Init : EpisodesScreenUIState

    data class Success(val episodesList: List<EpisodeWithId>) : EpisodesScreenUIState
    data class Error(val error: String?) : EpisodesScreenUIState
}