package nivaldo.dh.exercise.firebase.auth.model.business

import nivaldo.dh.exercise.firebase.auth.model.repository.SplashRepository
import nivaldo.dh.exercise.firebase.shared.data.Response

class SplashBusiness {

    private val repository by lazy {
        SplashRepository()
    }

    fun isUserSignedIn(): Response {
        return repository.isUserSignedIn()
    }

}