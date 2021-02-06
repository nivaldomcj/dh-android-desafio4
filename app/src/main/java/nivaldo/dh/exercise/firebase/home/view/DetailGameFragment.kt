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
import nivaldo.dh.exercise.firebase.R
import nivaldo.dh.exercise.firebase.databinding.FragmentDetailGameBinding

class DetailGameFragment : Fragment() {

    private lateinit var binding: FragmentDetailGameBinding
    private val args: DetailGameFragmentArgs by navArgs()

    private fun loadGameDetail() {
        binding.toolbarLayout.title = args.game.title
        binding.tvGameTitle.text = args.game.title

        // TODO set image with Glide

        binding.tvGameDescription.text = args.game.description
        binding.tvGameReleaseYear.text = getString(R.string.fmt_release_year, args.game.releaseYear)
    }

    private fun initComponents() {
        binding.fabEditGame.setOnClickListener {
            // TODO call EditGame
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
        binding = FragmentDetailGameBinding.inflate(layoutInflater, container, false)
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