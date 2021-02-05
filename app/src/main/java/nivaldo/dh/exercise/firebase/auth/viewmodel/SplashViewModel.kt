package nivaldo.dh.exercise.firebase.auth.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nivaldo.dh.exercise.firebase.auth.model.SplashModel
import nivaldo.dh.exercise.firebase.auth.business.SplashBusiness

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    val onSplashResult: MutableLiveData<SplashModel> = MutableLiveData()

    private val business by lazy {
        SplashBusiness()
    }

    fun getSplashScreen() {
        viewModelScope.launch {
            delay(2000)
            setSplashResult()
        }
    }

    private fun setSplashResult() {
        onSplashResult.postValue(business.getSplashResult())
    }

}