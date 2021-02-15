package nivaldo.dh.exercise.firebase.home.model.business

import nivaldo.dh.exercise.firebase.home.model.repository.HomeRepository
import nivaldo.dh.exercise.firebase.shared.data.Response

class HomeBusiness {

    private val repository by lazy {
        HomeRepository()
    }

    fun signOutUser(): Response {
        return repository.signOutUser()
    }

}