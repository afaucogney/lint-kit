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
import org.jetbrains.uast.UClass
import java.util.EnumSet

abstract class AbstractFeatureContractViewApiIsNotCompliantDetector :
    SourceCodeScanner,
    Detector() {

    ///////////////////////////////////////////////////////////////////////////
    // FACTORY
    ///////////////////////////////////////////////////////////////////////////

    companion object {
        fun buildIssue(
            issueId: String,
            className: String,
            compliantMethods: List<String>,
            clazz: Class<out Detector?>
        ): Issue {
            return Issue.create(
                issueId,
                "$className Feature Contract Interface should only expose compliant " +
                        "method names.",
                "$className Contract should only expose compliant method names : " +
                        "${compliantMethods.joinToString(", ")} in its Api.",
                Category.COMPLIANCE,
                5,
                Severity.ERROR,
                Implementation(
                    clazz,
                    EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES)
                )
            )
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // CONST
    ///////////////////////////////////////////////////////////////////////////

    abstract val compliantMethods: List<String>
    abstract val issueId: String
    abstract val className: String
    abstract val issue: Issue

    ///////////////////////////////////////////////////////////////////////////
    // CONFIGURATION
    ///////////////////////////////////////////////////////////////////////////

    override fun getApplicableUastTypes() = listOf(UClass::class.java)

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitClass(node: UClass) {
            if (node.isFeatureContract()) {
                node.selectClasses()?.let { clazz ->
                    clazz.methods.forEach { method ->
                        if (compliantMethods.none { method.name.startsWith(it) }) {
                            context.report(
                                issue,
                                node,
                                context.getNameLocation(method),
                                "The $className interface methods should only be named with compliant names"
                            )
                        } else {
                            Unit
                        }
                    }
                }
            }
        }
    }

    abstract fun UClass.selectClasses(): UClass?
}
