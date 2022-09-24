package com.example.mealapp.screens
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Network
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.mealapp.R
import com.squareup.moshi.*
//import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.*
import okio.IOException
import java.lang.Exception
import java.util.concurrent.CompletableFuture
import kotlin.concurrent.thread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val client = OkHttpClient().newBuilder().apply {
    addInterceptor(MyInterceptor())
}.build()

val urlList = mutableListOf<String>()

private val url = "https://tasty.p.rapidapi.com/"

private fun getMyData(ing: MutableList<MutableState<String>>){
    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(url)
        .client(client)
        .build()
        .create(ApiInterface::class.java)
    var tags = ""
    if (ing.isNotEmpty() && ing.size == 1){
        tags+= ing.elementAt(0)
    }
    else if (ing.isNotEmpty() && ing.size > 1){
        for(ingredient in ing){
            tags += ingredient
            if (ingredient == ing.elementAt(ing.size-1)){

            }
            else{
                tags += "%20"
            }
        }
    }
    else{

    }
    val retrofitData = retrofitBuilder.getData(tags)
    retrofitData.enqueue(object : Callback<MyRecipeList?> {
        override fun onResponse(
            call: Call<MyRecipeList?>,
            response: Response<MyRecipeList?>
        ) {
            val responseBody = response.body()!!

            for(recipeData in responseBody.results){
                    urlList.add(recipeData.video_url)
                    Log.i("log", recipeData.video_url)
            }
            Log.i("log", responseBody.count.toString())
            Log.i("log", responseBody.results.toString())
        }

        override fun onFailure(call: Call<MyRecipeList?>, t: Throwable) {
            Log.d("failed t", t.toString())
            Log.d("failed call", call.toString())
        }
    })
}

@Composable
fun CreateScreen() {
    val ing = Ingredients()
    var showRecipes = remember {
        mutableStateOf(false)
    }
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
        ){
            Text(
                text = "Select Your Ingredients",
                style = MaterialTheme.typography.h2,
                modifier = Modifier
                    .padding(top = 16.dp)
            )
        }
        Ingredients()
        Button(onClick = {
            getMyData(ing)
            showRecipes.value = true
        }) {
            Text("Confirm", color = Color.Black)
        }
        if(showRecipes.value) {

        }
        else{

        }
    }

}
@Composable
fun showList() {
    LazyColumn {
        items(urlList) { item: String ->
            Text(item, color = Color.Black)
        }
    }
}

@Composable
private fun Ingredients(count: List<String> = List(3) { "$it" }): MutableList<MutableState<String>> {
    val ing = mutableListOf<MutableState<String>>()
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = count) {
            ing.add(TopAppBarDropdownMenu())
        }
    }
    return ing
}


@Composable
fun NestedMenu(onValueChange: () -> Unit, expandedNested:MutableState<Boolean>,
               selectedText:MutableState<String>, textfieldSize:MutableState<Size>,
               listData: List<ProductModel>, label: String) {

    DropdownMenu(
        expanded = expandedNested.value,
        onDismissRequest = { expandedNested.value = false },
        modifier = Modifier
            .width(with(LocalDensity.current) { textfieldSize.value.width.toDp() })
    ) {
        listData.forEach { label ->
            DropdownMenuItem(onClick = {
                selectedText.value = label.title
                expandedNested.value = false
                onValueChange.invoke()
            }) {
                Row(horizontalArrangement = Arrangement.Start) {
                    Text(text = label.title)
                    Spacer(modifier = Modifier.weight(1f))
                    Image( painter = painterResource(id = label.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.requiredSize(50.dp))
                }
            }
        }
    }
}
@Composable
fun TopAppBarDropdownMenu(): MutableState<String> {
    var expandedMain = remember { mutableStateOf(false) }
    var expandedNested = remember { mutableStateOf(false) }
    var expandedNestedLabel = remember { mutableStateOf("") }
    val suggestions = listOf("Meats", "Dairy", "Fruits", "Vegetables")
    val meatsList = Data.meatData["Meats"]
    val dairyList = Data.dairyData["Dairy"]
    val vegetableList = Data.vegetableData["Vegetables"]
    val fruitList = Data.fruitData["Fruits"]
    var selectedText = remember { mutableStateOf("") }
    var textfieldSize = remember { mutableStateOf(Size.Zero) }

    val iconMain = if (expandedMain.value)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    val iconNested = if (expandedNested.value)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedText.value,
            onValueChange = { selectedText.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize.value = coordinates.size.toSize()
                },
            label = { Text("Label") },
            trailingIcon = {
                Icon(iconMain, "contentDescription",
                    Modifier.clickable {
                        // Expand the main menu on icon click
                        // and hide the nested menu.
                        expandedMain.value = true
                        expandedNested.value = false
                    })
            }
        )
        DropdownMenu(
            expanded = expandedMain.value,
            onDismissRequest = { expandedMain.value = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textfieldSize.value.width.toDp()})
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        expandedMain.value = false
                        expandedNested.value = true
                        expandedNestedLabel.value = label
                    },
                ){
                    Row(horizontalArrangement = Arrangement.Start) {
                        Text(text = label)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(imageVector = iconNested,
                            //modifier = Modifier.weight(6f, true),
                            contentDescription = null)
                    }
                }
            }
        }
        when (expandedNestedLabel.value) {
            "Meats" -> meatsList?.let {
                NestedMenu(
                    onValueChange = {
                        expandedMain.value = false
                    },
                    expandedNested = expandedNested,
                    selectedText = selectedText,
                    textfieldSize = textfieldSize,
                    listData = it,
                    label = expandedNestedLabel.value
                )
            }
            "Dairy" -> dairyList?.let {
                NestedMenu(
                    onValueChange = {
                        expandedMain.value = false
                    },
                    expandedNested = expandedNested,
                    selectedText = selectedText,
                    textfieldSize = textfieldSize,
                    listData = it,
                    label = expandedNestedLabel.value
                )
            }
            "Vegetables" -> vegetableList?.let {
                NestedMenu(
                    onValueChange = {
                        expandedMain.value = false
                    },
                    expandedNested = expandedNested,
                    selectedText = selectedText,
                    textfieldSize = textfieldSize,
                    listData = it,
                    label = expandedNestedLabel.value
                )
            }
            "Fruits"-> fruitList?.let {
                NestedMenu(
                    onValueChange = {
                        expandedMain.value = false
                    },
                    expandedNested = expandedNested,
                    selectedText = selectedText,
                    textfieldSize = textfieldSize,
                    listData = it,
                    label = expandedNestedLabel.value
                )
            }
            else -> {}

        }
    }
    return selectedText
}
