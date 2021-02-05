package nivaldo.dh.exercise.firebase.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import nivaldo.dh.exercise.firebase.databinding.FragmentListGamesBinding
import nivaldo.dh.exercise.firebase.home.viewmodel.ListGamesViewModel

class ListGamesFragment : Fragment() {

    private lateinit var listGamesViewModel: ListGamesViewModel
    private lateinit var binding: FragmentListGamesBinding

    private fun initToolbar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListGamesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        listGamesViewModel = ViewModelProvider(this).get(ListGamesViewModel::class.java)

        initToolbar()
    }
}