package id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.score

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class ScoreResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
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
	val deleteFlag: @RawValue Any? = null,

	@field:SerializedName("question_id")
	val questionId: Int? = null,

	@field:SerializedName("user")
	val user: User? = null
) : Parcelable

data class Data(

	@field:SerializedName("scores")
	val scores: MutableList<ScoresItem>
)

@Parcelize
data class User(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("hit")
	val hit: @RawValue Any? = null,

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
	val deleteFlag: @RawValue Any? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
