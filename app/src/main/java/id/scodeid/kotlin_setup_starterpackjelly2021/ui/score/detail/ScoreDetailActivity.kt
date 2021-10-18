package id.scodeid.kotlin_setup_starterpackjelly2021.ui.score.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.scodeid.kotlin_setup_starterpackjelly2021.R
import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.score.ScoresItem
import kotlinx.android.synthetic.main.activity_score_detail.*

class ScoreDetailActivity : AppCompatActivity() {

    private var scoresItem: ScoresItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_detail)

        intent?.also {
            scoresItem = it.getParcelableExtra(EXTRA_SCORE_DATA)
        }
        txt_detail.text = scoresItem?.score.toString()
    }


    companion object {
        val TAG_LOG = ScoreDetailActivity::class.java.simpleName
        const val EXTRA_SCORE_DATA = "extra_score_data"
    }
}