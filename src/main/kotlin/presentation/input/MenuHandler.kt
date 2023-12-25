package presentation.input

import presentation.model.OutputModel

enum class Sections {
    Main, Movie, Session, Places
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
    private var current = Sections.Main
    override fun handleMenu() {
        val input = readln()
        when (current) {
            Sections.Main -> mainCommand(input)
            Sections.Movie -> movieCommand(input)
            Sections.Session -> sessionCommand(input)
            Sections.Places -> placesCommand(input)
        }
    }

    private fun mainCommand(input: String) {
        println(menu.showMainMenu().message)
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

    private fun movieCommand(input: String) {
        println(menu.showMoviesMenu().message)
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

    private fun sessionCommand(input: String) {
        println(menu.showSessionsMenu().message)
        val res = when (input) {
            "add" -> commandsHandler.addSession()
            "change time" -> commandsHandler.changeSessionTime()
            "change movie" -> commandsHandler.changeSessionMovie()
            "show all" -> commandsHandler.showAllSessions()
            "exit" -> {
                current = Sections.Main
                OutputModel("Left movies section")
            }
            else -> OutputModel("Incorrect input")
        }
        println(res.message)
    }

    private fun placesCommand(input: String) {
        println(menu.showPlacesMenu().message)
        val res = when (input) {
            "buy" -> commandsHandler.buyPlace()
            "return" -> commandsHandler.returnPlace()
            "take" -> commandsHandler.takePlace()
            "show all" -> commandsHandler.showAllPlaces()
            "show free" -> commandsHandler.showFreePlaces()
            "show bought" -> commandsHandler.showBoughtPlaces()
            "exit" -> {
                current = Sections.Main
                OutputModel("Left movies section")
            }
            else -> OutputModel("Incorrect input")
        }
        println(res.message)
    }

}