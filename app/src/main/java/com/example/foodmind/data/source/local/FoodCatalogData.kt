package com.example.foodmind.data.source.local

import com.example.foodmind.data.model.FoodItemDto
import com.example.foodmind.data.model.NutritionInfoDto
import kotlin.math.round

internal object FoodCatalogData {
    val items: List<FoodItemDto> = buildFoodItems()

    private fun buildFoodItems(): List<FoodItemDto> {
        val categories = listOf(
            CategorySeed(
                idPrefix = "fruit",
                category = "Fruits",
                descriptionPrefix = "Fresh",
                baseNutrition = NutritionInfoDto(
                    calories = 60,
                    proteinGrams = 0.6,
                    fatGrams = 0.3,
                    carbsGrams = 15.0,
                    fiberGrams = 2.5,
                    sugarGrams = 11.0,
                    servingSizeGrams = 100
                ),
                baseRating = 4.5,
                names = listOf(
                    "Apple",
                    "Banana",
                    "Orange",
                    "Strawberry",
                    "Blueberry",
                    "Mango",
                    "Pineapple",
                    "Grapes",
                    "Peach",
                    "Pear",
                    "Kiwi",
                    "Watermelon",
                    "Papaya",
                    "Cherry",
                    "Raspberry"
                )
            ),
            CategorySeed(
                idPrefix = "vegetable",
                category = "Vegetables",
                descriptionPrefix = "Garden",
                baseNutrition = NutritionInfoDto(
                    calories = 40,
                    proteinGrams = 2.0,
                    fatGrams = 0.3,
                    carbsGrams = 8.0,
                    fiberGrams = 3.0,
                    sugarGrams = 3.0,
                    servingSizeGrams = 100
                ),
                baseRating = 4.0,
                names = listOf(
                    "Carrot",
                    "Broccoli",
                    "Spinach",
                    "Tomato",
                    "Cucumber",
                    "Bell Pepper",
                    "Zucchini",
                    "Eggplant",
                    "Cauliflower",
                    "Kale",
                    "Onion",
                    "Garlic",
                    "Asparagus",
                    "Sweet Potato",
                    "Mushroom"
                )
            ),
            CategorySeed(
                idPrefix = "grain",
                category = "Grains",
                descriptionPrefix = "Whole grain",
                baseNutrition = NutritionInfoDto(
                    calories = 120,
                    proteinGrams = 4.0,
                    fatGrams = 1.5,
                    carbsGrams = 24.0,
                    fiberGrams = 3.0,
                    sugarGrams = 1.0,
                    servingSizeGrams = 100
                ),
                baseRating = 4.1,
                names = listOf(
                    "Oats",
                    "Brown Rice",
                    "Quinoa",
                    "Whole Wheat Bread",
                    "Bulgur",
                    "Barley",
                    "Corn",
                    "Buckwheat",
                    "Couscous",
                    "Rye",
                    "Millet",
                    "Farro"
                )
            ),
            CategorySeed(
                idPrefix = "dairy",
                category = "Dairy",
                descriptionPrefix = "Creamy",
                baseNutrition = NutritionInfoDto(
                    calories = 130,
                    proteinGrams = 8.0,
                    fatGrams = 5.0,
                    carbsGrams = 10.0,
                    fiberGrams = 0.0,
                    sugarGrams = 7.0,
                    servingSizeGrams = 100
                ),
                baseRating = 4.2,
                names = listOf(
                    "Greek Yogurt",
                    "Milk",
                    "Cheddar Cheese",
                    "Cottage Cheese",
                    "Kefir",
                    "Mozzarella",
                    "Ricotta",
                    "Parmesan",
                    "Skyr",
                    "Butter",
                    "Cream Cheese",
                    "Yogurt Drink"
                )
            ),
            CategorySeed(
                idPrefix = "meat",
                category = "Meat & Poultry",
                descriptionPrefix = "Protein-rich",
                baseNutrition = NutritionInfoDto(
                    calories = 190,
                    proteinGrams = 24.0,
                    fatGrams = 9.0,
                    carbsGrams = 0.0,
                    fiberGrams = 0.0,
                    sugarGrams = 0.0,
                    servingSizeGrams = 120
                ),
                baseRating = 4.3,
                names = listOf(
                    "Chicken Breast",
                    "Turkey Breast",
                    "Beef Sirloin",
                    "Pork Loin",
                    "Lamb Chop",
                    "Duck Breast",
                    "Ground Beef",
                    "Chicken Thigh",
                    "Turkey Sausage",
                    "Veal Cutlet"
                )
            ),
            CategorySeed(
                idPrefix = "seafood",
                category = "Seafood",
                descriptionPrefix = "Ocean-fresh",
                baseNutrition = NutritionInfoDto(
                    calories = 160,
                    proteinGrams = 22.0,
                    fatGrams = 6.0,
                    carbsGrams = 0.0,
                    fiberGrams = 0.0,
                    sugarGrams = 0.0,
                    servingSizeGrams = 120
                ),
                baseRating = 4.2,
                names = listOf(
                    "Salmon",
                    "Tuna",
                    "Shrimp",
                    "Cod",
                    "Sardines",
                    "Mackerel",
                    "Trout",
                    "Crab",
                    "Mussels",
                    "Scallops",
                    "Tilapia",
                    "Squid"
                )
            ),
            CategorySeed(
                idPrefix = "legume",
                category = "Legumes",
                descriptionPrefix = "Hearty",
                baseNutrition = NutritionInfoDto(
                    calories = 140,
                    proteinGrams = 9.0,
                    fatGrams = 2.0,
                    carbsGrams = 24.0,
                    fiberGrams = 7.0,
                    sugarGrams = 2.0,
                    servingSizeGrams = 100
                ),
                baseRating = 4.0,
                names = listOf(
                    "Lentils",
                    "Chickpeas",
                    "Black Beans",
                    "Kidney Beans",
                    "Edamame",
                    "Green Peas",
                    "Soybeans",
                    "Pinto Beans",
                    "Navy Beans",
                    "Lima Beans"
                )
            ),
            CategorySeed(
                idPrefix = "nuts",
                category = "Nuts & Seeds",
                descriptionPrefix = "Crunchy",
                baseNutrition = NutritionInfoDto(
                    calories = 180,
                    proteinGrams = 6.0,
                    fatGrams = 15.0,
                    carbsGrams = 6.0,
                    fiberGrams = 3.0,
                    sugarGrams = 2.0,
                    servingSizeGrams = 30
                ),
                baseRating = 4.4,
                names = listOf(
                    "Almonds",
                    "Walnuts",
                    "Cashews",
                    "Pistachios",
                    "Hazelnuts",
                    "Peanuts",
                    "Pumpkin Seeds",
                    "Chia Seeds",
                    "Flax Seeds",
                    "Sunflower Seeds"
                )
            ),
            CategorySeed(
                idPrefix = "snack",
                category = "Snacks",
                descriptionPrefix = "Snackable",
                baseNutrition = NutritionInfoDto(
                    calories = 210,
                    proteinGrams = 6.0,
                    fatGrams = 8.0,
                    carbsGrams = 28.0,
                    fiberGrams = 3.0,
                    sugarGrams = 10.0,
                    servingSizeGrams = 60
                ),
                baseRating = 4.1,
                names = listOf(
                    "Granola Bar",
                    "Dark Chocolate",
                    "Popcorn",
                    "Rice Cakes",
                    "Protein Bar",
                    "Trail Mix",
                    "Pretzels",
                    "Apple Chips",
                    "Beef Jerky",
                    "Baked Chips"
                )
            ),
            CategorySeed(
                idPrefix = "drink",
                category = "Drinks",
                descriptionPrefix = "Refreshing",
                baseNutrition = NutritionInfoDto(
                    calories = 90,
                    proteinGrams = 1.0,
                    fatGrams = 0.5,
                    carbsGrams = 21.0,
                    fiberGrams = 0.0,
                    sugarGrams = 18.0,
                    servingSizeGrams = 250
                ),
                baseRating = 4.0,
                names = listOf(
                    "Green Tea",
                    "Black Coffee",
                    "Orange Juice",
                    "Berry Smoothie",
                    "Coconut Water",
                    "Herbal Tea",
                    "Sparkling Water",
                    "Kombucha",
                    "Milkshake",
                    "Sports Drink"
                )
            ),
            CategorySeed(
                idPrefix = "dessert",
                category = "Desserts",
                descriptionPrefix = "Sweet",
                baseNutrition = NutritionInfoDto(
                    calories = 260,
                    proteinGrams = 4.0,
                    fatGrams = 12.0,
                    carbsGrams = 32.0,
                    fiberGrams = 1.0,
                    sugarGrams = 22.0,
                    servingSizeGrams = 120
                ),
                baseRating = 4.6,
                names = listOf(
                    "Ice Cream",
                    "Cheesecake",
                    "Brownie",
                    "Fruit Tart",
                    "Pudding",
                    "Sorbet",
                    "Muffin",
                    "Pancakes",
                    "Waffles",
                    "Macaron"
                )
            )
        )

        return categories.flatMap { generateCategoryItems(it) }
    }

