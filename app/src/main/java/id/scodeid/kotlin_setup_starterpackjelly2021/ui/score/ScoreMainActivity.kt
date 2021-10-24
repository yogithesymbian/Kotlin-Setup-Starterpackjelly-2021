package id.scodeid.kotlin_setup_starterpackjelly2021.ui.score

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import id.scodeid.kotlin_setup_starterpackjelly2021.R
import id.scodeid.kotlin_setup_starterpackjelly2021.databinding.ActivityScoreMainBinding

class ScoreMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // instance fragmentManager
        val fragmentManager = supportFragmentManager
        // fragment transaction to operate add(), replace(), commit() , etc
        val mFragmentTransaction = fragmentManager.beginTransaction()

        Log.d("INSTANCE", "Method di instance [ScodeFragment]")
        // create object fragment
        val homeFragment = ScoreFragment()

        val fragment = fragmentManager.findFragmentByTag(ScoreFragment::class.java.simpleName)
        if (fragment !is ScoreFragment) {
            // add()
            mFragmentTransaction.add(R.id.frame_all_container, homeFragment, ScoreFragment::class.java.simpleName)

            Log.d("MyFlexibleFragment", "Fragment Name" + ScoreFragment::class.java.simpleName)

            //commit()
            mFragmentTransaction.commit()
        }
    }
}