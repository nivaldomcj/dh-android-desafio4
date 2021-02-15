package nivaldo.dh.exercise.firebase.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nivaldo.dh.exercise.firebase.home.model.Game
import nivaldo.dh.exercise.firebase.home.model.business.GameBusiness
import nivaldo.dh.exercise.firebase.shared.data.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val onGetGamesListSuccess: MutableLiveData<List<Game>> = MutableLiveData()
    val onGetGamesListFailure: MutableLiveData<String> = MutableLiveData()

    private val gameBusiness by lazy {
        GameBusiness()
    }

    fun getGamesList() {
        viewModelScope.launch {
            when (val response = gameBusiness.getGamesList()) {
                is Response.Success -> {
                    val gamesList = (response.data as? MutableList<*>)
                    onGetGamesListSuccess.postValue(gamesList?.filterIsInstance<Game>())
                }
                is Response.Failure -> {
                    onGetGamesListFailure.postValue(response.error)
                }
            }
        }
    }

}