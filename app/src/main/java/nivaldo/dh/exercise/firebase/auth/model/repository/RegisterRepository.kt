package nivaldo.dh.exercise.firebase.auth.model.repository

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import nivaldo.dh.exercise.firebase.auth.model.User
import nivaldo.dh.exercise.firebase.shared.constant.FirebaseFirestoreConstants
import nivaldo.dh.exercise.firebase.shared.model.data.Response

class RegisterRepository {

    private val firebaseAuth by lazy {
        Firebase.auth
    }
    private val firebaseFirestore by lazy {
        Firebase.firestore
    }

    private suspend fun registerUserOnFirestore(userUid: String, userName: String): Response {
        return try {
            // this returns nothing
            firebaseFirestore
                .collection(FirebaseFirestoreConstants.Users.COLLECTION_NAME)
                .document(userUid)
                .set(User(userUid, userName), SetOptions.merge())
                .await()

            Response.Success(true)
        } catch (e: FirebaseFirestoreException) {
            Response.Failure(e.localizedMessage)
        }
    }

    private suspend fun registerUserOnAuthentication(email: String, password: String): Response {
        return try {
            // this returns nothing
            firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()

            Response.Success(true)
        } catch (e: FirebaseAuthException) {
            Response.Failure(e.localizedMessage)
        }
    }

    suspend fun registerUser(name: String, email: String, password: String): Response {
        return try {
            when (val result = registerUserOnAuthentication(email, password)) {
                is Response.Success -> {
                    firebaseAuth.currentUser?.let {
                        registerUserOnFirestore(it.uid, name)
                    } ?: run {
                        Response.Failure("An error occurred. Try again later")
                    }
                }
                is Response.Failure -> {
                    result
                }
            }
        } catch (e: FirebaseException) {
            Response.Failure(e.localizedMessage)
        }
    }

}