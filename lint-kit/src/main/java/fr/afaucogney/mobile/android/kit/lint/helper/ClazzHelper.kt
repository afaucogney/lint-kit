package fr.niji.mobile.android.kit.lit.helper

import org.jetbrains.uast.UClass

fun UClass.containInitLambda(): Boolean {
    return this.text.contains("init\\s?\\{".toRegex())
}
