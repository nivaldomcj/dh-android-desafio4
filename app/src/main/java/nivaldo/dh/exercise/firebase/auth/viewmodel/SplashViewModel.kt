package nivaldo.dh.exercise.firebase.auth.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nivaldo.dh.exercise.firebase.auth.model.Splash
import nivaldo.dh.exercise.firebase.auth.model.business.SplashBusiness

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    val onSplashResult: MutableLiveData<Splash> = MutableLiveData()

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