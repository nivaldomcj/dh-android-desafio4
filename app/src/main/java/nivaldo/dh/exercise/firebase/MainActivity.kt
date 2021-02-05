package nivaldo.dh.exercise.firebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nivaldo.dh.exercise.firebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}