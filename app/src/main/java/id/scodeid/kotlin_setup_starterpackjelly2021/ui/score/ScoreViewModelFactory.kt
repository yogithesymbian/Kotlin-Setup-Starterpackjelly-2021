package id.scodeid.kotlin_setup_starterpackjelly2021.ui.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ScoreViewModelFactory(private val view:ScoreView) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScoreViewModel(view) as T
    }

}