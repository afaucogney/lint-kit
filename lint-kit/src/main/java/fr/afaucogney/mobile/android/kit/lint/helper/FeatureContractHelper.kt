package fr.niji.mobile.android.kit.lit.helper

import org.jetbrains.uast.UClass

fun UClass.isFeatureContract(): Boolean {
    return when {
        !this.isInterface -> false
        this.name == null -> false
        this.name!!.endsWith("contract", true) -> true
        else -> false
    }
}
