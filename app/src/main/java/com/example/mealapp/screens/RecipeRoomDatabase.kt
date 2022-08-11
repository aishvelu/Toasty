package com.example.mealapp.screens

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Recipe::class], version =1, exportSchema = false)
abstract class RecipeRoomDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    companion object{
        @Volatile
        private var INSTANCE: RecipeRoomDatabase? = null
        fun getDatabase(context: Context): RecipeRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeRoomDatabase::class.java,
                    "recipe_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }
}