package nivaldo.dh.exercise.firebase.auth.model.repository

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import nivaldo.dh.exercise.firebase.auth.model.User
import nivaldo.dh.exercise.firebase.shared.constant.FirestoreConstants
import nivaldo.dh.exercise.firebase.shared.data.Response

class AuthRepository {

    private val authentication by lazy {
        Firebase.auth
    }
    private val usersCollection by lazy {
        Firebase.firestore.collection(FirestoreConstants.COLLECTION_USERS)
    }

    private suspend fun createUserOnFirestore(userUid: String, userName: String): Response {
        return try {
            val newUser = User(userUid, userName)

            usersCollection.document(userUid)
                .set(newUser, SetOptions.merge())
                .await()

            Response.Success(newUser)
        } catch (e: FirebaseFirestoreException) {
            Response.Failure(e.localizedMessage)
        }
    }

    suspend fun registerUser(name: String, email: String, password: String): Response {
        return try {
            val result = authentication.createUserWithEmailAndPassword(email, password).await()

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