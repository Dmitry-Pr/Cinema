package data

import java.time.LocalDateTime


interface SessionDao {
    fun add(timeStart: LocalDateTime, movieId: Int)
    fun getAll(): List<SessionEntity>
    fun get(id: Int): SessionEntity?
    fun update(vararg listSession: SessionEntity)
}

class RuntimeSessionDao : SessionDao {
    private val sessions = mutableMapOf<Int, SessionEntity>()
    private var counter = 0

    override fun add(timeStart: LocalDateTime, movieId: Int) {
        val session = SessionEntity(
            id = counter,
            timeStart = timeStart,
            movieId = movieId
        )
        sessions[counter] = session
        counter++
    }

    override fun getAll(): List<SessionEntity> = sessions.values.toList().sortedBy { it.timeStart }

    override fun get(id: Int): SessionEntity? = sessions[id]

    override fun update(vararg listSession: SessionEntity) =
        listSession.forEach { session -> sessions[session.id] = session }

}
