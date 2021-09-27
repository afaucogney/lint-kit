@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.detector.api.Issue
import fr.afaucogney.mobile.android.kit.lint.helper.viewModelInterface
import org.jetbrains.uast.UClass

class WrongViewModelApiNamingDetector :
    AbstractFeatureContractViewApiIsNotCompliantDetector() {

    companion object {
        private const val issueId = "ViewModelContractApiIsNotCompliant"
        private const val className = "ViewModel"
        private val compliantMethods = listOf(
            "request",
            "start",
            "observe",
            "get",
        )

        val ISSUE = buildIssue(
            issueId,
            className,
            compliantMethods,
            WrongViewModelApiNamingDetector::class.java
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override val issueId = WrongViewModelApiNamingDetector.issueId
    override val className = WrongViewModelApiNamingDetector.className
    override val compliantMethods = WrongViewModelApiNamingDetector.compliantMethods
    override val issue: Issue = ISSUE

    override fun UClass.selectClasses(): UClass? {
        return this.viewModelInterface
    }
}
