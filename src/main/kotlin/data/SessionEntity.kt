package data

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


const val SIZE_N = 10
const val SIZE_M = 10

enum class Places {
    Free, Bought, Taken

}
@Serializable
data class SessionEntity(
    val id: Int,
    val timeStart: LocalDateTime,
    val movieId: Int
) {
    val places = Array(SIZE_N) { Array(SIZE_M) { Places.Free } }

    override fun toString(): String {
        return "Session. Id: $id starts: $timeStart movie: $movieId"
    }
}
