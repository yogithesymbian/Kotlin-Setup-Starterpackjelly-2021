package id.scodeid.kotlin_setup_starterpackjelly2021.ui.auth.signup.view

import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.auth.signup.User

interface RegisterView {

    fun showLoading()
    fun hideLoading()

    fun showRegisterView(
        data: User?
    )
}