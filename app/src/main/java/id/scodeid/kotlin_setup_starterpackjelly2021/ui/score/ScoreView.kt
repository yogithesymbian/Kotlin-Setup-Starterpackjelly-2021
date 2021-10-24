package id.scodeid.kotlin_setup_starterpackjelly2021.ui.score

import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.score.ScoresItem

interface ScoreView {

    fun showLoadingScoreView()
    fun hideLoadingScoreView()
    
    fun paginationTotalPageScoreView(totalPageDef: Int)

//    fun showScoreView(
//        data: MutableList<ScoresItem>?
//    )

//    fun showSingleScoreView(
//        data: ScoresItem?
//    )
}