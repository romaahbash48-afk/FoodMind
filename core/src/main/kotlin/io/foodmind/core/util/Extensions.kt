package io.foodmind.core.util

/**
 * Kotlin extension functions for common operations
 */

/**
 * Extension function to safely convert String to Int, returning null if conversion fails
 */
fun String?.toIntOrNull(): Int? {
    return this?.toIntOrNull()
}

/**
 * Extension function to check if a String is a valid email using simple regex
 * For production use, consider using a more comprehensive validation library
 */
fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$".toRegex()
    return emailRegex.matches(this)
}

/**
 * Extension function to capitalize first letter of each word
 */
fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}
