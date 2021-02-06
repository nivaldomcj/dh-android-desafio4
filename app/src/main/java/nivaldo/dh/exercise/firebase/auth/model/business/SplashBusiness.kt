package nivaldo.dh.exercise.firebase.auth.model.business

import nivaldo.dh.exercise.firebase.auth.model.Splash
import nivaldo.dh.exercise.firebase.auth.model.repository.SplashRepository

class SplashBusiness {

    private val repository by lazy {
        SplashRepository()
    }

    fun getSplashResult(): Splash {
        return repository.getSplashResult()
    }

}