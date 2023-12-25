package domain

import data.MovieDao
import data.Places
import data.SIZE_M
import data.SessionDao
import presentation.model.OutputModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface SessionController {
    fun addSession(timeStart: String, movieId: Int): OutputModel
    fun changeTimeStart(id: Int, newTime: String): OutputModel
    fun changeMovie(id: Int, movieId: Int): OutputModel
    fun buyPlace(id: Int, x: Int, y: Int): OutputModel
    fun returnPlace(id: Int, x: Int, y: Int): OutputModel
    fun takePlace(id: Int, x: Int, y: Int): OutputModel
    fun getAllSessions(): OutputModel
    fun getBoughtPlaces(id: Int): OutputModel
    fun getFreePlaces(id: Int): OutputModel
    fun getAllPlaces(id: Int): OutputModel
}

class SessionControllerImpl(
    private val movieDao: MovieDao,
    private val sessionValidator: SessionValidator,
    private val sessionDao: SessionDao
) : SessionController {
    override fun addSession(timeStart: String, movieId: Int): OutputModel {
        val movie = movieDao.get(movieId) ?: return OutputModel("Incorrect movie id")
        val startTimes = sessionDao.getAll().map { it.timeStart }.toList()
        val durations = sessionDao.getAll().map { movieDao.get(it.movieId)!!.duration }.toList()
        return when (val result =
            sessionValidator.validateTimeStart(timeStart, movie.duration, startTimes, durations)) {
            Success -> {
                sessionDao.add(LocalDateTime.parse(timeStart, DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss")), movieId)
                OutputModel("Added the session")
            }

            is Error -> result.outputModel
        }
    }

    override fun changeTimeStart(id: Int, newTime: String): OutputModel {
        val session = sessionDao.get(id) ?: return OutputModel("Incorrect session id")
        val movie = movieDao.get(session.movieId) ?: return OutputModel("Incorrect movie id")
        val startTimes = sessionDao.getAll().filter { it.id != id }.map { it.timeStart }.toList()
        val durations = sessionDao.getAll().filter { it.id != id }.map { movieDao.get(it.movieId)!!.duration }.toList()
        return when (val result =
            sessionValidator.validateTimeStart(newTime, movie.duration, startTimes, durations)) {
            Success -> {
                val newSession = session.copy(timeStart = LocalDateTime.parse(newTime, DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss")))
                sessionDao.update(newSession)
                OutputModel("The start time is changed")
            }

            is Error -> result.outputModel
        }
    }

    override fun changeMovie(id: Int, movieId: Int): OutputModel {
        val session = sessionDao.get(id) ?: return OutputModel("Incorrect session id")
        movieDao.get(movieId) ?: return OutputModel("Incorrect movie id")
        val newSession = session.copy(movieId = movieId)
        sessionDao.update(newSession)
        return OutputModel("Movie for the session is changed")
    }

    override fun buyPlace(id: Int, x: Int, y: Int): OutputModel {
        val session = sessionDao.get(id) ?: return OutputModel("Incorrect session id")
        return when (val result = sessionValidator.validatePlace(x, y)) {
            Success -> {
                if (session.places[x][y] == Places.Free) {
                    session.places[x][y] = Places.Bought
                    OutputModel("Successfully bought the place")
                } else {
                    OutputModel("The place is already bought")
                }
            }

            is Error -> result.outputModel
        }
    }

    override fun returnPlace(id: Int, x: Int, y: Int): OutputModel {
        val session = sessionDao.get(id) ?: return OutputModel("Incorrect session id")
        return when (val result = sessionValidator.validatePlace(x, y)) {
            Success -> {
                if (session.places[x][y] != Places.Free) {
                    session.places[x][y] = Places.Free
                    OutputModel("Successfully returned the ticket")
                } else {
                    OutputModel("The place is not bought")
                }
            }

            is Error -> result.outputModel
        }
    }

    override fun takePlace(id: Int, x: Int, y: Int): OutputModel {
        val session = sessionDao.get(id) ?: return OutputModel("Incorrect session id")
        return when (val result = sessionValidator.validatePlace(x, y)) {
            Success -> {
                when (session.places[x][y]) {
                    Places.Free -> OutputModel("The place is not bought")
                    Places.Bought -> {
                        session.places[x][y] = Places.Taken
                        OutputModel("You can take your place")
                    }

                    Places.Taken -> OutputModel("The place is already taken")
                }
            }

            is Error -> result.outputModel
        }
    }

    override fun getAllSessions(): OutputModel {
        val sessions = sessionDao.getAll().joinToString { it.toString() + " " + movieDao.get(it.movieId).toString() }
        return OutputModel(sessions).takeIf { it.message.isNotEmpty() } ?: OutputModel("List of sessions is empty")
    }

    override fun getBoughtPlaces(id: Int): OutputModel {
        val session = sessionDao.get(id) ?: return OutputModel("Incorrect session id")
        var res = ""
        for (i in session.places.indices) {
            for (j in session.places[i].indices) {
                if (session.places[i][j] != Places.Free) {
                    res += "Row ${i + 1} Place ${j + 1}\n"
                }
            }
        }
        return OutputModel(res)
    }

    override fun getFreePlaces(id: Int): OutputModel {
        val session = sessionDao.get(id) ?: return OutputModel("Incorrect session id")
        var res = ""
        for (i in session.places.indices) {
            for (j in session.places[i].indices) {
                if (session.places[i][j] == Places.Free) {
                    res += "Row ${i + 1} Place ${j + 1}\n"
                }
            }
        }
        return OutputModel(res)
    }

    override fun getAllPlaces(id: Int): OutputModel {
        val session = sessionDao.get(id) ?: return OutputModel("Incorrect session id")
        var res = "=======Screen======="
        val freeColor = "\u26aa"
        val boughtColor = "\u26ab"
        val takenColor = "\ud83d\udd35"
        for (i in session.places.indices) {
            res += "Row ${i + 1}    "
            for (j in session.places[i].indices) {
                res += when (session.places[i][j]) {
                    Places.Free -> freeColor
                    Places.Bought -> boughtColor
                    Places.Taken -> takenColor
                }
                res += " "
            }
            res += "\n"
        }
        res += "Place "
        for (i in 1..SIZE_M) {
            res += "$i "
        }
        return OutputModel(res)
    }
}