package nivaldo.dh.exercise.firebase.home.model.business

import android.graphics.Bitmap
import android.util.Log
import nivaldo.dh.exercise.firebase.home.model.Game
import nivaldo.dh.exercise.firebase.home.model.repository.GameRepository
import nivaldo.dh.exercise.firebase.shared.data.Response
import java.util.*

class GameBusiness {

    private val repository by lazy {
        GameRepository()
    }

    suspend fun getUserGamesList(): Response {
        return when (val gamesListResponse = repository.getUserGamesList()) {
            is Response.Success -> {
                var gamesList =
                    (gamesListResponse.data as? MutableList<*>)?.filterIsInstance(Game::class.java)

                gamesList = gamesList?.map { game ->
                    // get downloadable url of image from firestore
                    game.imagePath?.let { imagePath ->
                        when (val imgGameResponse = repository.getGameImageStorageUrl(imagePath)) {
                            is Response.Success -> {
                                game.mImageStoragePath = imgGameResponse.data as? String
                            }
                            is Response.Failure -> {
                                Log.e("NMCJ", "Image from ${game.title} does not exist!")
                            }
                        }
                    }

                    return@map game
                }

                Response.Success(gamesList)
            }
            is Response.Failure -> {
                gamesListResponse
            }
        }
    }

    suspend fun filterUserGamesList(text: String): Response {
        return when (val gamesListResponse = getUserGamesList()) {
            is Response.Success -> {
                var gamesList =
                    (gamesListResponse.data as? MutableList<*>)?.filterIsInstance(Game::class.java)

                gamesList = gamesList?.filter {
                    it.title
                        .toLowerCase(Locale.ROOT)
                        .startsWith(text.toLowerCase(Locale.ROOT))
                }

                Response.Success(gamesList)
            }
            is Response.Failure -> {
                gamesListResponse
            }
        }
    }

}