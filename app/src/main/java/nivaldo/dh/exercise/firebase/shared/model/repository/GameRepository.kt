package nivaldo.dh.exercise.firebase.shared.model.repository

import com.google.firebase.FirebaseException
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import nivaldo.dh.exercise.firebase.shared.constant.FirebaseStorageConstants
import nivaldo.dh.exercise.firebase.shared.model.data.Response

class GameRepository {

    private val firebaseStorage by lazy {
        Firebase.storage
    }

    suspend fun getGameImageStorageUrl(imagePath: String): Response {
        return try {
            val url = firebaseStorage
                .reference
                .child(FirebaseStorageConstants.REFERENCE_GAME_IMAGES_FOLDER)
                .child(imagePath)
                .downloadUrl
                .await()
                .toString()

            Response.Success(url)
        } catch (e: FirebaseException) {
            Response.Failure(e.localizedMessage)
        }
    }

}