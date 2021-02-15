package nivaldo.dh.exercise.firebase.auth.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import nivaldo.dh.exercise.firebase.auth.viewmodel.LoginViewModel
import nivaldo.dh.exercise.firebase.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    private fun initObservables() {
        loginViewModel.onSignInUserSuccess.observe(viewLifecycleOwner, { userEmail ->
            if (binding.cbRemember.isChecked)
                loginViewModel.saveLoginCredentials(userEmail)
            else
                loginViewModel.deleteSavedLoginCredentials()

            val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            findNavController().navigate(action)
        })
        loginViewModel.onSignInUserFailure.observe(viewLifecycleOwner, { error ->
            Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
        })

        loginViewModel.onGetSavedLoginCredentialsSuccess.observe(viewLifecycleOwner, {
            binding.etEmail.setText(it)
        })
        loginViewModel.onGetSavedLoginCredentialsFailure.observe(viewLifecycleOwner, {
            Log.i("NMCJ", "getSavedLoginCredentials() [FAIL] Error: $it")
        })

        loginViewModel.onSaveLoginCredentialsSuccess.observe(viewLifecycleOwner, {
            Log.d("NMCJ", "deleteSavedLoginCredentials() [OK] Success")
        })
        loginViewModel.onSaveLoginCredentialsFailure.observe(viewLifecycleOwner, {
            Log.e("NMCJ", "saveLoginCredentials() [FAIL] Error: $it")
        })

        loginViewModel.onDeleteSavedLoginCredentialsSuccess.observe(viewLifecycleOwner, {
            Log.d("NMCJ", "deleteSavedLoginCredentials() [OK] Success")
        })
        loginViewModel.onDeleteSavedLoginCredentialsFailure.observe(viewLifecycleOwner, {
            Log.e("NMCJ", "deleteSavedLoginCredentials() [FAIL] Error: $it")
        })
    }

    private fun initComponents() {
        loginViewModel.getSavedLoginCredentials()

        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            loginViewModel.signInUser(email, password)
        }

        binding.btnCreateAccount.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        initComponents()
        initObservables()
    }

}