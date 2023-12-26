package domain

import presentation.model.OutputModel

interface UserValidator {
    fun validateName(name: String): Result
    fun validateSurname(surname: String): Result
    fun validateLogin(login: String): Result
    fun validatePassword(password: String): Result
}

class UserValidatorImpl : UserValidator {
    override fun validateName(name: String): Result {
        return when {
            name.isNotEmpty() -> Success
            else -> Error(OutputModel("Invalid name format"))
        }
    }

    override fun validateSurname(surname: String): Result {
        return when {
            surname.isNotEmpty() -> Success
            else -> Error(OutputModel("Invalid surname format"))
        }
    }

    override fun validateLogin(login: String): Result {
        return when {
            login.isNotEmpty() -> Success
            else -> Error(OutputModel("Invalid login format"))
        }
    }

    override fun validatePassword(password: String): Result {
        return when {
            password.length < 6 -> Error(OutputModel("Password is too short"))
            else -> Success
        }
    }

}
