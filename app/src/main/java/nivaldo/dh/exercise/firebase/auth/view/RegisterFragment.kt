package nivaldo.dh.exercise.firebase.auth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import nivaldo.dh.exercise.firebase.auth.viewmodel.RegisterViewModel
import nivaldo.dh.exercise.firebase.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding

    private fun initObservables() {
        registerViewModel.onRegisterUserSuccess.observe(viewLifecycleOwner, {
            // splash sets a loading and moves user to next authorized screen
            val action = RegisterFragmentDirections.actionRegisterFragmentToSplashFragment()
            findNavController().navigate(action)
        })
        registerViewModel.onRegisterUserFailure.observe(viewLifecycleOwner, {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
        })
    }

    private fun initComponents() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val repeatPassword = binding.etRepeatPassword.text.toString()

            registerViewModel.registerUser(name, email, password, repeatPassword)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        initComponents()
        initObservables()
    }

}