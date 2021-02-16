package nivaldo.dh.exercise.firebase.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import nivaldo.dh.exercise.firebase.R
import nivaldo.dh.exercise.firebase.databinding.FragmentEditGameBinding
import nivaldo.dh.exercise.firebase.home.model.Game

class EditGameFragment : Fragment() {

    private lateinit var binding: FragmentEditGameBinding
    private val args: EditGameFragmentArgs by navArgs()

    private fun loadGameInformation(game: Game) {
        binding.tieName.setText(game.title)
        binding.tieReleaseYear.setText(game.releaseYear.toString())
        binding.tieDescription.setText(game.description)

        Glide.with(this)
            .load(game.mImageStoragePath)
            .centerCrop()
            .into(binding.ivGameImage)
    }

    private fun initComponents() {
        args.game?.let {
            binding.btnSaveGame.text = getString(R.string.edit_game)
        } ?: run {
            binding.btnSaveGame.text = getString(R.string.create_game)
        }

        binding.ivGameImage.setOnClickListener {

        }

        binding.btnSaveGame.setOnClickListener {

        }
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

}