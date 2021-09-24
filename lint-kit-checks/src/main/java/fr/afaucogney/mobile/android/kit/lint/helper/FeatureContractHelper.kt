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

val UClass.viewModelInterface: UClass?
    get() = this.innerClasses.find { it.name == "ViewModel" }

val UClass.viewCapabilitiesInterface: UClass?
    get() = this.innerClasses.find { it.name == "ViewCapabilities" }

val UClass.viewNavigationInterface: UClass?
    get() = this.innerClasses.find { it.name == "ViewNavigation" }

val UClass.viewEventInterface: UClass?
    get() = this.innerClasses.find { it.name == "ViewEvent" }

val UClass.viewTagInterface: UClass?
    get() = this.innerClasses.find { it.name == "ViewTag" }

