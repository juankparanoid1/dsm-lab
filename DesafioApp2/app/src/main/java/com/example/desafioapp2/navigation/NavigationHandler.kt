package com.example.desafioapp2.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.desafioapp2.screens.History
import com.example.desafioapp2.screens.Home
import com.example.desafioapp2.screens.ProductDetail

@Composable
fun NavigationHandler(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState,
    startDestination: String = Destinations.HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Destinations.HOME_ROUTE) {
            Home(navController = navController, drawerState = drawerState);
        }
        composable(Destinations.PRODUCT_DETAIL_ROUTE) {backStackEntry ->
            backStackEntry.arguments?.getString("userId")?.let {
                ProductDetail(it, navController = navController);
            };
        }
        composable(Destinations.HISTORY_ROUTE) {
            History(navController = navController);
        }
    }

}