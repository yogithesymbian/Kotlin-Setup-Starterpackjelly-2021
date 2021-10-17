package id.scodeid.kotlin_setup_starterpackjelly2021

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import id.scodeid.kotlin_setup_starterpackjelly2021.databinding.ActivityMainBinding
import id.scodeid.kotlin_setup_starterpackjelly2021.ui.auth.signin.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtHello.setOnClickListener {
            Log.d("test", "clicked")
            startActivity(Intent(this.applicationContext, LoginActivity::class.java))
        }
    }
}