package presentation.input

import domain.*
import presentation.model.OutputModel

interface CommandsHandler {
    fun start(): Result
    fun logIn(): Result
    fun signUp(): Result
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
    private val userController: UserController
) : CommandsHandler {
    override fun start(): Result {
        val resultUsers = userController.deserialize()
        val resultMovies = movieController.deserialize()
        val resultSessions = if (resultMovies == Success) sessionController.deserialize()
        else Error(OutputModel("Sessions data is invalid now"))
        if (resultUsers == Success && resultMovies == Success && resultSessions == Success) {
            return Success
        }
        var result = ""
        result += if (resultUsers is Error) resultUsers.outputModel.message + "\n" else ""
        result += if (resultMovies is Error) resultMovies.outputModel.message + "\n" else ""
        result += if (resultSessions is Error) resultSessions.outputModel.message + "\n" else ""
        return Error(OutputModel(result))
    }

    override fun logIn(): Result {
        println(OutputModel("Enter login and password in format: login; password").message)
        val input = readln().split("; ")
        if (input.size < 2) {
            return Error(OutputModel("Incorrect number of arguments"))
        }
        return userController.loginUser(input[0], input[1])
    }

    override fun signUp(): Result {
        println(OutputModel("Enter user info in format: name; surname; login; password").message)
        val input = readln().split("; ")
        if (input.size < 4) {
            return Error(OutputModel("Incorrect number of arguments"))
        }
        return userController.addUser(input[0], input[1], input[2], input[3])
    }

    override fun addMovie(): OutputModel {
        println(OutputModel("Enter the movie in format: name; director; duration. Duration is in format: nnHnnM").message)
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
        println(OutputModel("Enter movieId and newDirector in format: movieId; duration. Duration is in format: nnHnnM").message)
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
        println(OutputModel("Enter the session in format: timeStart; movieId. timeStart is in format: dd.MM.yyyy HH:mm:ss").message)
        val input = readln().split("; ")
        if (input.size < 2) {
            return OutputModel("Incorrect number of arguments")
        }
        val id = input[1].toIntOrNull() ?: return OutputModel("Incorrect movieId format")
        return sessionController.addSession(input[0], id)
    }

    override fun changeSessionTime(): OutputModel {
        println(OutputModel("Enter sessionId, timeStart in format: sessionId; timeStart. timeStart is in format: dd.MM.yyyy HH:mm:ss").message)
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
        return sessionController.buyPlace(id, x - 1, y - 1)

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
        return sessionController.returnPlace(id, x - 1, y - 1)
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
        return sessionController.takePlace(id, x - 1, y - 1)
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