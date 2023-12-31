package data

import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    val id: Int,
    val name: String,
    val surname: String,
    val login: String,
    val password: String
)
