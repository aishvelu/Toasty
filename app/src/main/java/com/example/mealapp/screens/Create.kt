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

@Composable
fun CreateScreen() {
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
        Ingredients()
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
@Preview
fun CreateScreenPreview() {
    CreateScreen()
}
