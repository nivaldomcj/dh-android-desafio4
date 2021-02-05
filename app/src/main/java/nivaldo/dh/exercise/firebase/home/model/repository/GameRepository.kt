package nivaldo.dh.exercise.firebase.home.model.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import nivaldo.dh.exercise.firebase.home.model.GameModel
import nivaldo.dh.exercise.firebase.shared.constants.FirestoreConstants
import nivaldo.dh.exercise.firebase.shared.data.Response

class GameRepository {

    private val gamesCollection by lazy {
        Firebase.firestore.collection(FirestoreConstants.COLLECTION_GAMES)
    }

    suspend fun getGamesList(): Response {
        return try {
            val snapshot = gamesCollection.get().await()
            val listGames = snapshot.toObjects(GameModel::class.java)

            Response.Success(listGames)
        } catch (e: FirebaseFirestoreException) {
            Response.Failure(e.localizedMessage)
        }
    }

}