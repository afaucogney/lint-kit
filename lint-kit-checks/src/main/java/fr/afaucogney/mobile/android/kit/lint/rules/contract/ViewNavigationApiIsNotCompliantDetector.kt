@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.detector.api.Issue
import fr.afaucogney.mobile.android.kit.lint.helper.viewCapabilitiesInterface
import fr.afaucogney.mobile.android.kit.lint.helper.viewNavigationInterface
import org.jetbrains.uast.UClass

class ViewNavigationApiIsNotCompliantDetector :
    AbstractFeatureContractViewApiIsNotCompliantDetector() {

    companion object {
        private const val issueId = "ViewNavigationContractApiIsNotCompliant"
        private const val className = "ViewNavigation"
        private val compliantMethods = listOf(
            "goTo",
            "quit",
        )

        val ISSUE = buildIssue(
            issueId,
            className,
            compliantMethods,
            ViewNavigationApiIsNotCompliantDetector::class.java
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override val issueId = ViewNavigationApiIsNotCompliantDetector.issueId
    override val className = ViewNavigationApiIsNotCompliantDetector.className
    override val compliantMethods = ViewNavigationApiIsNotCompliantDetector.compliantMethods
    override val issue: Issue = ISSUE

    override fun UClass.selectClasses(): UClass? {
        return this.viewNavigationInterface
    }
}
