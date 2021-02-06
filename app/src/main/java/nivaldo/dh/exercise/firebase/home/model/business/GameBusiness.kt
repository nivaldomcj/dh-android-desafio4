package nivaldo.dh.exercise.firebase.home.model.business

import nivaldo.dh.exercise.firebase.home.model.repository.GameRepository
import nivaldo.dh.exercise.firebase.shared.data.Response

class GameBusiness {

    private val gameRepository by lazy {
        GameRepository()
    }

    suspend fun getGamesList(): Response {
        return gameRepository.getGamesList()
    }

}