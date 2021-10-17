package id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.auth.signin

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)