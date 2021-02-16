package nivaldo.dh.exercise.firebase.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import nivaldo.dh.exercise.firebase.R
import nivaldo.dh.exercise.firebase.databinding.FragmentGameDetailBinding
import nivaldo.dh.exercise.firebase.home.model.Game
import nivaldo.dh.exercise.firebase.home.viewmodel.GameDetailViewModel

class GameDetailFragment : Fragment() {

    private lateinit var binding: FragmentGameDetailBinding
    private lateinit var gameDetailViewModel: GameDetailViewModel
    private val args: GameDetailFragmentArgs by navArgs()

    private fun setupGameDetailsComponents(game: Game) {
        // action of edit this game
        binding.fabEditGame.setOnClickListener {
            val action = GameDetailFragmentDirections
                .actionGameDetailFragmentToEditGameFragment(game)

            findNavController().navigate(action)
        }

        // information of this game
        binding.tvGameTitle.text = game.title

        Glide.with(this)
            .load(game.mImageStoragePath)
            .centerCrop()
            .into(binding.ivGameCover)

        binding.tvGameDescription.text = game.description
        binding.tvGameReleaseYear.text = getString(R.string.fmt_release_year, game.releaseYear)
    }

    private fun initObservables() {
        gameDetailViewModel.onGameDataResultSuccess.observe(viewLifecycleOwner, {
            setupGameDetailsComponents(it)
        })
        gameDetailViewModel.onGameDataResultFailure.observe(viewLifecycleOwner, {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
        })
    }

    private fun initActionBar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        gameDetailViewModel = ViewModelProvider(this).get(GameDetailViewModel::class.java)

        // attempt to retrieve game data
        args.gameUid.let {
            gameDetailViewModel.getGameData(it)
        }

        initActionBar()
        initObservables()
    }
}