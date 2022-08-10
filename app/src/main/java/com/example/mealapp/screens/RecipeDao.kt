package com.example.mealapp.screens

import android.arch.persistence.room.*

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

}