@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.detector.api.Issue
import fr.afaucogney.mobile.android.kit.lint.helper.viewEventInterface
import fr.afaucogney.mobile.android.kit.lint.helper.viewNavigationInterface
import org.jetbrains.uast.UClass

class ViewEventApiIsNotCompliantDetector :
    AbstractFeatureContractViewApiIsNotCompliantDetector() {

    companion object {
        private const val issueId = "ViewEventContractApiIsNotCompliant"
        private const val className = "ViewEvent"
        private val compliantMethods = listOf(
            "on",
        )

        val ISSUE = buildIssue(
            issueId,
            className,
            compliantMethods,
            ViewEventApiIsNotCompliantDetector::class.java
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override val issueId = ViewEventApiIsNotCompliantDetector.issueId
    override val className = ViewEventApiIsNotCompliantDetector.className
    override val compliantMethods = ViewEventApiIsNotCompliantDetector.compliantMethods
    override val issue: Issue = ISSUE

    override fun UClass.selectClasses(): UClass? {
        return this.viewEventInterface
    }
}
