package data

import kotlinx.datetime.LocalDateTime


interface SessionDao {
    fun add(timeStart: LocalDateTime, movieId: Int)
    fun getAll(): List<SessionEntity>
    fun get(id: Int): SessionEntity?
    fun update(vararg listSession: SessionEntity)
    fun load(sessions: List<SessionEntity>)
}

class RuntimeSessionDao : SessionDao {
    private var sessions = mutableMapOf<Int, SessionEntity>()
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

    override fun load(sessions: List<SessionEntity>) {
        sessions.forEach { add(it.timeStart, it.movieId) }
    }

}
