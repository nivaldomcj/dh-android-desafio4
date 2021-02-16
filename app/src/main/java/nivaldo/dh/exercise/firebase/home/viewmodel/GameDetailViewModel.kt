package nivaldo.dh.exercise.firebase.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nivaldo.dh.exercise.firebase.home.model.Game
import nivaldo.dh.exercise.firebase.home.model.business.GameDetailBusiness
import nivaldo.dh.exercise.firebase.shared.data.Response

class GameDetailViewModel(application: Application) : AndroidViewModel(application) {

    val onGameDataResultSuccess: MutableLiveData<Game> = MutableLiveData()
    val onGameDataResultFailure: MutableLiveData<String> = MutableLiveData()

    private val business by lazy {
        GameDetailBusiness()
    }

    fun getGameData(gameUid: String) {
        viewModelScope.launch {
            when (val response = business.getGameData(gameUid)) {
                is Response.Success -> {
                    onGameDataResultSuccess.postValue(response.data as Game)
                }
                is Response.Failure -> {
                    onGameDataResultFailure.postValue(response.error)
                }
            }
        }
    }

}