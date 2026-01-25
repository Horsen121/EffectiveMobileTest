package com.example.effectivemobiletest

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ui.components.LabelText
import com.example.ui.theme.EffectiveMobileTestTheme

@Composable
fun BottomNavPanel(
    navController: NavController,
    currentRoute: String
) {
    BottomAppBar(
        {
            NavigationBarItem(
                selected = currentRoute == Routing.MainScreen.route,
                onClick = { if (currentRoute != Routing.MainScreen.route)
                    navController.navigate(Routing.MainScreen.route)
                },
                icon = {
                    Image(
                        painter = painterResource(com.example.ui.R.drawable.ui_home),
                        contentDescription = stringResource(com.example.main.R.string.main_),
                        colorFilter = ColorFilter.tint(if (currentRoute == Routing.MainScreen.route)
                            MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground)
                    )
                },
                label = {
                    LabelText(
                        text =com.example.main.R.string.main_,
                        color = if (currentRoute == Routing.MainScreen.route)
                            MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground
                    )
                }
            )
            NavigationBarItem(
                selected = currentRoute == Routing.FavouritesScreen.route,
                onClick = { if (currentRoute != Routing.FavouritesScreen.route)
                    navController.navigate(Routing.FavouritesScreen.route)
                },
                icon = {
                    Image(
                        painter = painterResource(com.example.ui.R.drawable.ui_bookmark),
                        contentDescription = stringResource(com.example.favourites.R.string.favourites_),
                        colorFilter = ColorFilter.tint(if (currentRoute == Routing.FavouritesScreen.route)
                            MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground),
                        modifier = Modifier.scale(1.5f)
                    )
                },
                label = {
                    LabelText(
                        text =com.example.favourites.R.string.favourites_,
                        color = if (currentRoute == Routing.FavouritesScreen.route)
                            MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground
                    )
                }
            )
            NavigationBarItem(
                selected = currentRoute == Routing.AccountScreen.route,
                onClick = { if (currentRoute != Routing.AccountScreen.route)
                    navController.navigate(Routing.AccountScreen.route)
                },
                icon = {
                    Image(
                        painter = painterResource(com.example.ui.R.drawable.ui_person),
                        contentDescription = stringResource(R.string.app_account),
                        colorFilter = ColorFilter.tint(if (currentRoute == Routing.AccountScreen.route)
                            MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground)
                    )
                },
                label = {
                    LabelText(
                        text = R.string.app_account,
                        color = if (currentRoute == Routing.AccountScreen.route)
                            MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground
                    )
                }
            )
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun BottomNavPanelPreview() {
    EffectiveMobileTestTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            bottomBar = {
                val context = LocalContext.current
                BottomNavPanel(NavController(context), Routing.MainScreen.route)
            },
            contentWindowInsets = WindowInsets(left = 16.dp, right = 16.dp, top = 32.dp),
            modifier = Modifier.fillMaxSize()
        ) {}
    }
}