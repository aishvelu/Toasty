package com.example.mealapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "HOME",
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun NestedMenu(expandedNested:MutableState<Boolean>, selectedText:MutableState<String>,
               textfieldSize:MutableState<Size>, listData: List<ProductModel> ) {
    DropdownMenu(
        expanded = expandedNested.value,
        onDismissRequest = { expandedNested.value = false },
        modifier = Modifier
            .width(with(LocalDensity.current){textfieldSize.value.width.toDp()})
    ) {
        listData.forEach { label ->
            DropdownMenuItem(onClick = {
                selectedText.value = label.title
                expandedNested.value = false
            }) {
                Text(text = label.title)
            }
        }
    }
}
@Composable
fun TopAppBarDropdownMenu() {
    var expandedMain = remember { mutableStateOf(false) }
    var expandedNested = remember { mutableStateOf(false) }
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

    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = selectedText },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
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
                },
                content = {
                    when (label) {
                        "Meats" -> meatsList
                        "Dairy" -> dairyList
                        "Vegetables" -> vegetableList
                        else -> fruitList
                    }?.let {
                        NestedMenu(
                            expandedNested = expandedNested,
                            selectedText = selectedText,
                            textfieldSize = textfieldSize,
                            listData = it
                        )
                    }
                })
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}
