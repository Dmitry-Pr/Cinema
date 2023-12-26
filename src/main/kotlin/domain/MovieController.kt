package domain

import data.MovieDao
import data.MovieEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import presentation.model.OutputModel
import java.io.File
import java.io.FileNotFoundException
import java.lang.Exception
import kotlin.time.Duration

const val MOVIES_JSON_PATH = "data/movies.json"

interface MovieController {
    fun addMovie(name: String, director: String, duration: String): OutputModel
    fun changeName(id: Int, name: String): OutputModel
    fun changeDirector(id: Int, director: String): OutputModel
    fun changeDuration(id: Int, duration: String): OutputModel
    fun getAllMovies(): OutputModel
    fun deserialize(): Result
}

class MovieControllerImpl(
    private val movieDao: MovieDao,
    private val movieValidator: MovieValidator
) : MovieController {
    override fun addMovie(name: String, director: String, duration: String): OutputModel {
        val resultName = movieValidator.validateName(name)
        val resultDirector = movieValidator.validateDirector(director)
        val resultDuration = movieValidator.validateDuration(duration)
        return when {
            resultName is Error -> resultName.outputModel
            resultDuration is Error -> resultDuration.outputModel
            resultDirector is Error -> resultDirector.outputModel
            else -> {
                movieDao.add(name, director, Duration.parse(duration))
                OutputModel("Added the film" + serialize().message)
            }
        }
    }

    override fun changeName(id: Int, name: String): OutputModel {
        val movie = movieDao.get(id) ?: return OutputModel("Incorrect movie id")
        return when (val result = movieValidator.validateName(name)) {
            Success -> {
                val updatedMovie = movie.copy(name = name)
                movieDao.update(updatedMovie)
                OutputModel("Changed name of the film" + serialize().message)
            }

            is Error -> result.outputModel
        }
    }

    override fun changeDirector(id: Int, director: String): OutputModel {
        val movie = movieDao.get(id) ?: return OutputModel("Incorrect movie id")
        return when (val result = movieValidator.validateDirector(director)) {
            Success -> {
                val updatedMovie = movie.copy(director = director)
                movieDao.update(updatedMovie)
                OutputModel("Changed director of the film" + serialize().message)
            }

            is Error -> result.outputModel
        }
    }

    override fun changeDuration(id: Int, duration: String): OutputModel {
        val movie = movieDao.get(id) ?: return OutputModel("Incorrect movie id")
        return when (val result = movieValidator.validateDuration(duration)) {
            Success -> {
                val updatedMovie = movie.copy(duration = Duration.parse(duration))
                movieDao.update(updatedMovie)
                OutputModel("Changed duration of the film" + serialize().message)
            }

            is Error -> result.outputModel
        }
    }

    override fun getAllMovies(): OutputModel {
        val movies = movieDao.getAll().joinToString("\n")
        return OutputModel(movies).takeIf { it.message.isNotEmpty() } ?: OutputModel("List of movies is empty")
    }

    override fun deserialize(): Result {
        return try {
            val file = File(MOVIES_JSON_PATH)
            val jsonString = file.readText()
            val movies = Json.decodeFromString<List<MovieEntity>>(jsonString)
            movieDao.load(movies)
            Success
        } catch (ex: Exception) {
            Error(OutputModel("Unable to load movies data"))
        }
    }

    private fun serialize(): OutputModel {
        return try {
            val file = File(MOVIES_JSON_PATH)
            val jsonString = Json.encodeToString(movieDao.getAll())
            file.writeText(jsonString)
            OutputModel("")
        } catch (ex: FileNotFoundException) {
            OutputModel("\nThe changes are not saved, saving file is not found")
        } catch (ex: Exception) {
            OutputModel("\nThe changes are not saved, unpredicted problem with saving file")
        }
    }
}
