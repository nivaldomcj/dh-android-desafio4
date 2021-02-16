package nivaldo.dh.exercise.firebase.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import nivaldo.dh.exercise.firebase.R
import nivaldo.dh.exercise.firebase.databinding.FragmentGameDetailBinding

class GameDetailFragment : Fragment() {

    private lateinit var binding: FragmentGameDetailBinding
    private val args: GameDetailFragmentArgs by navArgs()

    private fun loadGameDetail() {
        args.game.let { game ->
            binding.tvGameTitle.text = game.title

            Glide.with(this)
                .load(game.mImageStoragePath)
                .centerCrop()
                .into(binding.ivGameCover)

            binding.tvGameDescription.text = game.description
            binding.tvGameReleaseYear.text = getString(R.string.fmt_release_year, game.releaseYear)
        }
    }

    private fun initComponents() {
        binding.fabEditGame.setOnClickListener {
            args.game.let {
                val action = GameDetailFragmentDirections
                    .actionGameDetailFragmentToEditGameFragment(it)
                findNavController().navigate(action)
            }
        }
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

        initActionBar()
        initComponents()
        loadGameDetail()
    }
}