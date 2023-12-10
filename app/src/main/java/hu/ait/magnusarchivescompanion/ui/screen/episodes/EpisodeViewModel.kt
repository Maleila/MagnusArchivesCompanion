package hu.ait.magnusarchivescompanion.ui.screen.episodes

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.magnusarchivescompanion.Episode.Episode
import hu.ait.magnusarchivescompanion.Episode.EpisodeWithId
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class EpisodeViewModel: ViewModel() {

    companion object {
        const val COLLECTION_EPISODES = "episodes"  //where to look in the database
    }

    var searchParams = Search("Any", "Any", "Any")

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

    fun filter(episodesList: List<EpisodeWithId>): List<EpisodeWithId> {
        var filteredEpisodes: MutableList<EpisodeWithId> = mutableListOf()

        for(e in episodesList) {
            if(e.episode.narrator == searchParams.narrator || searchParams.narrator == "Any") {
                if(e.episode.entities!!.contains(searchParams.entity) || searchParams.entity =="Any") {
                    if(e.episode.season == searchParams.season || searchParams.season == "Any") {
                        filteredEpisodes.add(e)
                        System.out.println("adding: " + e.episode.title)
                    }
                }
            }
        }
        return sortByOrder(filteredEpisodes)
    }
}

fun sortByOrder(episodes: MutableList<EpisodeWithId>): List<EpisodeWithId> {
    return episodes.sortedWith { a, b ->
        when {
            a.episode.getEpisodeNumber() > b.episode.getEpisodeNumber() -> 1
            a.episode.getEpisodeNumber() < b.episode.getEpisodeNumber() -> -1
            else -> 0
        }
    }
}

//to hold search params -- not sure this is the best way to do it
public class Search(narrator: String, entity: String, season: String) {
    var narrator: String = narrator
    var entity: String = entity
    var season: String = season
    var searchList: MutableList<String> = mutableListOf()

    fun updateList() {
        searchList.clear()
        if(narrator != "Any") {
            searchList.add(narrator)
        }
        if(entity != "Any") {
            searchList.add(entity)
        }
        if(season != "Any") {
            searchList.add(season)
        }
    }

    fun reset() {
        narrator = "Any"
        entity = "Any"
        season = "Any"
    }
}

sealed interface EpisodesScreenUIState {
    object Init : EpisodesScreenUIState

    data class Success(val episodesList: List<EpisodeWithId>) : EpisodesScreenUIState
    data class Error(val error: String?) : EpisodesScreenUIState
}