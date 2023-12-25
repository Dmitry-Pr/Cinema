package presentation

import di.DI.menuHandler

fun main() {
    println("HELLO")
    val menuHandler = menuHandler
    do {
        menuHandler.handleMenu()
    } while(!menuHandler.finish)
}
