package nivaldo.dh.exercise.firebase.auth.view

import android.os.Bundle
import nivaldo.dh.exercise.firebase.BaseActivity
import nivaldo.dh.exercise.firebase.databinding.ActivityAuthBinding

class AuthActivity : BaseActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}