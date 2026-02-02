package com.example.foodmind.data.importer

object ImportSeeds {
    val categories = listOf(
        CategorySeed(id = "dairy", name = "Dairy", offTag = "dairies"),
        CategorySeed(id = "cheese", name = "Cheese", offTag = "cheeses"),
        CategorySeed(id = "grains", name = "Grains", offTag = "cereals"),
        CategorySeed(id = "meat", name = "Meat & Poultry", offTag = "meats"),
        CategorySeed(id = "seafood", name = "Seafood", offTag = "seafoods"),
        CategorySeed(id = "vegetables", name = "Vegetables", offTag = "vegetables"),
        CategorySeed(id = "fruits", name = "Fruits", offTag = "fruits"),
        CategorySeed(id = "drinks", name = "Drinks", offTag = "beverages"),
        CategorySeed(id = "snacks", name = "Snacks", offTag = "snacks"),
        CategorySeed(id = "bakery", name = "Bakery", offTag = "breads")
    )
}
