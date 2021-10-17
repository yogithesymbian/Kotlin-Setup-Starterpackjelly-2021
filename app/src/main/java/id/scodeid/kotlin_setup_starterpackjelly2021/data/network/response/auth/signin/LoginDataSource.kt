package id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.auth.signin

import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.Result
import java.io.IOException
import java.util.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUserResponse> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUserResponse(UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}