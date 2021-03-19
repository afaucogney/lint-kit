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
import fr.afaucogney.mobile.android.kit.lint.helper.isFeatureContract
import org.jetbrains.uast.UClass
import java.util.*

class WrongFeatureContractNamingDetector : Detector(), Detector.UastScanner {

    ///////////////////////////////////////////////////////////////////////////
    // CONST
    ///////////////////////////////////////////////////////////////////////////

    companion object {
        val ISSUE_FEATURE_CONTRACT_NAMING = Issue.create("FeatureContractNaming",
                "Feature Contract Interface should be well named.",
                "Contract Interface interface name should start by I and finish with Contract",
                Category.COMPLIANCE,
                5,
                Severity.WARNING,
                Implementation(
                        WrongFeatureContractNamingDetector::class.java,
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
                node.name?.let { name ->
                    if (name.endsWith("contract")) {
                        context.report(
                                ISSUE_FEATURE_CONTRACT_NAMING,
                                node,
                                context.getNameLocation(node),
                                "Camel case not respected contract, the name should end by Contract"
                        )
                    }
                    if (!name.startsWith("I", true) || name[1].isLowerCase()) {
                        context.report(
                                ISSUE_FEATURE_CONTRACT_NAMING,
                                node,
                                context.getNameLocation(node),
                                "Interface contract should start with I, to help finding them with autocomplete"
                        )
                    }
                }
            }
        }
    }
}