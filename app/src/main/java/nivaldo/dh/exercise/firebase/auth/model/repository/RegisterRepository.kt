package nivaldo.dh.exercise.firebase.auth.model.repository

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import nivaldo.dh.exercise.firebase.auth.model.UserModel
import nivaldo.dh.exercise.firebase.shared.constants.FirestoreConstants
import nivaldo.dh.exercise.firebase.shared.data.Response

class RegisterRepository {

    private val firebaseAuth by lazy {
        Firebase.auth
    }
    private val firebaseFirestore by lazy {
        Firebase.firestore
    }

    private suspend fun createUserOnFirestore(userUid: String, userName: String): Response {
        return try {
            val newUser = UserModel(userUid, userName)

            firebaseFirestore
                .collection(FirestoreConstants.COLLECTION_USERS)
                .document(userUid)
                .set(newUser, SetOptions.merge())
                .await()

            Response.Success(newUser)
        } catch (e: FirebaseFirestoreException) {
            Response.Failure(e.localizedMessage)
        }
    }

    suspend fun registerUser(name: String, email: String, password: String): Response {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            result.user?.let {
                createUserOnFirestore(it.uid, name)
            } ?: run {
                Response.Failure("An error occurred while registering user. Try again later")
            }
        } catch (e: FirebaseAuthException) {
            Response.Failure(e.localizedMessage)
        }
    }

}