package nivaldo.dh.exercise.firebase.home.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private var completeUserGamesList: MutableList<Game> = mutableListOf()
    private var filteredUserGamesList: MutableList<Game> = mutableListOf()

    private fun initSearchBar() {
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

        // search
        binding.searchBar.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text: String = s.toString().toLowerCase(Locale.ROOT)
                var list: List<Game> = completeUserGamesList

                filteredUserGamesList.clear()

                if (count > 0)
                    list = list.filter { it.title.toLowerCase(Locale.ROOT).startsWith(text) }

                filteredUserGamesList.addAll(list)

                binding.rvHomeGamesList.adapter?.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun initObservables() {
        homeViewModel.onGetGamesListSuccess.observe(viewLifecycleOwner, { gamesList ->
            // notice that user games at initialization will have all data
            // it is not filtered yet, but it can be (so we keep a reference to full list)
            completeUserGamesList = gamesList.toMutableList()
            filteredUserGamesList.addAll(completeUserGamesList)

            binding.rvHomeGamesList.adapter?.notifyDataSetChanged()
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
            //val action = HomeFragmentDirections.actionHomeFragmentToEditGameFragment(null)
            //findNavController().navigate(action)

            val list2 =

                Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show()
        }

        binding.rvHomeGamesList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = HomeGamesListAdapter(filteredUserGamesList) {
                val action = HomeFragmentDirections
                    .actionHomeFragmentToDetailGameFragment(it)

                findNavController().navigate(action)
            }
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