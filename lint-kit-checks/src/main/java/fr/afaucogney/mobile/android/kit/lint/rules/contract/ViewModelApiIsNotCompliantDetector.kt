@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.detector.api.Issue
import fr.afaucogney.mobile.android.kit.lint.helper.viewModelInterface
import org.jetbrains.uast.UClass

class ViewModelApiIsNotCompliantDetector :
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
            ViewModelApiIsNotCompliantDetector::class.java
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override val issueId = ViewModelApiIsNotCompliantDetector.issueId
    override val className = ViewModelApiIsNotCompliantDetector.className
    override val compliantMethods = ViewModelApiIsNotCompliantDetector.compliantMethods
    override val issue: Issue = ISSUE

    override fun UClass.selectClasses(): UClass? {
        return this.viewModelInterface
    }
}
