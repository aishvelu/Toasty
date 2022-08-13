package com.example.mealapp.screens
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Build
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
import coil.compose.rememberAsyncImagePainter
import com.example.mealapp.R
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import okhttp3.*
import okio.IOException
import java.lang.Exception
import java.util.concurrent.CompletableFuture


private val client = OkHttpClient()
private val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory()).build()
@OptIn(ExperimentalStdlibApi::class)
private val jsonAdapter: JsonAdapter<ResponseRecipe> = moshi.adapter<ResponseRecipe>()
private val url = "https://tasty.p.rapidapi.com/recipes/list?from=0&size=10&tags=under_30_minutes&q="


@Composable
fun CreateScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Ingredients",
            modifier = Modifier.padding(16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Magenta
        )
        var ing = Ingredients()
        var rec: ResponseRecipe? = ResponseRecipe(0)
        Button(onClick = {
            if(ing.size > 0) {
                rec = getRecipeImage(ingredients = ing)
            }
            else {
                showToasts(context, "Ingredients required")
            }
        }) {
            Text("Create")
        }

        if(rec?.count?.compareTo(0)!! > 0) {
            rec!!.results.forEach { recipe ->
                Column(
                    //modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = rememberAsyncImagePainter(recipe.thumbnail_url), contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun NetworkImage(url: String) {
    var image by remember {mutableStateOf<Image?>(null)}
    var drawable by remember {mutableStateOf<Drawable?>(null)}

    val onCommit: (url: String) -> Unit = {
        val picasso = Picasso.get()

        val target = object: Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                drawable = placeHolderDrawable
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                drawable = errorDrawable
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                image = bitmap?.asImage()
            }
        }
        picasso.load(url).into(target)

        onDispose {
            image = null
            drawable = null
            picasso.cancelRequest(target)
        }
    }
}

fun showToasts(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
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


fun getRecipeImage(ingredients: MutableList<MutableState<String>>): ResponseRecipe? {
    var urlmod = url
    for(ingredient in ingredients){
        urlmod += "+" + ingredient
    }

    val request = Request.Builder()
        .url(urlmod)
        .get()
        .addHeader("X-RapidAPI-Key", "1fce4d8ce8mshb43ee73e23131ecp128481jsn7574c01064bf")
        .addHeader("X-RapidAPI-Host", "tasty.p.rapidapi.com")
        .build()
    var recipeI: ResponseRecipe
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: java.io.IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val recipes = jsonAdapter.fromJson(response.body!!.source())

                if (recipes != null) {
                    recipeI = recipes
                }
            }
        }
    })
    return recipeI
}


@JsonClass(generateAdapter = true)
data class ResponseRecipe(
    val count: Int,
    val results: List<RecipeInfo> = listOf<RecipeInfo>()
)

@JsonClass(generateAdapter = true)
data class Gist(var files: Map<String, GistFile>?)

@JsonClass(generateAdapter = true)
data class GistFile(var content: String?)


data class RecipeInfo(
    @Json(name = "id") val id: Int,
    @Json(name = "name")val name: String,
    @Json(name = "description")val description: String = "",
    @Json(name = "instructions")val instructions: List<Instruction>,
    @Json(name = "video_url")val video_url: String = "",
    @Json(name = "thumbnail_url")val thumbnail_url: String = "",
    val cook_time_minutes: Int,
    val prep_time_minutes: Int
)

data class Instruction(
    val id: Int,
    val display_text: String,
    val position: Int
)


@Composable
@Preview
fun CreateScreenPreview() {
    CreateScreen()
}
