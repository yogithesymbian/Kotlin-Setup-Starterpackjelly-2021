package id.scodeid.kotlin_setup_starterpackjelly2021.ui.score.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.scodeid.kotlin_setup_starterpackjelly2021.R
import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.score.ScoresItem
import id.scodeid.kotlin_setup_starterpackjelly2021.databinding.ActivityScoreDetailBinding

class ScoreDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreDetailBinding
    private var scoresItem: ScoresItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.also {
            scoresItem = it.getParcelableExtra(EXTRA_SCORE_DATA)
        }

        binding.txtDetail.text = scoresItem?.score.toString()
    }


    companion object {
        val TAG_LOG = ScoreDetailActivity::class.java.simpleName
        const val EXTRA_SCORE_DATA = "extra_score_data"
    }
}