package nivaldo.dh.exercise.firebase.home.model.business

import android.graphics.Bitmap
import nivaldo.dh.exercise.firebase.home.model.repository.EditGameRepository
import nivaldo.dh.exercise.firebase.shared.data.Response
import java.io.ByteArrayOutputStream

class EditGameBusiness {

    private val repository by lazy {
        EditGameRepository()
    }

    fun editGame(
        name: String,
        releaseYear: String,
        description: String,
        imageBitmap: Bitmap
    ): Response {
        return Response.Success(null)
    }

    suspend fun saveGame(
        name: String,
        releaseYear: String,
        description: String,
        imageBitmap: Bitmap?
    ): Response {
        // check all fields
        if (name.isBlank()) {
            return Response.Failure("Name field is required")
        }
        if (releaseYear.isBlank()) {
            return Response.Failure("Release Year field is required")
        }
        if (description.isBlank()) {
            return Response.Failure("Description field is required")
        }
        if (imageBitmap == null) {
            return Response.Failure("Game Image is required")
        }

        // compress image
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        return repository.saveGame(
            title = name.trim(),
            releaseYear = releaseYear.toInt(),
            description = description.trim(),
            imageBytes = baos.toByteArray()
        )
    }

}