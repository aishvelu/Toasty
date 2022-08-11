package com.example.mealapp.screens

import androidx.annotation.WorkerThread

class RecipeRepository(private val recipeDao: RecipeDao) {
    @Suppress("RedundantSuspendModifier")
    suspend fun addToSaved(recipe: Recipe) {
       recipeDao.save(recipe)
    }

    suspend fun removeFromSaved(recipe: Recipe){
        recipeDao.delete(recipe)
    }

}