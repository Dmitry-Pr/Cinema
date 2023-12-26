package di

import data.*
import domain.*
import presentation.input.*

object DI {

    private val movieValidator: MovieValidator
        get() = MovieValidatorImpl()

    private val sessionValidator: SessionValidator
        get() = SessionValidatorImpl()

    private val userValidator: UserValidator
        get() = UserValidatorImpl()

    private val menu: Menu
        get() = MenuImpl()

    private val movieDao: MovieDao by lazy {
        RuntimeMovieDao()
    }
    private val sessionDao: SessionDao by lazy {
        RuntimeSessionDao()
    }

    private val userDao: UserDao by lazy {
        RuntimeUserDao()
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

    private val userController: UserController
        get() = UserControllerImpl(
            userDao = userDao,
            userValidator = userValidator
        )

    private val commandsHandler: CommandsHandler
        get() = CommandsHandlerImpl(movieController, sessionController, userController)

    val menuHandler: MenuHandler
        get() = MenuHandlerImpl(commandsHandler, menu)
}