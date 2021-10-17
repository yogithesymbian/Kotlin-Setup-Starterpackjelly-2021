package id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.auth.signin

/**
 * id.scodeid.mytesting.data.network.response.auth.signup.User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String
    //... other data fields that may be accessible to the UI
)