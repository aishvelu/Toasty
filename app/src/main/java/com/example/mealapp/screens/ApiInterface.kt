package com.example.mealapp.screens

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("recipes/list?from=0&size=10&=under_30_minutes&q={tags}")
    fun getData(@Path( value = "tags", encoded = true) tags: String ): Call<MyRecipeList>
}