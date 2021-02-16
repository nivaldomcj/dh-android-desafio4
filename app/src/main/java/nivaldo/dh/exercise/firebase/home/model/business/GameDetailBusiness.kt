package nivaldo.dh.exercise.firebase.home.model.business

import android.util.Log
import nivaldo.dh.exercise.firebase.home.model.Game
import nivaldo.dh.exercise.firebase.home.model.repository.GameDetailRepository
import nivaldo.dh.exercise.firebase.shared.model.repository.GameRepository
import nivaldo.dh.exercise.firebase.shared.model.data.Response

class GameDetailBusiness {

    private val gameDetailRepository by lazy {
        GameDetailRepository()
    }
    private val gameRepository by lazy {
        GameRepository()
    }

    suspend fun getGameData(gameUid: String): Response {
        return when (val gameResponse = gameDetailRepository.getGameData(gameUid)) {
            is Response.Success -> {
                val game = gameResponse.data as Game

                // get downloadable url of image from firestore
                game.imagePath?.let {
                    when (val gameImgResponse = gameRepository.getGameImageStorageUrl(it)) {
                        is Response.Success -> {
                            game.mImageStoragePath = gameImgResponse.data as String
                        }
                        is Response.Failure -> {
                            Log.e("NMCJ", "Image from ${game.title} does not exist!")
                        }
                    }
                }

                Response.Success(game)
            }
            is Response.Failure -> {
                gameResponse
            }
        }
    }

}