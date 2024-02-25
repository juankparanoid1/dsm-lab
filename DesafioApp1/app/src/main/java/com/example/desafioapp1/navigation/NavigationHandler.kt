package com.example.desafioapp1.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.desafioapp1.Screens.Ejercicio1
import com.example.desafioapp1.Screens.Ejercicio2
import com.example.desafioapp1.Screens.Ejercicio3
import com.example.desafioapp1.Screens.Home

@Composable
fun NavigationHandler(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Desafio1Destinations.HOME_ROUTE,
    drawerState: DrawerState,
    navigateToHome: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            route = Desafio1Destinations.HOME_ROUTE
        ) {
            Home(
                drawerState = drawerState
            );
        }
        composable(
            route = Desafio1Destinations.EJERCICIO1_ROUTE
        ) {
            Ejercicio1(
                navigateToHome = navigateToHome
            );
        }
        composable(
            route = Desafio1Destinations.EJERCICIO2_ROUTE
        ) {
            Ejercicio2(
                navigateToHome = navigateToHome
            );
        }
        composable(
            route = Desafio1Destinations.EJERCICIO3_ROUTE
        ) {
            Ejercicio3(
                navigateToHome = navigateToHome
            );
        }
    }

}