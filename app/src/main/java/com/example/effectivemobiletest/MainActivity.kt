package com.example.effectivemobiletest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.favourites.FavouritesScreen
import com.example.login.LoginScreen
import com.example.main.MainScreen
import com.example.ui.AccountScreen
import com.example.ui.theme.EffectiveMobileTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EffectiveMobileTestTheme(
                darkTheme = true,
                dynamicColor = false
            ) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: Routing.LoginScreen.route

                Scaffold(
                    bottomBar = {
                        if (currentRoute !in listOf(Routing.LoginScreen.route)) {
                            BottomNavPanel(navController, currentRoute)
                        }
                    },
                    contentWindowInsets = WindowInsets(left = 16.dp, right = 16.dp, top = 32.dp),
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = currentRoute
                    ) {
                        composable(Routing.LoginScreen.route) {
                            LoginScreen(
                                onLoginSuccess = { navController.navigate(Routing.MainScreen.route) },
                                paddingValues = innerPadding
                            )
                        }
                        composable(Routing.MainScreen.route) {
                            MainScreen(navController = navController, paddingValues = innerPadding)
                        }
                        composable(Routing.FavouritesScreen.route) {
                            FavouritesScreen(navController = navController, paddingValues = innerPadding)
                        }
                        composable(Routing.AccountScreen.route) {
                            AccountScreen(navController = navController, paddingValues = innerPadding)
                        }
                    }
                }
            }
        }
    }
}