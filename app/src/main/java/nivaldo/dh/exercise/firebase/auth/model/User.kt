package nivaldo.dh.exercise.firebase.auth.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    val uid: String? = "",

    val name: String? = "",
)