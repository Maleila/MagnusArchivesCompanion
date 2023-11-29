package hu.ait.magnusarchivescompanion.Episode

data class Episode(
    var uid: String = "",
    var title: String = "",
    var description: String = "",
    var narrator: String = "",
    var season: Int = 0,
    var entities: List<String>? = null
)

//might not need this
data class PostWithId(
    val postId: String,
    val post: Episode
)