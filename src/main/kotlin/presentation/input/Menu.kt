package presentation.input

import presentation.model.OutputModel

interface Menu {
    fun showMainMenu(): OutputModel
    fun showRegistrationMenu(): OutputModel
    fun showMoviesMenu(): OutputModel
    fun showSessionsMenu(): OutputModel
    fun showPlacesMenu(): OutputModel
}

class MenuImpl : Menu {

    override fun showMainMenu(): OutputModel {
        val res =
            """Main menu. Enter
            |"movie" In order to add o change a movie
            |"session" In order to add o change a session
            |"places" In order to work with places
            |"exit" In order to finish program
        """.trimMargin()
        return OutputModel(res)
    }

    override fun showRegistrationMenu(): OutputModel {
        val res =
            """Registration menu. Enter
            |"log in" If you already have an account
            |"sign up" In order to make an account
            |"exit" In order to finish program
        """.trimMargin()
        return OutputModel(res)
    }

    override fun showMoviesMenu(): OutputModel {
        val res =
            """Movies menu. Enter
            |"add" In order to add a movie
            |"change name" In order to change a movie name
            |"change director" In order to change a movie director
            |"change duration" In order to change a movie duration
            |"show all" In order to see the list of movies
            |"exit" In order to exit to main menu
        """.trimMargin()
        return OutputModel(res)
    }

    override fun showSessionsMenu(): OutputModel {
        val res =
            """Session menu. Enter
            |"add" In order to add a session
            |"change time" In order to change a session start time
            |"change movie" In order to change the session movie 
            |"show all" In order to see the list of sessions
            |"exit" In order to exit to main menu
        """.trimMargin()
        return OutputModel(res)
    }

    override fun showPlacesMenu(): OutputModel {
        val res =
            """Places menu. Enter
            |"buy" In order to mark the bought place
            |"return" In order to mark the free place   
            |"take" In order to mark the taken place 
            |"show all" In order to see all places
            |"show free" In order to see free places
            |"show bought" In order to see bought places
            |"exit" In order to exit to main menu
        """.trimMargin()
        return OutputModel(res)
    }

}