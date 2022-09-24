package com.example.mealapp.screens


import android.util.Log
import com.squareup.okhttp.Response
import okhttp3.Interceptor

class MyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-RapidAPI-Key", "1fce4d8ce8mshb43ee73e23131ecp128481jsn7574c01064bf")
        .addHeader("X-RapidAPI-Host", "tasty.p.rapidapi.com")
            .build()
        Log.d("myInterceptor", request.toString())
        return chain.proceed(request)
    }
}