package com.example.mealapp.screens

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

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