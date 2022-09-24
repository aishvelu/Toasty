package com.example.mealapp.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository, private val recipeDao: RecipeDao): ViewModel(){
    suspend fun saveRecipe(recipe: Recipe){
        recipeDao.save(recipe)
    }

    suspend fun deleteRecipe(recipe: Recipe){
        recipeDao.delete(recipe)
    }

class RecipeViewModelFactory(private val repository: RecipeRepository, private val recipeDao: RecipeDao): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return RecipeViewModel(repository, recipeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
}