@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import fr.afaucogney.mobile.android.kit.lint.helper.isFeatureContract
import org.jetbrains.kotlin.asJava.builder.memberIndex
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UMethod
import java.util.EnumSet

class ViewModelMethodParameterIsCallbackDetector : Detector(), SourceCodeScanner {

    companion object {
        val ISSUE = Issue.create(
            "ViewModelMethodParameterIsCallback",
            "Feature ViewModel Contract Interface should only expose members of type LiveData.",
            "Contract should not expose other types than LiveData to prevent memory leaks between ViewModel" +
                    " and ViewModel owners/users",
            Category.COMPLIANCE,
            8,
            Severity.ERROR,
            Implementation(
                ViewModelMethodParameterIsCallbackDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES)
            )
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // CONFIGURATION
    ///////////////////////////////////////////////////////////////////////////

    override fun getApplicableUastTypes() = listOf(UClass::class.java)

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override fun createUastHandler(context: JavaContext) = RuleHandler(context)

    ///////////////////////////////////////////////////////////////////////////
    // HELPER
    ///////////////////////////////////////////////////////////////////////////

    class RuleHandler(private val context: JavaContext) : UElementHandler() {
        override fun visitClass(node: UClass) {
            if (node.isFeatureContract()) {
                node.innerClasses.find { it.name == "ViewModel" }.let { clazz ->
                    clazz?.memberIndex.let {
                        Unit
                    }
                    clazz?.methods?.forEach { method ->
                        method.parameters.forEach { param ->
                            when {
                                // KO if param name contains callback
                                param.name?.contains("callback", true) == true -> {
                                    reportIssue(
                                        node,
                                        method,
                                        "callback type : ${param.type}"
                                    )
                                }
                                // KO if param type name contains callback
                                param.type.toString().contains("callback", true) -> {
                                    reportIssue(
                                        node,
                                        method,
                                        "callback type : ${param.type}"
                                    )
                                }
                                // KO if param type contains Lambda
                                param.type.toString().contains("function", true) -> {
                                    reportIssue(
                                        node,
                                        method,
                                        "function type : ${param.name}"
                                    )
                                }
                                // KO if param name contains Lambda
                                param.name?.toString().contains("function", true) -> {
                                    reportIssue(
                                        node,
                                        method,
                                        "function name : ${param.name}"
                                    )
                                }
                                else -> {
                                    Unit
                                }
                            }
                        }
                        when {
                            method.returnType?.toString()?.contains("function", true) == true -> {
                                reportIssue(
                                    node,
                                    method,
                                    "method returns type is Function"
                                )
                            }
                            method.returnType?.toString()?.contains("callback", true) == true -> {
                                reportIssue(
                                    node,
                                    method,
                                    "method returns type is potentially callback"
                                )
                            }
                        }
                    }
                }
            }
        }

        private fun reportIssue(node: UClass, method: UMethod, returnType: String) {
            context.report(
                ISSUE,
                node,
                context.getNameLocation(method),
                "The ViewModel method parameter should not by typed with $returnType"
            )
        }
    }
}
