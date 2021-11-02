package fr.afaucogney.mobile.android.kit.lint.helper

import org.jetbrains.uast.UClass

fun UClass.containInitLambda(): Boolean {
    return this.text.contains("init\\s?\\{".toRegex())
}
