package nivaldo.dh.exercise.firebase.home.model.repository

import com.google.firebase.FirebaseException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import nivaldo.dh.exercise.firebase.home.model.Game
import nivaldo.dh.exercise.firebase.shared.constant.FirebaseFirestoreConstants
import nivaldo.dh.exercise.firebase.shared.data.Response

class GameDetailRepository {

    private val firebaseFirestore by lazy {
        Firebase.firestore
    }

    suspend fun getGameData(gameUid: String): Response {
        return try {
            val game = firebaseFirestore
                .collection(FirebaseFirestoreConstants.Games.COLLECTION_NAME)
                .document(gameUid)
                .get()
                .await()
                .toObject(Game::class.java)

            Response.Success(game)
        } catch (e: FirebaseException) {
            Response.Failure(e.localizedMessage)
        }
    }

}