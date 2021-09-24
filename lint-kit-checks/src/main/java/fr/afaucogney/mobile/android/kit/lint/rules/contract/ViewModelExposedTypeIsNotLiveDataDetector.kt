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
import org.jetbrains.uast.UMethod
import java.util.EnumSet

class ViewModelExposedTypeIsNotLiveDataDetector : Detector(), SourceCodeScanner {

    ///////////////////////////////////////////////////////////////////////////
    // CONST
    ///////////////////////////////////////////////////////////////////////////

    companion object {
        val ISSUE_VIEWMODEL_EXPOSED_TYPE_ARE_NOT_LIVEDATA = Issue.create(
            "ViewModelMethodDoesntReturnLiveData",
            "Feature ViewModel Contract Interface should only expose method that return LiveData.",
            "Contract should not expose other than LiveData to prevent memory leaks between ViewModel" +
                    " and ViewModel owners/users",
            Category.PERFORMANCE,
            8,
            Severity.ERROR,
            Implementation(
                ViewModelExposedTypeIsNotLiveDataDetector::class.java,
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
                    clazz?.methods?.forEach { method ->
                        when {
                            // No return type is OK
                            method.returnType?.canonicalText?.equals("void")
                                ?: false -> {
                                Unit
                            }
                            // Nullable ReturnType is NOK
                            method.text.endsWith("?") -> {
                                reportIssue(
                                    node,
                                    method,
                                    "nullable ${method.returnType!!.canonicalText}"
                                )
                            }
                            // Is LiveData
                            method.returnType?.canonicalText?.startsWith("androidx.lifecycle.LiveData<")
                                ?: false -> {
                                if (method.text.contains("?")) {
                                    // But has nullable inside
                                    reportIssue(
                                        node,
                                        method,
                                        "LiveData type with nullable type inside"
                                    )
                                }
                            }
                            else -> {
                                reportIssue(
                                    node,
                                    method,
                                    method.returnType!!.canonicalText
                                )
                            }
                        }
                    }
                }
            }
        }

        private fun reportIssue(node: UClass, method: UMethod, returnType: String) {
            context.report(
                ISSUE_VIEWMODEL_EXPOSED_TYPE_ARE_NOT_LIVEDATA,
                node,
                context.getNameLocation(method),
                "The ViewModel interface method should only return LiveData, not $returnType type"
            )
        }
    }
}