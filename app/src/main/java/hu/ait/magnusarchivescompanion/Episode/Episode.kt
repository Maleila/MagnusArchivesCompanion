package hu.ait.magnusarchivescompanion.Episode

data class Episode(
    var uid: String = "",
    var title: String = "",
    var description: String = "",
    var narrator: String = "",
    //var season: Long = 0,
    var season: String = "",
    var entities: List<String>? = null
) {
    fun getEpisodeNumber(): Int {
        var temp = title.split(":")[0]
        print(temp)
        return temp.substring(4).toInt()
    }
}

//might not need this
data class EpisodeWithId(
    val episodeId: String,
    val episode: Episode
)