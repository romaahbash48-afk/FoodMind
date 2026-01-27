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
 * Extension function to check if a String is a valid email
 */
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * Extension function to capitalize first letter of each word
 */
fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}
