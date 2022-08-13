package com.example.mealapp.screens

import android.media.Image
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

val savedList = listOf<String>("https://img.buzzfeed.com/tasty-app-user-assets-prod-us-east-1/recipes/4ea6c7ca275a4112b063af2cd4ffe13d.jpeg",

    "https://img.buzzfeed.com/tasty-app-user-assets-prod-us-east-1/recipes/79237d0c898649c5bb373483f792b135.jpeg",

    "https://img.buzzfeed.com/tasty-app-user-assets-prod-us-east-1/recipes/a3356aaddec3466c9eae2a3f2abd109b.jpeg")

@Composable
fun SavedScreen() {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.primaryVariant)
                .requiredSize(width = 600.dp, height = 75.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Saved Recipes",
                style = MaterialTheme.typography.h2,
                modifier = Modifier
                    .padding(0.dp)
            )
        }
        showSaved()


    }

}

@Composable
fun showSaved(){
    LazyColumn {
        items(savedList) { item: String ->
            AsyncImage(model = item, contentDescription = null, modifier = Modifier.fillMaxWidth())
        }
    }
}





@Composable
@Preview
fun SavedScreenPreview() {
    SavedScreen()
}