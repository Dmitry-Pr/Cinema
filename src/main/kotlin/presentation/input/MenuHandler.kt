package presentation.input

import domain.Success
import domain.Error
import presentation.model.OutputModel

enum class Sections {
    Registration, Main, Movie, Session, Places
}

interface MenuHandler {
    fun handleMenu()
    var finish: Boolean
}

class MenuHandlerImpl(
    private val commandsHandler: CommandsHandler,
    private val menu: Menu
) : MenuHandler {
    override var finish = false
    private var current = Sections.Registration
    override fun handleMenu() {
        when (current) {
            Sections.Registration -> registrationCommand()
            Sections.Main -> mainCommand()
            Sections.Movie -> movieCommand()
            Sections.Session -> sessionCommand()
            Sections.Places -> placesCommand()
        }
    }

    private fun mainCommand() {
        println(menu.showMainMenu().message)
        val input = readln()
        when (input) {
            "movie" -> current = Sections.Movie
            "session" -> current = Sections.Session
            "places" -> current = Sections.Places
            "exit" -> finish = true
            else -> {
                println(OutputModel("Incorrect command").message)
            }
        }
    }

    private fun registrationCommand() {
        println(menu.showRegistrationMenu().message)
        val input = readln()
        when (input) {
            "log in" -> {
                when (val res = commandsHandler.logIn()) {
                    Success -> current = Sections.Main
                    is Error -> println(res.outputModel.message)
                }
            }

            "sign up" -> {
                when (val res = commandsHandler.signUp()) {
                    Success -> current = Sections.Main
                    is Error -> println(res.outputModel.message)
                }
            }

            "exit" -> finish = true
            else -> {
                println(OutputModel("Incorrect command").message)
            }
        }
    }

    private fun movieCommand() {
        println(menu.showMoviesMenu().message)
        val input = readln()
        val res = when (input) {
            "add" -> commandsHandler.addMovie()
            "change name" -> commandsHandler.changeMovieName()
            "change director" -> commandsHandler.changeMovieDirector()
            "change duration" -> commandsHandler.changeMovieDuration()
            "show all" -> commandsHandler.showAllMovies()
            "exit" -> {
                current = Sections.Main
                OutputModel("Left movies section")
            }

            else -> OutputModel("Incorrect input")
        }
        println(res.message)
    }

    private fun sessionCommand() {
        println(menu.showSessionsMenu().message)
        val input = readln()
        val res = when (input) {
            "add" -> commandsHandler.addSession()
            "change time" -> commandsHandler.changeSessionTime()
            "change movie" -> commandsHandler.changeSessionMovie()
            "show all" -> commandsHandler.showAllSessions()
            "exit" -> {
                current = Sections.Main
                OutputModel("Left session section")
            }

            else -> OutputModel("Incorrect input")
        }
        println(res.message)
    }

    private fun placesCommand() {
        println(menu.showPlacesMenu().message)
        val input = readln()
        val res = when (input) {
            "buy" -> commandsHandler.buyPlace()
            "return" -> commandsHandler.returnPlace()
            "take" -> commandsHandler.takePlace()
            "show all" -> commandsHandler.showAllPlaces()
            "show free" -> commandsHandler.showFreePlaces()
            "show bought" -> commandsHandler.showBoughtPlaces()
            "exit" -> {
                current = Sections.Main
                OutputModel("Left places section")
            }

            else -> OutputModel("Incorrect input")
        }
        println(res.message)
    }

}