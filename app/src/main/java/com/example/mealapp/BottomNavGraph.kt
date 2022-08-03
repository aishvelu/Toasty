package com.example.mealapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mealapp.screens.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BottomNavGraph(navController: NavHostController, auth: FirebaseAuth) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Profile.route
    ) {
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(auth)
        }
        composable(route = BottomBarScreen.Goals.route) {
            GoalsScreen()
        }
        composable(route = BottomBarScreen.Saved.route) {
            SavedScreen()
        }
        composable(route = BottomBarScreen.Create.route) {
            CreateScreen()
        }
    }
}

