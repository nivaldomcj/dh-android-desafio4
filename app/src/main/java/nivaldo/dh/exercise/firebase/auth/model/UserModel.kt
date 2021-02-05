package nivaldo.dh.exercise.firebase.auth.model

import com.google.firebase.firestore.DocumentId

data class UserModel(
    @DocumentId
    val uid: String? = "",

    val name: String? = "",
)