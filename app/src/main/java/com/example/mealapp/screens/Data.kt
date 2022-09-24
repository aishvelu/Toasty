package com.example.mealapp.screens

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.mealapp.R

data class ProductModel(
    val title: String,
    @DrawableRes
    val image: Int,
    val color: Color
)
fun getMeatData(): List<ProductModel> {
    return listOf(
        ProductModel("Beef", R.drawable.beef, Color(0xFFFF1A9B)),
        ProductModel("Lamb", R.drawable.lamb, Color(0xFF5C00FF)),
        ProductModel("Chicken", R.drawable.chicken, Color(0xFFFF0000)),
        ProductModel("Turkey", R.drawable.turkey, Color(0xFF447ACE)),
        ProductModel("Fish", R.drawable.fish, Color(0xFFFF1A9B)),
        ProductModel("Crab", R.drawable.crab, Color(0xFF5C00FF)),
        ProductModel("Lobster", R.drawable.lobster, Color(0xFFFF0000)),
    )
}

fun getDairyData(): List<ProductModel> {
    return listOf(
        ProductModel("Milk", R.drawable.milk, Color(0xFFFF1A9B)),
        ProductModel("Yogurt", R.drawable.yogurt, Color(0xFF5C00FF)),
        ProductModel("Butter", R.drawable.butter, Color(0xFFFF0000)),
        ProductModel("Cheese", R.drawable.cheese, Color(0xFF447ACE)),
        ProductModel("SourCream", R.drawable.sourcream, Color(0xFFFF1A9B)),
        ProductModel("IceCream", R.drawable.icecream, Color(0xFF5C00FF)),
        ProductModel("WhippedCream", R.drawable.whippedcream, Color(0xFFFF0000)),
    )
}

fun getVegetableData(): List<ProductModel> {
    return listOf(
        ProductModel("Tomato", R.drawable.tomato, Color(0xFFFF1A9B)),
        ProductModel("Potato", R.drawable.potato, Color(0xFF5C00FF)),
        ProductModel("Eggplant", R.drawable.eggplant, Color(0xFFFF0000)),
        ProductModel("Broccoli", R.drawable.broccoli, Color(0xFF447ACE)),
        ProductModel("BellPepper", R.drawable.bellpepper, Color(0xFFFF1A9B)),
        ProductModel("Peas", R.drawable.peas, Color(0xFF5C00FF)),
        ProductModel("Corn", R.drawable.corn, Color(0xFFFF0000)),
        ProductModel("Carrot", R.drawable.carrot, Color(0xFFFF1A9B)),
        ProductModel("Lettuce", R.drawable.lettuce, Color(0xFF5C00FF)),
        ProductModel("Spinach", R.drawable.spinach, Color(0xFFFF0000)),
    )
}

fun getFruitsData(): List<ProductModel> {
    return listOf(
        ProductModel("Apple", R.drawable.apple, Color(0xFFFF1A9B)),
        ProductModel("Banana", R.drawable.banana, Color(0xFF5C00FF)),
        ProductModel("Grapes", R.drawable.grapes, Color(0xFFFF0000)),
        ProductModel("Watermelon", R.drawable.watermelon, Color(0xFF447ACE)),
        ProductModel("Pomegranate", R.drawable.pomegranate, Color(0xFFFF1A9B)),
        ProductModel("Mango", R.drawable.mango, Color(0xFF5C00FF)),
        ProductModel("Orange", R.drawable.orange, Color(0xFFFF0000)),
        ProductModel("Kiwi", R.drawable.kiwi, Color(0xFFFF1A9B)),
    )
}

object Data {
    val meatData = mapOf(Pair<String,List<ProductModel>>("Meats", getMeatData()))
    val dairyData = mapOf(Pair<String,List<ProductModel>>("Dairy", getDairyData()))
    val vegetableData = mapOf(Pair<String,List<ProductModel>>("Vegetables", getVegetableData()))
    val fruitData = mapOf(Pair<String,List<ProductModel>>("Fruits", getFruitsData()))
}