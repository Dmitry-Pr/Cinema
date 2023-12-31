package data

interface UserDao {
    fun add(name: String, surname: String, login: String, password: String)
    fun get(login: String): UserEntity?
    fun getAll(): List<UserEntity>
    fun load(users: List<UserEntity>)
}

class RuntimeUserDao : UserDao {
    private val users = mutableMapOf<String, UserEntity>()
    private var counter = 0
    override fun add(name: String, surname: String, login: String, password: String) {
        val user = UserEntity(
            id = counter,
            name = name,
            surname = surname,
            login = login,
            password = password
        )
        users[login] = user
        counter++
    }

    override fun get(login: String): UserEntity? = users[login]
    override fun getAll(): List<UserEntity> = users.values.toList()
    override fun load(users: List<UserEntity>) {
        users.forEach { add(it.name, it.surname, it.login, it.password) }
    }
}