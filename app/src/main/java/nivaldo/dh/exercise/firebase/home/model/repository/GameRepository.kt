package nivaldo.dh.exercise.firebase.home.model.repository

import com.google.firebase.FirebaseException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import nivaldo.dh.exercise.firebase.home.model.Game
import nivaldo.dh.exercise.firebase.shared.constant.FirebaseFirestoreConstants
import nivaldo.dh.exercise.firebase.shared.constant.FirebaseStorageConstants
import nivaldo.dh.exercise.firebase.shared.data.Response

class GameRepository {

    private val firebaseAuth by lazy {
        Firebase.auth
    }
    private val firebaseFirestore by lazy {
        Firebase.firestore
    }
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

    suspend fun getUserGamesList(): Response {
        var gamesList: MutableList<Game?> = mutableListOf()

        return try {
            firebaseAuth.currentUser?.let { currentUser ->
                val query = firebaseFirestore
                    .collection(FirebaseFirestoreConstants.Games.COLLECTION_NAME)
                    .whereEqualTo(FirebaseFirestoreConstants.Games.FIELD_USER_UID, currentUser.uid)
                    .get()
                    .await()

                gamesList = query.toObjects(Game::class.java)
            }

            Response.Success(gamesList)
        } catch (e: FirebaseException) {
            Response.Failure(e.localizedMessage)
        }
    }

}