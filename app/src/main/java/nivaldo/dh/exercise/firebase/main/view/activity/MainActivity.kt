package nivaldo.dh.exercise.firebase.main.view.activity

import android.os.Bundle
import nivaldo.dh.exercise.firebase.BaseActivity
import nivaldo.dh.exercise.firebase.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}