    private fun generateCategoryItems(seed: CategorySeed): List<FoodItemDto> {
        return seed.names.mapIndexed { index, name ->
            val delta = (index % 5) - 2
            val nutrition = seed.baseNutrition.adjustBy(delta)
            FoodItemDto(
                id = "${seed.idPrefix}-${index + 1}",
                name = name,
                description = "${seed.descriptionPrefix} $name with balanced nutrition and flavor.",
                category = seed.category,
                nutrition = nutrition,
                tasteRating = (seed.baseRating + delta * 0.12).coerceIn(1.0, 5.0),
                imageUrl = "https://picsum.photos/seed/${seed.idPrefix}-${index + 1}/600/400"
            )
        }
    }

    private fun NutritionInfoDto.adjustBy(delta: Int): NutritionInfoDto {
        val calories = (calories + delta * 8).coerceAtLeast(20)
        val protein = (proteinGrams + delta * 0.6).coerceAtLeast(0.0)
        val fat = (fatGrams + delta * 0.5).coerceAtLeast(0.0)
        val carbs = (carbsGrams + delta * 0.8).coerceAtLeast(0.0)
        val fiber = (fiberGrams + delta * 0.2).coerceAtLeast(0.0)
        val sugar = (sugarGrams + delta * 0.3).coerceAtLeast(0.0)
        val serving = (servingSizeGrams + delta * 5).coerceAtLeast(30)

        return copy(
            calories = calories,
            proteinGrams = roundTo1Decimal(protein),
            fatGrams = roundTo1Decimal(fat),
            carbsGrams = roundTo1Decimal(carbs),
            fiberGrams = roundTo1Decimal(fiber),
            sugarGrams = roundTo1Decimal(sugar),
            servingSizeGrams = serving
        )
    }

    private fun roundTo1Decimal(value: Double): Double {
        return round(value * 10.0) / 10.0
    }

    private data class CategorySeed(
        val idPrefix: String,
        val category: String,
        val descriptionPrefix: String,
        val baseNutrition: NutritionInfoDto,
        val baseRating: Double,
        val names: List<String>
    )
}
