package fr.afaucogney.mobile.android.kit.lint.helper

import com.android.tools.lint.detector.api.JavaContext
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UClass

fun UClass.isBaseViewModel(): Boolean {
    return this.name?.contains("baseviewmodel", true) ?: false
}

fun UClass.isConcreteViewModel(context: JavaContext): Boolean {
    return this.javaPsi.superClass?.let {
        context.evaluator.getQualifiedName(it) == "fr.afaucogney.app.presentation.common.viewmodel.BaseViewModel"
    } ?: false
}

fun UCallExpression.isViewModelProviderClass(): Boolean {
    return this.classReference?.resolvedName == "ViewModelProvider"
}
