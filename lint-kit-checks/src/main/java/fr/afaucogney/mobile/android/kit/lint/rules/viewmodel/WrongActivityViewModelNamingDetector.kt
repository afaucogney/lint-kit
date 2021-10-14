@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.viewmodel

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import fr.afaucogney.mobile.android.kit.lint.helper.isActivityBasedClass
import fr.afaucogney.mobile.android.kit.lint.helper.isFragmentBasedClass
import fr.afaucogney.mobile.android.kit.lint.helper.isViewModelProviderClass
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UClass
import org.jetbrains.uast.getContainingDeclaration
import org.jetbrains.uast.getContainingUClass
import java.util.EnumSet

class WrongActivityViewModelNamingDetector : Detector(), Detector.UastScanner {

    ///////////////////////////////////////////////////////////////////////////
    // CONST
    ///////////////////////////////////////////////////////////////////////////

    companion object {
        val ISSUE = Issue.create(
            "WrongActivityViewModelNaming",
            "ViewModel should not be name xxxActivityViewModel when bind to Activity.",
            "For better visibility, we add Activity prefix to every ViewModel which is " +
                    "bound to Activity.",
            Category.COMPLIANCE,
            7,
            Severity.ERROR,
            Implementation(
                WrongActivityViewModelNamingDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES)
            )
        )

        private const val JC = "::class.java"
        private const val GAVM = "getActivityViewModel"
        private const val GVM = "getViewModel"
    }

    ///////////////////////////////////////////////////////////////////////////
    // CONFIGURATION
    ///////////////////////////////////////////////////////////////////////////

    override fun getApplicableUastTypes() = listOf(UCallExpression::class.java)

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override fun createUastHandler(context: JavaContext) = RuleHandler(context)

    ///////////////////////////////////////////////////////////////////////////
    // HELPER
    ///////////////////////////////////////////////////////////////////////////

    class RuleHandler(private val context: JavaContext) : UElementHandler() {
        override fun visitCallExpression(node: UCallExpression) {
            if (node.isViewModelProviderClass()) {
                if (node.kind.name == "constructor_call") {
                    if (node.getContainingUClass()?.isActivityBasedClass() == true) {
                        val targetViewModelProviderModelClass = node.getContainingDeclaration()
                            ?.text!!
                            .split(").get(")[1]
                            .split(JC)[0]
                        reportIssueIfNecessary(
                            context,
                            node,
                            targetViewModelProviderModelClass,
                            "activity"
                        )
                    } else if (node.getContainingUClass()?.isFragmentBasedClass() == true) {
                        val targetViewModelProviderModelClass = node.getContainingDeclaration()
                            ?.text!!
                            .split(").get(")[1]
                            .split(JC)[0]
                        val viewModelProviderContext = node.getContainingDeclaration()
                            ?.text!!
                            .split(").get(")[0]
                            .split("(")[1]
                        reportIssueIfNecessary(
                            context,
                            node,
                            targetViewModelProviderModelClass,
                            viewModelProviderContext
                        )
                    }
                }
            } else if (node.methodIdentifier?.name.equals(GVM, true)) {
                val targetViewModelProviderModelClass =
                    extractTargetViewModelProviderModelClass(
                        node,
                        GVM
                    )
                val viewModelProviderContext = extractViewModelProviderContext(
                    node,
                    GVM
                )
                reportIssueIfNecessary(
                    context,
                    node,
                    targetViewModelProviderModelClass,
                    viewModelProviderContext
                )
            } else if (node.methodIdentifier?.name.equals(GAVM, true)) {
                val targetViewModelProviderModelClass =
                    extractTargetViewModelProviderModelClass(
                        node,
                        GAVM
                    )
                val viewModelProviderContext = extractViewModelProviderContext(
                    node,
                    GVM
                )
                reportIssueIfNecessary(
                    context,
                    node,
                    targetViewModelProviderModelClass,
                    viewModelProviderContext
                )
            }
        }

        ///////////////////////////////////////////////////////////////////////////
        // HELPER
        ///////////////////////////////////////////////////////////////////////////

        private fun extractTargetViewModelProviderModelClass(
            node: UCallExpression?,
            key: String
        ): String {
            return node
                ?.getContainingDeclaration()
                ?.text
                ?.let {
                    it
                        .split(".$key")[1]
                        .let {
                            if (it.contains(JC)) {
                                return@let it
                                    .split(JC)[0]
                                    .split("(")[1]
                            } else {
                                return@let it
                                    .split("<")[1]
                                    .split(">")[0]
                            }
                        }
                } ?: "prout"
        }

        private fun extractViewModelProviderContext(
            node: UCallExpression?,
            key: String
        ): String {
            return node
                ?.getContainingDeclaration()
                ?.text
                ?.let {
                    it
                        .split(".$key")[0]
                        .split("=")[1]
                        .trim()
                        .replaceThisByContainingClass(node)
                } ?: "shit"
        }

        private fun reportIssueIfNecessary(
            context: JavaContext,
            node: UCallExpression,
            targetViewModelProviderModelClass: String,
            viewModelProviderContext: String
        ) {
            println(targetViewModelProviderModelClass + " - " + viewModelProviderContext)
            if (targetViewModelProviderModelClass.contains("Activity") &&
                viewModelProviderContext.contains("activity", true).not()
            ) {
                context.report(
                    ISSUE,
                    node,
                    context.getLocation(node),
                    "ViewModel scoped for fragment must not have the \'Activity\' prefix in its name"
                )
            } else if (targetViewModelProviderModelClass.contains("Activity").not() &&
                viewModelProviderContext.contains("activity", true)
            ) {
                context.report(
                    ISSUE,
                    node,
                    context.getLocation(node),
                    "ViewModel scoped for activity must have the \'Activity\' prefix in its name"
                )
            }
        }

        private fun String.replaceThisByContainingClass(node: UCallExpression): String {
            return when {
                node.getContainingUClass() == null -> this
                node.getContainingUClass()!!.isFragmentBasedClass() -> this.replace(
                    "this",
                    "fragment"
                )
                node.getContainingUClass()!!.isActivityBasedClass() -> this.replace(
                    "this",
                    "activity"
                )
                else -> this
            }
        }
    }
}

