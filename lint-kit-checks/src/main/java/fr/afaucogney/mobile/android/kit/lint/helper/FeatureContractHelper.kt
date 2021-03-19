package fr.afaucogney.mobile.android.kit.lint.helper

import org.jetbrains.uast.UClass

fun UClass.isFeatureContract(): Boolean {
    return when {
        !this.isInterface -> false
        this.name == null -> false
        this.name!!.endsWith("contract", true) -> true
        else -> false
    }
}
