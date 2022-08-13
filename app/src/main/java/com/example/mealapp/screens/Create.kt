package com.example.mealapp.screens
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.example.mealapp.R
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

private val client = OkHttpClient()
private val moshi = Moshi.Builder().build()
private val gistJsonAdapter = moshi.adapter(Gist::class.java)
private val url = "https://tasty.p.rapidapi.com/recipes/list?from=0&size=10&tags=under_30_minutes&q="
val linkList = listOf<String>("https://img.buzzfeed.com/tasty-app-user-assets-prod-us-east-1/recipes/4ea6c7ca275a4112b063af2cd4ffe13d.jpeg",

    "https://img.buzzfeed.com/tasty-app-user-assets-prod-us-east-1/recipes/79237d0c898649c5bb373483f792b135.jpeg",

    "https://img.buzzfeed.com/tasty-app-user-assets-prod-us-east-1/recipes/a3356aaddec3466c9eae2a3f2abd109b.jpeg",

    "https://img.buzzfeed.com/tasty-app-user-assets-prod-us-east-1/recipes/c10bb88f1f664b3890c38c506cadd7a2.jpeg",

    "https://img.buzzfeed.com/tasty-app-user-assets-prod-us-east-1/recipes/32ef88a048064206a7d9299fed001f50.jpeg",

    "https://img.buzzfeed.com/tasty-app-user-assets-prod-us-east-1/recipes/7c4bbc36486441a5a9b4ee0a6b89066a.jpeg",

    )


@JsonClass(generateAdapter = true)
data class Gist(var files: Map<String, GistFile>?)

@JsonClass(generateAdapter = true)
data class GistFile(var content: String?)



@Composable
fun CreateScreen() {
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
            showRecipes.value = true
        }) {
            Text("Confirm")
        }
        if(showRecipes.value) {
            showList()
        }
    }

}

@Composable
fun showList() {
    LazyColumn {
        items(linkList) { item: String ->
            AsyncImage(model = item, contentDescription = null, modifier = Modifier.fillMaxWidth())
        }
                }
}






@Composable
private fun Ingredients(count: List<String> = List(3) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = count) {
            TopAppBarDropdownMenu()
        }
    }
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
fun TopAppBarDropdownMenu() {
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
                DropdownMenuItem(onClick = {
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
}

@Composable
fun RecipeImage(ingredient_1: String, ingredient_2: String = "", ingredient_3: String = "") {
    var urlmod = url + ingredient_1
    if(!ingredient_2.equals("")){
        urlmod += ingredient_2
    }
    if(!ingredient_3.equals("")){
        urlmod += ingredient_3
    }

    val request = Request.Builder()
        .url(urlmod)
        .get()
        .addHeader("X-RapidAPI-Key", "1fce4d8ce8mshb43ee73e23131ecp128481jsn7574c01064bf")
        .addHeader("X-RapidAPI-Host", "tasty.p.rapidapi.com")
        .build()
    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        val gist = gistJsonAdapter.fromJson(response.body!!.source())

        for ((key, value) in gist!!.files!!) {
            println(key)
            println(value.content)
        }
    }
}

@Composable
@Preview
fun CreateScreenPreview() {
    CreateScreen()
}
