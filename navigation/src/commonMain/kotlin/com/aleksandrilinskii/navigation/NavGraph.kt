package com.aleksandrilinskii.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aleksandrilinskii.auth.AuthScreen
import com.aleksandrilinskii.nutrisport.shared.navigation.Screen
import org.aleksandrilinskii.home.HomeGraphScreen

@Composable
fun NavGraph(
    startDestination: Screen = Screen.Auth
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Auth> {
            AuthScreen(
                navigateToHome = {
                    navController.navigate(Screen.HomeGraph) {
                        popUpTo(Screen.Auth) { inclusive = true }
                    }
                }
            )
        }

        composable<Screen.HomeGraph> {
            HomeGraphScreen()
        }
    }
}