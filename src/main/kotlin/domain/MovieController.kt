package domain

import data.MovieDao
import presentation.model.OutputModel
import java.time.Duration

interface MovieController {
    fun addMovie(name: String, director: String, duration: String): OutputModel
    fun changeName(id: Int, name: String): OutputModel
    fun changeDirector(id: Int, director: String): OutputModel
    fun changeDuration(id: Int, duration: String): OutputModel
    fun getAllMovies(): OutputModel
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
                OutputModel("Added the film")
            }
        }
    }

    override fun changeName(id: Int, name: String): OutputModel {
        val movie = movieDao.get(id) ?: return OutputModel("Incorrect movie id")
        return when (val result = movieValidator.validateName(name)) {
            Success -> {
                val updatedMovie = movie.copy(name = name)
                movieDao.update(updatedMovie)
                OutputModel("Changed name of the film")
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
                OutputModel("Changed director of the film")
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
                OutputModel("Changed duration of the film")
            }

            is Error -> result.outputModel
        }
    }

    override fun getAllMovies(): OutputModel {
        val movies = movieDao.getAll().joinToString()
        return OutputModel(movies).takeIf { it.message.isNotEmpty() } ?: OutputModel("List of movies is empty")
    }
}
