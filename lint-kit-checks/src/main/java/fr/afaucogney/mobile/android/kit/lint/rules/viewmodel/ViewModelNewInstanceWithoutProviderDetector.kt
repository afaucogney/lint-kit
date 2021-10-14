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
import fr.afaucogney.mobile.android.kit.lint.helper.isViewModelProviderClass
import org.jetbrains.kotlin.psi.psiUtil.parentsWithSelf
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UClass
import org.jetbrains.uast.getContainingDeclaration
import org.jetbrains.uast.getContainingUClass
import java.util.EnumSet

class ViewModelNewInstanceWithoutProviderDetector : Detector(), Detector.UastScanner {

    ///////////////////////////////////////////////////////////////////////////
    // CONST
    ///////////////////////////////////////////////////////////////////////////

    companion object {
        val ISSUE = Issue.create(
            "ViewModelNewInstance",
            "ViewModel should not be instantiated.",
            "To preserve the ViewModel from it re-creation, it should be injected from" +
                    "the ViewModelProvider.",
            Category.CORRECTNESS,
            10,
            Severity.ERROR,
            Implementation(
                ViewModelNewInstanceWithoutProviderDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES)
            )
        )
        private const val JC = "::class.java"
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
                        val exp = node.getContainingDeclaration()
                            ?.text!!
                            .split(").get(")[1]
                            .split(JC)[0]
                        if (exp.contains("Activity").not()) {
                            context.report(
                                ISSUE,
                                node,
                                context.getLocation(node),
                                "ViewModel scoped for activity must have the \'Activity\' in its name"
                            )
                        }
                    }
                }
            }
        }
    }
}