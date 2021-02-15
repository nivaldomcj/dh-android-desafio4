package nivaldo.dh.exercise.firebase.auth.model.business

import nivaldo.dh.exercise.firebase.auth.model.repository.RegisterRepository
import nivaldo.dh.exercise.firebase.shared.data.Response
import nivaldo.dh.exercise.firebase.shared.extension.isValidEmailAddress

class RegisterBusiness {

    private val repository by lazy { RegisterRepository() }

    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        repeatPassword: String
    ): Response {
        if (name.isBlank()) {
            return Response.Failure("Name field is required")
        }

        if (email.isBlank()) {
            return Response.Failure("E-mail field is required")
        }
        if (!email.isValidEmailAddress()) {
            return Response.Failure("Email is not valid")
        }

        if (password.isBlank()) {
            return Response.Failure("Password field is required")
        }
        if (repeatPassword.isBlank()) {
            return Response.Failure("Repeat Password field is required")
        }
        if (password != repeatPassword) {
            return Response.Failure("Password and Repeat Password does not match")
        }

        return repository.registerUser(name.trim(), email.trim(), password)
    }

}