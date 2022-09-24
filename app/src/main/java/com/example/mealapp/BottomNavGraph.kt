package com.example.mealapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mealapp.screens.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BottomNavGraph(navController: NavHostController, auth: FirebaseAuth) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Profile.route
    ) {
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(auth, navController)
        }
        composable(route = "${BottomBarScreen.Goals.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })) {
            GoalsScreen(it.arguments?.getString("userId"))
        }
        composable(route = BottomBarScreen.Saved.route) {
            SavedScreen()
        }
        composable(route = BottomBarScreen.Create.route) {
            CreateScreen()
        }
    }
}

