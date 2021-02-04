package nivaldo.dh.exercise.firebase.auth.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import nivaldo.dh.exercise.firebase.auth.viewmodel.SplashViewModel
import nivaldo.dh.exercise.firebase.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var splashViewModel: SplashViewModel

    private lateinit var binding: FragmentSplashBinding


    private fun setSplashObservable() {
        splashViewModel.initSplashScreen()
        splashViewModel.splashResult.observe(this, {
            val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            findNavController().navigate(action)
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        setSplashObservable()
    }

}