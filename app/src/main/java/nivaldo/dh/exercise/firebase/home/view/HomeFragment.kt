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
import nivaldo.dh.exercise.firebase.databinding.FragmentHomeBinding
import nivaldo.dh.exercise.firebase.home.model.Game
import nivaldo.dh.exercise.firebase.home.view.adapter.HomeAdapter
import nivaldo.dh.exercise.firebase.home.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private fun loadGamesRecyclerView(gamesList: List<Game>) {
        // hide loading
        binding.pbLoadingGames.visibility = View.GONE

        // show list of games
        binding.rvGames.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = HomeAdapter(gamesList) {
                val action = HomeFragmentDirections
                    .actionHomeFragmentToDetailGameFragment(it)

                findNavController().navigate(action)
            }
        }
    }

    private fun initObservables() {
        homeViewModel.onGetGamesListSuccess.observe(viewLifecycleOwner, {
            loadGamesRecyclerView(it)
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
        binding.btnSignOut.setOnClickListener {
            homeViewModel.signOutUser()
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

        homeViewModel.getGamesList()

        initComponents()
        initObservables()
    }
}