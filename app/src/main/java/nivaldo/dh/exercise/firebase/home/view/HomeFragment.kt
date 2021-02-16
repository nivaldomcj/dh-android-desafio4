package nivaldo.dh.exercise.firebase.home.view

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
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
import com.mancj.materialsearchbar.MaterialSearchBar
import nivaldo.dh.exercise.firebase.R
import nivaldo.dh.exercise.firebase.databinding.FragmentHomeBinding
import nivaldo.dh.exercise.firebase.home.model.Game
import nivaldo.dh.exercise.firebase.home.view.adapter.HomeGamesListAdapter
import nivaldo.dh.exercise.firebase.home.viewmodel.HomeViewModel
import java.util.*

class HomeFragment : Fragment() {

    companion object {
        const val RC_SPEECH_INPUT = 32
    }

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private var userGamesList: MutableList<Game> = mutableListOf()

    private fun handleSpeechRecognizerResult(result: ArrayList<String>?) {
        result?.let {
            binding.searchBar.openSearch()
            binding.searchBar.text = result.first()
        }
    }

    private fun openSpeechRecognizerActivity() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US")
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "pt-BR")
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)

        startActivityForResult(intent, RC_SPEECH_INPUT)
    }

    private fun initSearchBar() {
        // menu
        binding.searchBar.inflateMenu(R.menu.search_bar_menu)
        binding.searchBar.menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_sign_out -> homeViewModel.signOutUser()
            }

            return@setOnMenuItemClickListener false
        }

        // search
        val searchListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.pbLoadingContent.visibility = View.VISIBLE
                binding.rvUserGamesList.visibility = View.INVISIBLE

                if (count > 0) {
                    homeViewModel.filterUserGamesList(s.toString())
                } else {
                    homeViewModel.getUserGamesList()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        binding.searchBar.addTextChangeListener(searchListener)

        // speech
        val speechListener = object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {
            }

            override fun onSearchConfirmed(text: CharSequence?) {
            }

            override fun onButtonClicked(buttonCode: Int) {
                when (buttonCode) {
                    MaterialSearchBar.BUTTON_SPEECH -> openSpeechRecognizerActivity()
                }
            }
        }
        binding.searchBar.setOnSearchActionListener(speechListener)
    }

    private fun initObservables() {
        homeViewModel.onFetchUserGamesListSuccess.observe(viewLifecycleOwner, {
            userGamesList.clear()
            userGamesList.addAll(it)

            binding.pbLoadingContent.visibility = View.GONE
            binding.rvUserGamesList.visibility = View.VISIBLE

            binding.rvUserGamesList.adapter?.notifyDataSetChanged()
        })
        homeViewModel.onFetchUserGamesListFailure.observe(viewLifecycleOwner, {
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

        binding.rvUserGamesList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = HomeGamesListAdapter(userGamesList) {
                val action = HomeFragmentDirections.actionHomeFragmentToGameDetailFragment(it)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SPEECH_INPUT && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            handleSpeechRecognizerResult(result)
        }
    }
}