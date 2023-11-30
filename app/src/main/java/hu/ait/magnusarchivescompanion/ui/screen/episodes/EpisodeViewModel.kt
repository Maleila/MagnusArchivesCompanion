package hu.ait.magnusarchivescompanion.ui.screen.episodes

import androidx.lifecycle.ViewModel
import hu.ait.magnusarchivescompanion.Episode.EpisodeWithId
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class EpisodeViewModel: ViewModel() {
//    fun episodesList() = callbackFlow {
//        val snapshotListener =
//            FirebaseFirestore.getInstance().collection(WritePostScreenViewModel.COLLECTION_POSTS)
//                .addSnapshotListener() { snapshot, e ->
//                    val response = if (snapshot != null) {
//                        val postList = snapshot.toObjects(Post::class.java)
//                        val postWithIdList = mutableListOf<PostWithId>()
//
//                        postList.forEachIndexed { index, post ->
//                            postWithIdList.add(PostWithId(snapshot.documents[index].id, post))
//                        }
//
//                        MainScreenUIState.Success(
//                            postWithIdList
//                        )
//                    } else {
//                        MainScreenUIState.Error(e?.message.toString())
//                    }
//
//                    trySend(response) // emit this value through the flow
//                }
//        awaitClose {
//            snapshotListener.remove()
//        }
//    }
}

sealed interface EpisodesScreenUIState {
    object Init : EpisodesScreenUIState

    data class Success(val postList: List<EpisodeWithId>) : EpisodesScreenUIState
    data class Error(val error: String?) : EpisodesScreenUIState
}