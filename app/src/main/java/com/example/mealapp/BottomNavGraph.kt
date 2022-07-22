package com.example.mealapp

<<<<<<< HEAD
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mealapp.screens.HomeScreen
import com.example.mealapp.screens.ProfileScreen
import com.example.mealapp.screens.CreateScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = BottomBarScreen.Create.route) {
            CreateScreen()
        }
    }
}
=======
>>>>>>> e339ed1ae685f4fa61954b9d235580b1ccd57caa
