package com.example.mealapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )
    object Goals : BottomBarScreen(
        route = "goals",
        title = "Goals",
        icon = Icons.Default.Star
    )
    object Saved : BottomBarScreen(
        route = "saved",
        title = "Saved",
        icon = Icons.Default.AccountBox
    )
    object Create : BottomBarScreen(
        route = "create",
        title = "Create",
        icon = Icons.Default.ShoppingCart
    )
}

