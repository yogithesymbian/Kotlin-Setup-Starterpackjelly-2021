package id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.auth.signin

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUserResponse(
    val userId: String,
    val displayName: String
)