package nivaldo.dh.exercise.firebase.auth.model.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import nivaldo.dh.exercise.firebase.shared.constant.SharedPreferencesConstants
import nivaldo.dh.exercise.firebase.shared.data.Response

class LoginRepository(context: Context) {

    private val firebaseAuth by lazy {
        Firebase.auth
    }
    private val sharedPreferences by lazy {
        context.getSharedPreferences(SharedPreferencesConstants.NAME, MODE_PRIVATE)
    }

    fun getSavedLoginCredentials(): Response {
        return try {
            val email = sharedPreferences
                .getString(SharedPreferencesConstants.LOGIN_EMAIL, null)

            Response.Success(email)
        } catch (e: Exception) {
            Response.Failure(e.localizedMessage)
        }
    }

    fun saveLoginCredentials(email: String): Response {
        return try {
            // this returns nothing
            sharedPreferences.edit()
                .putString(SharedPreferencesConstants.LOGIN_EMAIL, email)
                .apply()

            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e.localizedMessage)
        }
    }

    fun deleteSavedLoginCredentials(): Response {
        return try {
            // this returns nothing
            sharedPreferences.edit()
                .remove(SharedPreferencesConstants.LOGIN_EMAIL)
                .apply()

            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e.localizedMessage)
        }
    }

    suspend fun signInUser(email: String, password: String): Response {
        return try {
            val result = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()

            result.user?.let {
                Response.Success(it.email)
            } ?: run {
                Response.Failure("An error occurred. Try again later")
            }
        } catch (e: FirebaseAuthException) {
            Response.Failure(e.localizedMessage)
        }
    }

}