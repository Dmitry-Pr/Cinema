package data

import java.time.Duration

data class MovieEntity(
    val id: Int,
    val name: String,
    val director: String,
    val duration: Duration
) {
    override fun toString(): String {
        return "Movie: $id name: $name director: $director duration: ${duration.toString()}"
    }
}
