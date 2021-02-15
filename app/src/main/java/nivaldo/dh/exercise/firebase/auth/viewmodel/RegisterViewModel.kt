package nivaldo.dh.exercise.firebase.auth.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nivaldo.dh.exercise.firebase.auth.model.business.RegisterBusiness
import nivaldo.dh.exercise.firebase.shared.data.Response

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    // firebase registration methods does not have return
    val onRegisterUserResultSuccess: MutableLiveData<Any> = MutableLiveData()
    val onRegisterUserResultFailure: MutableLiveData<String> = MutableLiveData()

    private val business by lazy { RegisterBusiness() }

    fun registerUser(name: String, email: String, password: String, repeatPassword: String) {
        viewModelScope.launch {
            when (val response = business.registerUser(name, email, password, repeatPassword)) {
                is Response.Success -> onRegisterUserResultSuccess.postValue(response.data)
                is Response.Failure -> onRegisterUserResultFailure.postValue(response.error)
            }
        }
    }

}