package presentation.input

import domain.MovieController
import domain.SessionController
import presentation.model.OutputModel

interface CommandsHandler {
    fun addMovie(): OutputModel
    fun changeMovieName(): OutputModel
    fun changeMovieDirector(): OutputModel
    fun changeMovieDuration(): OutputModel
    fun showAllMovies(): OutputModel
    fun addSession(): OutputModel
    fun changeSessionTime(): OutputModel
    fun changeSessionMovie(): OutputModel
    fun showAllSessions(): OutputModel
    fun buyPlace(): OutputModel
    fun returnPlace(): OutputModel
    fun takePlace(): OutputModel
    fun showAllPlaces(): OutputModel
    fun showFreePlaces(): OutputModel
    fun showBoughtPlaces(): OutputModel
}

class CommandsHandlerImpl(
    private val movieController: MovieController,
    private val sessionController: SessionController,
) : CommandsHandler {
    override fun addMovie(): OutputModel {
        println(OutputModel("Enter the movie in format: name; director; duration. Duration is in format: NNhNNm").message)
        val input = readln().split("; ")
        if (input.size < 3) {
            return OutputModel("Incorrect number of arguments")
        }
        return movieController.addMovie(input[0], input[1], "PT" + input[2])
    }

    override fun changeMovieName(): OutputModel {
        println(OutputModel("Enter movieId and newName in format: movieId; newName").message)
        val input = readln().split("; ")
        if (input.size < 2) {
            return OutputModel("Incorrect number of arguments")
        }
        val id = input[0].toIntOrNull() ?: return OutputModel("Incorrect movieId format")
        return movieController.changeName(id, input[1])
    }

    override fun changeMovieDirector(): OutputModel {
        println(OutputModel("Enter movieId and newDirector in format: movieId; newDirector").message)
        val input = readln().split("; ")
        if (input.size < 2) {
            return OutputModel("Incorrect number of arguments")
        }
        val id = input[0].toIntOrNull() ?: return OutputModel("Incorrect movieId format")
        return movieController.changeDirector(id, input[1])
    }

    override fun changeMovieDuration(): OutputModel {
        println(OutputModel("Enter movieId and newDirector in format: movieId; duration. Duration is in format: NNhNNm").message)
        val input = readln().split("; ")
        if (input.size < 2) {
            return OutputModel("Incorrect number of arguments")
        }
        val id = input[0].toIntOrNull() ?: return OutputModel("Incorrect movieId format")
        return movieController.changeDuration(id, "PT" + input[1])
    }

    override fun showAllMovies(): OutputModel {
        return movieController.getAllMovies()
    }

    override fun addSession(): OutputModel {
        println(OutputModel("Enter the session in format: timeStart; movieId. timeStart is in format: dd.MM.YYYY HH:mm:ss").message)
        val input = readln().split("; ")
        if (input.size < 2) {
            return OutputModel("Incorrect number of arguments")
        }
        val id = input[1].toIntOrNull() ?: return OutputModel("Incorrect movieId format")
        return sessionController.addSession(input[0], id)
    }

    override fun changeSessionTime(): OutputModel {
        println(OutputModel("Enter sessionId, timeStart in format: sessionId; timeStart. timeStart is in format: dd.MM.YYYY HH:mm:ss").message)
        val input = readln().split("; ")
        if (input.size < 2) {
            return OutputModel("Incorrect number of arguments")
        }
        val id = input[0].toIntOrNull() ?: return OutputModel("Incorrect sessionId format")
        return sessionController.changeTimeStart(id, input[1])
    }

    override fun changeSessionMovie(): OutputModel {
        println(OutputModel("Enter sessionId, movieId in format: sessionId; movieId").message)
        val input = readln().split("; ")
        if (input.size < 2) {
            return OutputModel("Incorrect number of arguments")
        }
        val sessionId = input[0].toIntOrNull() ?: return OutputModel("Incorrect sessionId format")
        val movieId = input[1].toIntOrNull() ?: return OutputModel("Incorrect movieId format")
        return sessionController.changeMovie(sessionId, movieId)
    }

    override fun showAllSessions(): OutputModel {
        return sessionController.getAllSessions()
    }

    override fun buyPlace(): OutputModel {
        println(OutputModel("Enter sessionId, row, place in format: sessionId; row; place").message)
        val input = readln().split("; ")
        if (input.size < 3) {
            return OutputModel("Incorrect number of arguments")
        }
        val id = input[0].toIntOrNull() ?: return OutputModel("Incorrect sessionId format")
        val x = input[1].toIntOrNull() ?: return OutputModel("Incorrect row format")
        val y = input[2].toIntOrNull() ?: return OutputModel("Incorrect place format")
        return sessionController.buyPlace(id, x, y)

    }

    override fun returnPlace(): OutputModel {
        println(OutputModel("Enter sessionId, row, place in format: sessionId; row; place").message)
        val input = readln().split("; ")
        if (input.size < 3) {
            return OutputModel("Incorrect number of arguments")
        }
        val id = input[0].toIntOrNull() ?: return OutputModel("Incorrect sessionId format")
        val x = input[1].toIntOrNull() ?: return OutputModel("Incorrect row format")
        val y = input[2].toIntOrNull() ?: return OutputModel("Incorrect place format")
        return sessionController.returnPlace(id, x, y)
    }

    override fun takePlace(): OutputModel {
        println(OutputModel("Enter sessionId, row, place in format: sessionId; row; place").message)
        val input = readln().split("; ")
        if (input.size < 3) {
            return OutputModel("Incorrect number of arguments")
        }
        val id = input[0].toIntOrNull() ?: return OutputModel("Incorrect sessionId format")
        val x = input[1].toIntOrNull() ?: return OutputModel("Incorrect row format")
        val y = input[2].toIntOrNull() ?: return OutputModel("Incorrect place format")
        return sessionController.takePlace(id, x, y)
    }

    override fun showAllPlaces(): OutputModel {
        println(OutputModel("Enter sessionId").message)
        val input = readln()
        val id = input.toIntOrNull() ?: return OutputModel("Incorrect sessionId format")
        return sessionController.getAllPlaces(id)
    }

    override fun showFreePlaces(): OutputModel {
        println(OutputModel("Enter sessionId").message)
        val input = readln()
        val id = input.toIntOrNull() ?: return OutputModel("Incorrect sessionId format")
        return sessionController.getFreePlaces(id)
    }

    override fun showBoughtPlaces(): OutputModel {
        println(OutputModel("Enter sessionId").message)
        val input = readln()
        val id = input.toIntOrNull() ?: return OutputModel("Incorrect sessionId format")
        return sessionController.getBoughtPlaces(id)
    }


}