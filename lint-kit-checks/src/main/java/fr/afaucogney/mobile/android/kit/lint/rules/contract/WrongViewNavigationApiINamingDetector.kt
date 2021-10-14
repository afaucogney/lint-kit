@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.detector.api.Issue
import fr.afaucogney.mobile.android.kit.lint.helper.viewNavigationInterface
import org.jetbrains.uast.UClass

class WrongViewNavigationApiINamingDetector :
    AbstractFeatureContractViewApiIsNotCompliantDetector() {

    companion object {
        private const val issueId = "ViewNavigationContractApiIsNotCompliant"
        private const val className = "ViewNavigation"
        private val compliantMethodPrefixes = listOf(
            "goTo",
            "quit",
        )

        val ISSUE = buildIssue(
            issueId,
            className,
            compliantMethodPrefixes,
            WrongViewNavigationApiINamingDetector::class.java
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override val issueId = WrongViewNavigationApiINamingDetector.issueId
    override val className = WrongViewNavigationApiINamingDetector.className
    override val compliantMethods = WrongViewNavigationApiINamingDetector.compliantMethodPrefixes
    override val issue: Issue = ISSUE

    override fun UClass.selectClasses(): UClass? {
        return this.viewNavigationInterface
    }
}
