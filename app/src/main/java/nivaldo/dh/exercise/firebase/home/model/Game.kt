package nivaldo.dh.exercise.firebase.home.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    @DocumentId
    val uid: String? = "",

    @Exclude
    var imageStoragePath: String? = "",

    val imagePath: String? = "",

    val title: String? = "",

    val releaseYear: Int? = 0,

    val description: String? = "",
) : Parcelable