package id.scodeid.kotlin_setup_starterpackjelly2021.ui.auth.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.scodeid.kotlin_setup_starterpackjelly2021.ui.auth.signup.view.RegisterView


class RegisterViewModelFactory(private val view: RegisterView) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(view) as T
    }
}