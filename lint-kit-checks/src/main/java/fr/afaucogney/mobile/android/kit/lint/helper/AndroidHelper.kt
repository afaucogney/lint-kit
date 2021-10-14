package fr.afaucogney.mobile.android.kit.lint.helper

import org.jetbrains.uast.UClass

fun UClass.isActivityBasedClass(): Boolean {
    var target = this
    do {
        if (target.name.equals("Activity") || target.name.equals("AppCompatActivity")) {
            return true
        } else {
            target.superClass?.run {
                target = this
            } ?: return false
        }
    } while (target.superClass != null)
    return false
}

fun UClass.isFragmentBasedClass(): Boolean {
    var target = this
    do {
        if (target.name.equals("Fragment")) {
            return true
        } else {
            target.superClass?.run {
                target = this
            } ?: return false
        }
    } while (target.superClass != null)
    return false
}