package domain

import presentation.model.OutputModel
import java.time.Duration

interface MovieValidator {
    fun validateName(name: String): Result
    fun validateDuration(duration: String): Result
    fun validateDirector(director: String): Result
}

class MovieValidatorImpl : MovieValidator {
    override fun validateName(name: String): Result {
        return when {
            name.isEmpty() -> Error(OutputModel("Name is incorrect"))
            else -> Success
        }
    }

    override fun validateDuration(duration: String): Result {
        return try {
            val dur = Duration.parse(duration)
            Success
        } catch (ex: IllegalArgumentException) {
            Error(OutputModel("Duration is incorrect"))
        }
    }

    override fun validateDirector(director: String): Result {
        return when {
            director.isEmpty() -> Error(OutputModel("Director name is incorrect"))
            else -> Success
        }
    }

}