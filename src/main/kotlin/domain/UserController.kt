package domain

import data.UserDao
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.mindrot.jbcrypt.BCrypt
import presentation.model.OutputModel
import java.io.File
import java.io.FileNotFoundException
import java.lang.Exception

const val USERS_JSON_PATH = "data/users.json"
interface UserController {
    fun addUser(name: String, surname: String, login: String, password: String): Result
    fun loginUser(login: String, password: String): Result
}

class UserControllerImpl (
    private val userDao: UserDao,
    private val userValidator: UserValidator
) : UserController {
    override fun addUser(name: String, surname: String, login: String, password: String): Result {
        val resultName = userValidator.validateName(name)
        val resultSurname = userValidator.validateSurname(surname)
        val resultLogin = userValidator.validateLogin(login)
        val resultPassword = userValidator.validatePassword(password)
        return when {
            resultName is Error -> resultName
            resultSurname is Error -> resultSurname
            resultLogin is Error -> resultLogin
            resultPassword is Error -> resultPassword
            userDao.get(login) != null -> Error(OutputModel("User $login already exists"))
            else -> {
                userDao.add(
                    name = name,
                    surname = surname,
                    login = login,
                    password = BCrypt.hashpw(password, BCrypt.gensalt()))
                when(val res = serialize()) {
                    Success -> Success
                    is Error -> res
                }
            }
        }

    }

    override fun loginUser(login: String, password: String): Result {
        val user = userDao.get(login) ?: return Error(OutputModel("User with login $login is not found"))
        val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
        return when(BCrypt.checkpw(user.password, hashedPassword)) {
            true -> Success
            false -> Error(OutputModel("Incorrect password"))
        }
    }
    private fun serialize(): Result {
        return try {
            val file = File(USERS_JSON_PATH)
            val jsonString = Json.encodeToString(userDao.getAll())
            file.writeText(jsonString)
            Success
        } catch (ex: FileNotFoundException) {
            Error(OutputModel("The changes are not saved, saving file is not found"))
        } catch (ex: Exception) {
            Error(OutputModel("The changes are not saved, unpredicted problem with saving file"))
        }
    }

}