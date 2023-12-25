package di

import data.MovieDao
import data.RuntimeMovieDao
import data.RuntimeSessionDao
import data.SessionDao
import domain.*
import presentation.input.*

object DI {

    private val movieValidator: MovieValidator
        get() = MovieValidatorImpl()

    private val sessionValidator: SessionValidator
        get() = SessionValidatorImpl()

    private val menu: Menu
        get() = MenuImpl()

    private val movieDao: MovieDao by lazy {
        RuntimeMovieDao()
    }
    private val sessionDao: SessionDao by lazy {
        RuntimeSessionDao()
    }

    private val sessionController: SessionController
        get() = SessionControllerImpl(
            movieDao = movieDao,
            sessionValidator = sessionValidator,
            sessionDao = sessionDao
        )

    private val movieController: MovieController
        get() = MovieControllerImpl(
            movieDao = movieDao,
            movieValidator = movieValidator
        )

    private val commandsHandler: CommandsHandler
        get() = CommandsHandlerImpl(movieController, sessionController)

    val menuHandler: MenuHandler
        get() = MenuHandlerImpl(commandsHandler, menu)
}