package id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response

import com.google.gson.annotations.SerializedName
import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.auth.signup.User

data class RegisterResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("user")
	val user: User?
)
