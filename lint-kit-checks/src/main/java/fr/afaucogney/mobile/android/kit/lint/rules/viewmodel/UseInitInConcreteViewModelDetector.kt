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
import fr.afaucogney.mobile.android.kit.lint.helper.containInitLambda
import fr.afaucogney.mobile.android.kit.lint.helper.isBaseViewModel
import fr.afaucogney.mobile.android.kit.lint.helper.isConcreteViewModel
import org.jetbrains.uast.UClass
import java.util.*

class UseInitInConcreteViewModelDetector : Detector(), Detector.UastScanner {

    ///////////////////////////////////////////////////////////////////////////
    // CONST
    ///////////////////////////////////////////////////////////////////////////

    companion object {
        val ISSUE_INIT_IN_VIEWMODEL = Issue.create("KotlinInitInAppViewModel",
                "ViewModel should not implement the init routine.",
                "Because of the BaseViewModel, initialisation should be done by overriding onCreated() method",
                Category.CORRECTNESS,
                9,
                Severity.ERROR,
                Implementation(
                        UseInitInConcreteViewModelDetector::class.java,
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
            if (node.isBaseViewModel().not() && node.isConcreteViewModel(context) && node.containInitLambda()) {
                context.report(
                        ISSUE_INIT_IN_VIEWMODEL,
                        node,
                        context.getNameLocation(node),
                        "init {...} should not be defined in concrete ViewModels"
                )
            }
        }
    }
}