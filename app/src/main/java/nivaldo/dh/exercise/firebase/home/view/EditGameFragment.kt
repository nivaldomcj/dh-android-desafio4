package nivaldo.dh.exercise.firebase.home.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import nivaldo.dh.exercise.firebase.R
import nivaldo.dh.exercise.firebase.databinding.FragmentEditGameBinding
import nivaldo.dh.exercise.firebase.home.model.Game

class EditGameFragment : Fragment() {

    companion object {
        const val RC_IMAGE_CAPTURE = 1024
        const val RC_CHOOSE_GALLERY = 2048
    }

    private lateinit var binding: FragmentEditGameBinding
    private val args: EditGameFragmentArgs by navArgs()

    private fun openChooseImageDialog() {
        val options = arrayOf(
            getString(R.string.action_take_photo),
            getString(R.string.action_choose_gallery),
        )

        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.title_select_image)
                .setItems(options) { _, item ->
                    when (options[item]) {
                        getString(R.string.action_take_photo) -> {
                            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                                startActivityForResult(takePictureIntent, RC_IMAGE_CAPTURE)
                            }
                        }
                        getString(R.string.action_choose_gallery) -> {
//                            val chooseGallery = Intent(
//                                Intent.ACTION_PICK,
//                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//                            )
//                            startActivityForResult(chooseGallery, RC_CHOOSE_GALLERY)
                        }
                    }
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun initComponents() {
        args.game?.let {
            binding.btnSaveGame.text = getString(R.string.edit_game)
        } ?: run {
            binding.btnSaveGame.text = getString(R.string.save_game)
        }

        binding.ivGameImage.setOnClickListener {
            openChooseImageDialog()
        }

        binding.btnSaveGame.setOnClickListener {

        }
    }

    private fun loadGameInformation(game: Game) {
        binding.tieName.setText(game.title)
        binding.tieReleaseYear.setText(game.releaseYear.toString())
        binding.tieDescription.setText(game.description)

        Glide.with(this)
            .load(game.mImageStoragePath)
            .centerCrop()
            .into(binding.ivGameImage)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditGameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // editing a game?
        args.game?.let {
            loadGameInformation(it)
        }

        initComponents()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                RC_IMAGE_CAPTURE -> {
                    data?.extras?.get("data").let { imageTaken ->
                        binding.ivGameImage.setImageBitmap(imageTaken as? Bitmap)
                    }
                }
                RC_CHOOSE_GALLERY -> {
//                    data?.data?.let { selectedImage ->
//                        Log.i("OOPS", "onActivityResult: $selectedImage")
//                    }
                }
            }
        }
    }

}