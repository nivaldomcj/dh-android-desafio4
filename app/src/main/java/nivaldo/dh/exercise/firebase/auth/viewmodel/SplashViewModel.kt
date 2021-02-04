package nivaldo.dh.exercise.firebase.auth.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nivaldo.dh.exercise.firebase.auth.model.Splash

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    val splashResult: MutableLiveData<Splash> = MutableLiveData()

    fun initSplashScreen() {
        viewModelScope.launch {
            delay(2000)
            updateSplashResult()
        }
    }

    private fun updateSplashResult() {
        splashResult.postValue(Splash())
    }

}