package com.example.mealapp.screens

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy


@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

}