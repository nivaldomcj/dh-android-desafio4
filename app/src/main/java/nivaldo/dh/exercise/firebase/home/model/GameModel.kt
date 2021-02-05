package nivaldo.dh.exercise.firebase.home.model

import com.google.firebase.firestore.DocumentId

data class GameModel(
    @DocumentId
    val uid: String? = "",

    val imagePath: String? = "",

    val title: String? = "",

    val releaseYear: Int? = 0,

    val description: String? = "",
)
