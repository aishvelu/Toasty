package com.example.mealapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

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
fun MainMenu(
    menuSelection: MutableState<MenuSelection>,
    expandedMain: MutableState<Boolean>,
    expandedNested: MutableState<Boolean>
) {
    DropdownMenu(
        expanded = expandedMain.value,
        onDismissRequest = { expandedMain.value = false },
    ) {
        DropdownMenuItem(
            onClick = {
                expandedMain.value = false // hide main menu
                expandedNested.value = true // show nested menu
                menuSelection.value = MenuSelection.NESTED
            }
        ) {
            Text("Nested Options \u25B6")
        }


        Divider()

        DropdownMenuItem(
            onClick = {
                // close main menu
                expandedMain.value = false
                menuSelection.value = MenuSelection.Meats
            }
        ) {
            Text("Meats")
        }

        Divider()

        DropdownMenuItem(
            onClick = {
                // close main menu
                expandedMain.value = false
                menuSelection.value = MenuSelection.Dairy
            }
        ) {
            Text("Dairy")
        }

        Divider()

        DropdownMenuItem(
            onClick = {
                // close main menu
                expandedMain.value = false
                menuSelection.value = MenuSelection.Vegetbles
            }
        ) {
            Text("Vegetables")
        }

        Divider()

        DropdownMenuItem(
            onClick = {
                // close main menu
                expandedMain.value = false
                menuSelection.value = MenuSelection.Fruits
            }
        ) {
            Text("Fruits")
        }
    }
}
    @Composable
    fun NestedMenu(
        expandedNested: MutableState<Boolean>,
        nestedMenuSelection: MutableState<NestedMenuSelection>
    ) {
        DropdownMenu(
            expanded = expandedNested.value,
            onDismissRequest = { expandedNested.value = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    // close nested menu
                    expandedNested.value = false
                    nestedMenuSelection.value = NestedMenuSelection.FIRST
                }
            ) {
                Text("First")
            }
            DropdownMenuItem(
                onClick = {
                    // close nested menu
                    expandedNested.value = false
                    nestedMenuSelection.value = NestedMenuSelection.SECOND
                }
            ) {
                Text("Second")
            }
        }

}
@Composable
fun TopAppBarDropdownMenu(
    menuSelection: MutableState<MenuSelection>,
    nestedMenuSelection: MutableState<NestedMenuSelection>
) {

    val expandedMain = remember { mutableStateOf(false) }
    val expandedNested = remember { mutableStateOf(false) }

    // Three Dot icon
    Box(
        Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(
            onClick = {
                // Expand the main menu on three dots icon click
                // and hide the nested menu.
                expandedMain.value = true
                expandedNested.value = false
            }
        ) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "More Menu"
            )
        }
    }

    MainMenu(
        menuSelection = menuSelection,
        expandedMain = expandedMain,
        expandedNested = expandedNested
    )
    NestedMenu(
        expandedNested = expandedNested,
        nestedMenuSelection = nestedMenuSelection
    )

}

@Composable
fun MainScreen(
    /* some params */
) {

    val menuSelection = remember { mutableStateOf(MenuSelection.NONE) }
    val nestedMenuSelection = remember { mutableStateOf(NestedMenuSelection.DEFAULT) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Ingredients") },
                actions = {
                    TopAppBarDropdownMenu(
                        menuSelection = menuSelection,
                        nestedMenuSelection= nestedMenuSelection
                    )
                }
            )
        }
    ){}
}
@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}
