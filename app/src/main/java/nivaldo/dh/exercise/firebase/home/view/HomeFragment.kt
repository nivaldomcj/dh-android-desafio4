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
import nivaldo.dh.exercise.firebase.home.view.adapter.GameAdapter
import nivaldo.dh.exercise.firebase.home.viewmodel.ListGamesViewModel

class HomeFragment : Fragment() {

    private lateinit var listGamesViewModel: ListGamesViewModel
    private lateinit var binding: FragmentHomeBinding

    private fun loadGamesRecyclerView(gamesList: List<Game>) {
        // hide loading
        binding.pbLoadingGames.visibility = View.GONE

        // show list of games
        binding.rvGames.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = GameAdapter(gamesList) {
                val action = HomeFragmentDirections
                    .actionHomeFragmentToDetailGameFragment(it)

                findNavController().navigate(action)
            }
        }
    }

    private fun initObservables() {
        listGamesViewModel.getGamesList()
        listGamesViewModel.onGetGamesListSuccess.observe(viewLifecycleOwner, {
            loadGamesRecyclerView(it)
        })
        listGamesViewModel.onGetGamesListFailure.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initComponents() {
        binding.fabCreateGame.setOnClickListener {
            // TODO
        }
        binding.btnSignOut.setOnClickListener {
            // TODO
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

        listGamesViewModel = ViewModelProvider(this).get(ListGamesViewModel::class.java)

        initComponents()
        initObservables()
    }
}