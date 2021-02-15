package nivaldo.dh.exercise.firebase.auth.model.repository

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nivaldo.dh.exercise.firebase.shared.data.Response

class SplashRepository {

    private val firebaseAuth by lazy { Firebase.auth }

    fun isUserSignedIn(): Response {
        return try {
            firebaseAuth.currentUser?.uid?.let {
                Response.Success(true)
            } ?: run {
                Response.Success(false)
            }
        } catch (e: FirebaseAuthException) {
            Response.Failure(e.localizedMessage)
        }
    }

}