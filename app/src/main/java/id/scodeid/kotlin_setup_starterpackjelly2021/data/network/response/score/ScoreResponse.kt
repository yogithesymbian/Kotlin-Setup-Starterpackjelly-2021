package id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.score

import com.google.gson.annotations.SerializedName

data class ScoreResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ScoresItem(

	@field:SerializedName("score")
	val score: Double? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("delete_flag")
	val deleteFlag: Any? = null,

	@field:SerializedName("question_id")
	val questionId: Int? = null,

	@field:SerializedName("user")
	val user: User? = null
)

data class Data(

	@field:SerializedName("scores")
	val scores: MutableList<ScoresItem>
)

data class User(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("hit")
	val hit: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("image_profile")
	val imageProfile: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("delete_flag")
	val deleteFlag: Any? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)