package nivaldo.dh.exercise.firebase.auth.model.business

import android.content.Context
import nivaldo.dh.exercise.firebase.auth.model.repository.LoginRepository
import nivaldo.dh.exercise.firebase.shared.data.Response
import nivaldo.dh.exercise.firebase.shared.extension.isValidEmailAddress

class LoginBusiness(context: Context) {

    private val repository by lazy {
        LoginRepository(context)
    }

    fun getSavedLoginCredentials(): Response {
        return repository.getSavedLoginCredentials()
    }

    fun saveLoginCredentials(email: String): Response {
        return repository.saveLoginCredentials(email.trim())
    }

    fun deleteSavedLoginCredentials(): Response {
        return repository.deleteSavedLoginCredentials()
    }

    suspend fun signInUser(email: String, password: String): Response {
        if (email.isBlank()) {
            return Response.Failure("E-mail field is required")
        }
        if (!email.isValidEmailAddress()) {
            return Response.Failure("Email is not valid")
        }

        if (password.isBlank()) {
            return Response.Failure("Password field is required")
        }

        return repository.signInUser(email.trim(), password)
    }

}