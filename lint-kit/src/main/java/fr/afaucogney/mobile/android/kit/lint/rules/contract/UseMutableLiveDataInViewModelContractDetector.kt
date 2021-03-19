@file:Suppress("UnstableApiUsage")

package fr.niji.mobile.android.kit.lit.rules.contract

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import fr.niji.mobile.android.kit.lit.helper.isFeatureContract
import com.intellij.psi.impl.compiled.ClsTypeElementImpl
import org.jetbrains.uast.UClass
import java.util.*

class UseMutableLiveDataInViewModelContractDetector : Detector(), SourceCodeScanner {

    ///////////////////////////////////////////////////////////////////////////
    // CONST
    ///////////////////////////////////////////////////////////////////////////

    companion object {
        val ISSUE_MUTABLELIVEDATA_IN_FEATURE_CONTRACT = Issue.create("MutableLiveData",
                "Feature ViewModel Contract Interface should only expose LiveData.",
                "Contract should not expose MutableLiveData but only LiveData",
                Category.COMPLIANCE,
                8,
                Severity.ERROR,
                Implementation(
                        UseMutableLiveDataInViewModelContractDetector::class.java,
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
                node.innerClasses.find { it.name == "ViewModel" }.let {
                    it?.methods?.forEach {
                        if ((it.returnTypeElement as ClsTypeElementImpl).canonicalText.contains("mutablelivedata", true)) {
                            context.report(
                                    ISSUE_MUTABLELIVEDATA_IN_FEATURE_CONTRACT,
                                    node,
                                    context.getNameLocation(it),
                                    "The ViewModel interface method should only return LiveData (immutable)"
                            )
                        }
                    }
                }
            }
        }
    }
}