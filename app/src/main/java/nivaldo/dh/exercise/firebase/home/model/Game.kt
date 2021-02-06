package nivaldo.dh.exercise.firebase.home.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    @DocumentId
    val uid: String? = "",

    val imagePath: String? = "",

    val title: String? = "",

    val releaseYear: Int? = 0,

    val description: String? = "",
) : Parcelable