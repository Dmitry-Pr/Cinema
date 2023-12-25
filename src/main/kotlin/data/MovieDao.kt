package data

import java.time.Duration

interface MovieDao {
    fun add(name: String, director: String, duration: Duration)
    fun getAll(): List<MovieEntity>
    fun get(id: Int): MovieEntity?
    fun update(vararg listMovie: MovieEntity)
}

class RuntimeMovieDao : MovieDao {
    private val movies = mutableMapOf<Int, MovieEntity>()
    private var counter = 0
    override fun add(name: String, director: String, duration: Duration) {
        val movie = MovieEntity(
            id = counter,
            name = name,
            director = director,
            duration = duration
        )
        movies[counter] = movie
        counter++
    }

    override fun getAll(): List<MovieEntity> = movies.values.toList()

    override fun get(id: Int): MovieEntity? = movies[id]

    override fun update(vararg listMovie: MovieEntity) =
        listMovie.forEach { movie -> movies[movie.id] = movie }

}
