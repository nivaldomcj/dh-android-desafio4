package nivaldo.dh.exercise.firebase.auth.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nivaldo.dh.exercise.firebase.auth.model.User
import nivaldo.dh.exercise.firebase.auth.model.business.AuthBusiness
import nivaldo.dh.exercise.firebase.shared.data.Response

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    val onRegisterUserSuccess: MutableLiveData<User> = MutableLiveData()
    val onRegisterUserFailure: MutableLiveData<String> = MutableLiveData()

    private val business by lazy {
        AuthBusiness()
    }

    fun registerUser(name: String, email: String, password: String, repeatPassword: String) {
        viewModelScope.launch {
            when (val response = business.registerUser(name, email, password, repeatPassword)) {
                is Response.Success -> {
                    val user = response.data as? User
                    onRegisterUserSuccess.postValue(user)
                }
                is Response.Failure -> {
                    onRegisterUserFailure.postValue(response.error)
                }
            }
        }
    }

}