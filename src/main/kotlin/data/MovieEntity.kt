package data

import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class MovieEntity(
    val id: Int,
    val name: String,
    val director: String,
    val duration: Duration
) {
    override fun toString(): String {
        return "Movie. id: $id name: $name director: $director duration: ${duration.toString().drop(2)}"
    }
}
