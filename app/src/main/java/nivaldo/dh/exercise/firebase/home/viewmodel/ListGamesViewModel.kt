package nivaldo.dh.exercise.firebase.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nivaldo.dh.exercise.firebase.home.model.GameModel
import nivaldo.dh.exercise.firebase.home.model.business.GameBusiness
import nivaldo.dh.exercise.firebase.shared.data.Response

class ListGamesViewModel(application: Application) : AndroidViewModel(application) {

    val onGetGamesListSuccess: MutableLiveData<List<GameModel>> = MutableLiveData()
    val onGetGamesListFailure: MutableLiveData<String> = MutableLiveData()

    private val gameBusiness by lazy {
        GameBusiness()
    }

    fun getGamesList() {
        viewModelScope.launch {
            when (val response = gameBusiness.getGamesList()) {
                is Response.Success -> {
                    @Suppress("UNCHECKED_CAST")
                    onGetGamesListSuccess.postValue(response.data as? List<GameModel>)
                }
                is Response.Failure -> {
                    onGetGamesListFailure.postValue(response.error)
                }
            }
        }
    }

}