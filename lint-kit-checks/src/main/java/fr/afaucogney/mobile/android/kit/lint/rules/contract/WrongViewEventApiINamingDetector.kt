@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.detector.api.Issue
import fr.afaucogney.mobile.android.kit.lint.helper.viewEventInterface
import org.jetbrains.uast.UClass

class WrongViewEventApiINamingDetector :
    AbstractFeatureContractViewApiIsNotCompliantDetector() {

    companion object {
        private const val issueId = "ViewEventContractApiIsNotCompliant"
        private const val className = "ViewEvent"
        private val compliantMethodPrefixes = listOf(
            "on",
        )

        val ISSUE = buildIssue(
            issueId,
            className,
            compliantMethodPrefixes,
            WrongViewEventApiINamingDetector::class.java
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override val issueId = WrongViewEventApiINamingDetector.issueId
    override val className = WrongViewEventApiINamingDetector.className
    override val compliantMethods = WrongViewEventApiINamingDetector.compliantMethodPrefixes
    override val issue: Issue = ISSUE

    override fun UClass.selectClasses(): UClass? {
        return this.viewEventInterface
    }
}
