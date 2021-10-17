package id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.auth.signin

import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.Result

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var userResponse: LoggedInUserResponse? = null
        private set

    val isLoggedIn: Boolean
        get() = userResponse != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        userResponse = null
    }

    fun logout() {
        userResponse = null
        dataSource.logout()
    }

    fun login(username: String, password: String): Result<LoggedInUserResponse> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUserResponse: LoggedInUserResponse) {
        this.userResponse = loggedInUserResponse
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}