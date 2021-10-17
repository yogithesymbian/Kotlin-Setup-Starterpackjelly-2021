package id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.auth.signup

import com.google.gson.annotations.SerializedName

data class User(

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("email")
    val email: String? = null
)