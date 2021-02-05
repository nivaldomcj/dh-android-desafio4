package nivaldo.dh.exercise.firebase.auth.business

import nivaldo.dh.exercise.firebase.auth.model.SplashModel
import nivaldo.dh.exercise.firebase.auth.repository.SplashRepository

class SplashBusiness {

    private val repository by lazy {
        SplashRepository()
    }

    fun getSplashResult(): SplashModel {
        return repository.getSplashResult()
    }

}