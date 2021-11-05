package fr.afaucogney.mobile.android.kit.lint.helper

private const val ALPHA_NUMERIC_REGEX = "[^A-Za-z0-9 ]"

fun String.isUpperCase(): Boolean {
    var isUpperCase = true
    Regex(ALPHA_NUMERIC_REGEX)
        .replace(this, "")
        .removeWhitespaces()
        .toList()
        .forEach { if (!it.isUpperCase()) isUpperCase = false }
    return isUpperCase
}

fun String.removeWhitespaces() = replace(" ", "")
