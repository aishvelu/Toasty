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
        route = "MyBook",
        title = "MyBook",
        icon = Icons.Filled.Description
    )
    object Saved : BottomBarScreen(
        route = "saved",
        title = "Saved",
        icon = Icons.Filled.Favorite
    )
    object Create : BottomBarScreen(
        route = "create",
        title = "Create",
        icon = Icons.Default.ShoppingCart
    )
}

