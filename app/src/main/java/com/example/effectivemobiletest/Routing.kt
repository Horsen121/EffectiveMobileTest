package com.example.effectivemobiletest

sealed class Routing(val route: String) {
    data object LoginScreen: Routing("login_screen")
    data object MainScreen: Routing("main_screen")
    data object FavouritesScreen: Routing("favourites_screen")
}