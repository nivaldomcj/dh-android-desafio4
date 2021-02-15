package nivaldo.dh.exercise.firebase.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import nivaldo.dh.exercise.firebase.R
import nivaldo.dh.exercise.firebase.databinding.FragmentHomeBinding
import nivaldo.dh.exercise.firebase.home.model.Game
import nivaldo.dh.exercise.firebase.home.view.adapter.HomeGamesListAdapter
import nivaldo.dh.exercise.firebase.home.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private fun initSearchBar() {
        binding.searchBar.setSpeechMode(true)

        // menu
        binding.searchBar.inflateMenu(R.menu.search_bar_menu)
        binding.searchBar.menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_sign_out -> {
                    homeViewModel.signOutUser()
                }
            }

            return@setOnMenuItemClickListener false
        }
    }

    private fun setupHomeGamesListRecyclerView(gamesList: List<Game>) {
        binding.pbLoadingGamesList.visibility = View.GONE

        if (gamesList.isEmpty()) {
            binding.tvPlaceholderEmptyGames.visibility = View.VISIBLE
            return
        }

        binding.rvHomeGamesList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = HomeGamesListAdapter(gamesList) {
                val action = HomeFragmentDirections
                    .actionHomeFragmentToDetailGameFragment(it)

                findNavController().navigate(action)
            }
        }
    }

    private fun initObservables() {
        homeViewModel.onGetGamesListSuccess.observe(viewLifecycleOwner, {
            setupHomeGamesListRecyclerView(it)
        })
        homeViewModel.onGetGamesListFailure.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        homeViewModel.onSignOutUserSuccess.observe(viewLifecycleOwner, {
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(action)
        })
        homeViewModel.onSignOutUserFailure.observe(viewLifecycleOwner, {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
        })
    }

    private fun initComponents() {
        binding.fabCreateGame.setOnClickListener {
            // since a new game will be created, at the moment it does not exist (null)
            val action = HomeFragmentDirections.actionHomeFragmentToEditGameFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.getUserGamesList()

        initComponents()
        initObservables()
        initSearchBar()
    }
}