package nivaldo.dh.exercise.firebase.home.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nivaldo.dh.exercise.firebase.home.model.Game
import nivaldo.dh.exercise.firebase.home.model.business.EditGameBusiness
import nivaldo.dh.exercise.firebase.shared.data.Response

class EditGameViewModel(application: Application) : AndroidViewModel(application) {

    val onStoreGameResultSuccess: MutableLiveData<Game> = MutableLiveData()
    val onStoreGameResultFailure: MutableLiveData<String> = MutableLiveData()

    private val business by lazy {
        EditGameBusiness()
    }

    fun editGame(name: String, releaseYear: String, description: String, imageBitmap: Bitmap?) {

    }

    fun saveGame(name: String, releaseYear: String, description: String, imageBitmap: Bitmap?) {
        viewModelScope.launch {
            when (val response = business.saveGame(name, releaseYear, description, imageBitmap)) {
                is Response.Success -> {
                    onStoreGameResultSuccess.postValue(response.data as Game)
                }
                is Response.Failure -> {
                    onStoreGameResultFailure.postValue(response.error)
                }
            }
        }
    }

}