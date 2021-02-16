package nivaldo.dh.exercise.firebase.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nivaldo.dh.exercise.firebase.home.model.Game
import nivaldo.dh.exercise.firebase.home.model.business.GameBusiness
import nivaldo.dh.exercise.firebase.home.model.business.HomeBusiness
import nivaldo.dh.exercise.firebase.shared.data.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val onFetchUserGamesListSuccess: MutableLiveData<List<Game>> = MutableLiveData()
    val onFetchUserGamesListFailure: MutableLiveData<String> = MutableLiveData()

    val onSignOutUserSuccess: MutableLiveData<Any> = MutableLiveData()
    val onSignOutUserFailure: MutableLiveData<String> = MutableLiveData()

    private val gameBusiness by lazy {
        GameBusiness()
    }
    private val homeBusiness by lazy {
        HomeBusiness()
    }

    fun getUserGamesList() {
        viewModelScope.launch {
            when (val response = gameBusiness.getUserGamesList()) {
                is Response.Success -> {
                    val gamesList = (response.data as? MutableList<*>)
                    onFetchUserGamesListSuccess.postValue(gamesList?.filterIsInstance<Game>())
                }
                is Response.Failure -> {
                    onFetchUserGamesListFailure.postValue(response.error)
                }
            }
        }
    }

    fun filterUserGamesList(text: String) {
        viewModelScope.launch {
            when (val response = gameBusiness.filterUserGamesList(text)) {
                is Response.Success -> {
                    val gamesList = (response.data as? MutableList<*>)
                    onFetchUserGamesListSuccess.postValue(gamesList?.filterIsInstance<Game>())
                }
                is Response.Failure -> {
                    onFetchUserGamesListFailure.postValue(response.error)
                }
            }
        }
    }

    fun signOutUser() {
        when (val response = homeBusiness.signOutUser()) {
            is Response.Success -> {
                onSignOutUserSuccess.postValue(response.data)
            }
            is Response.Failure -> {
                onSignOutUserFailure.postValue(response.error)
            }
        }
    }

}