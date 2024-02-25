package com.example.desafioapp1.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class Desafio1Navigation(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(Desafio1Destinations.HOME_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    };

    val navigateToEjercicio1: () -> Unit = {
        navController.navigate(Desafio1Destinations.EJERCICIO1_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    };

    val navigateToEjercicio2: () -> Unit = {
        navController.navigate(Desafio1Destinations.EJERCICIO2_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    };

    val navigateToEjercicio3: () -> Unit = {
        navController.navigate(Desafio1Destinations.EJERCICIO3_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}