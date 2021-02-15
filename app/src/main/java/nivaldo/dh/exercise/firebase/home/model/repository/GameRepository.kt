package nivaldo.dh.exercise.firebase.home.model.repository

import com.google.firebase.FirebaseException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import nivaldo.dh.exercise.firebase.home.model.Game
import nivaldo.dh.exercise.firebase.shared.constant.FirebaseFirestoreConstants
import nivaldo.dh.exercise.firebase.shared.constant.FirebaseStorageConstants
import nivaldo.dh.exercise.firebase.shared.data.Response

class GameRepository {

    private val gamesCollection by lazy {
        Firebase.firestore.collection(FirebaseFirestoreConstants.COLLECTION_GAMES)
    }
    private val gamesStorageRef by lazy {
        Firebase.storage.reference.child(FirebaseStorageConstants.REFERENCE_GAME_IMAGES)
    }

    suspend fun getGamesList(): Response {
        val gamesList: MutableList<Game?> = mutableListOf()

        return try {
            val snapshot = gamesCollection.get().await()

            snapshot.documents.forEach { document ->
                val game = document.toObject(Game::class.java)

                game?.imagePath?.let {
                    // we need to get the URL tokenized to the image from Firebase Storage
                    // tokenized means to get image path with auth token of current user
                    game.imageStorageUrl = gamesStorageRef.child(it).downloadUrl.await().toString()
                }

                gamesList.add(game)
            }

            Response.Success(gamesList)
        } catch (e: FirebaseException) {
            Response.Failure(e.localizedMessage)
        }
    }

}