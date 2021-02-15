package nivaldo.dh.exercise.firebase.auth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import nivaldo.dh.exercise.firebase.auth.viewmodel.SplashViewModel
import nivaldo.dh.exercise.firebase.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var splashViewModel: SplashViewModel
    private lateinit var binding: FragmentSplashBinding

    private fun initObservables() {
        splashViewModel.isUserSignedIn()
        splashViewModel.onIsUserSignedInResultSuccess.observe(viewLifecycleOwner, { isSignedIn ->
            if (isSignedIn) {
                val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                findNavController().navigate(action)
            } else {
                val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                findNavController().navigate(action)
            }
        })
        splashViewModel.onIsUserSignedInResultFailure.observe(viewLifecycleOwner, { error ->
            Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
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

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)

        initObservables()
    }

}