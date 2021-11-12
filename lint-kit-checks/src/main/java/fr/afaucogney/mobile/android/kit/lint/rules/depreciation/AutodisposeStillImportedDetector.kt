@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.depreciation

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UFile
import java.util.EnumSet

class AutodisposeStillImportedDetector : Detector(), Detector.UastScanner {

    ///////////////////////////////////////////////////////////////////////////
    // CONST
    ///////////////////////////////////////////////////////////////////////////

    companion object {
        val ISSUE = Issue.create(
            "AutodisposeIsDeprecated",
            "Autodispose must not be used anymore.",
            "Because bindAndSub rx helpers is sufficient and simplest method",
            Category.CORRECTNESS,
            10,
            Severity.ERROR,
            Implementation(
                AutodisposeStillImportedDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES)
            )
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // CONFIGURATION
    ///////////////////////////////////////////////////////////////////////////

    override fun getApplicableUastTypes() = listOf(UFile::class.java)

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override fun createUastHandler(context: JavaContext) = RuleHandler(context)

    ///////////////////////////////////////////////////////////////////////////
    // HELPER
    ///////////////////////////////////////////////////////////////////////////

    class RuleHandler(private val context: JavaContext) : UElementHandler() {
        override fun visitFile(node: UFile) {
            for (import in node.imports) {
                import.importReference?.asRenderString()?.let {
                    if (it.contains("autodispose")) {
                        context.report(
                            ISSUE,
                            node,
                            context.getNameLocation(node),
                            "Autodispose shall not be used, use bindAndSub instead"
                        )
                    }
                }
            }
        }
    }
}
