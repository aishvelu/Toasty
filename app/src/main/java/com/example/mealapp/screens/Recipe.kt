package com.example.mealapp.screens

import androidx.room.Entity


@Entity(tableName = "recipe")
data class Recipe(
    val title: String = "",
    val ingredients: String = "",
    val process: String = ""
)

