package presentation

import di.DI.menuHandler

fun main() {
    val menuHandler = menuHandler
    do {
        menuHandler.handleMenu()
    } while(!menuHandler.finish)
    println("Program is finished")
}
