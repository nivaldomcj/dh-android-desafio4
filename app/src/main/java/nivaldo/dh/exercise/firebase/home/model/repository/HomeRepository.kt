package nivaldo.dh.exercise.firebase.home.model.repository

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nivaldo.dh.exercise.firebase.shared.data.Response

class HomeRepository {

    private val firebaseAuth by lazy {
        Firebase.auth
    }

    fun signOutUser(): Response {
        return try {
            // this returns nothing
            firebaseAuth.signOut()

            Response.Success(null)
        } catch (e: FirebaseAuthException) {
            Response.Failure(e.localizedMessage)
        }
    }

}