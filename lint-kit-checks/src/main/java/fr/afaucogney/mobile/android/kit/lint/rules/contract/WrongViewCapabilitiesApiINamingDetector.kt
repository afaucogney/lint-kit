@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.detector.api.Issue
import fr.afaucogney.mobile.android.kit.lint.helper.viewCapabilitiesInterface
import org.jetbrains.uast.UClass

class WrongViewCapabilitiesApiINamingDetector :
    AbstractFeatureContractViewApiIsNotCompliantDetector() {

    companion object {
        private const val issueId = "ViewCapabilitiesContractApiIsNotCompliant"
        private const val className = "ViewCapabilities"
        private val compliantMethods = listOf(
            "show",
            "hide",
            "update",
            "dismiss",
        )

        val ISSUE = buildIssue(
            issueId,
            className,
            compliantMethods,
            WrongViewCapabilitiesApiINamingDetector::class.java
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override val issueId = WrongViewCapabilitiesApiINamingDetector.issueId
    override val className = WrongViewCapabilitiesApiINamingDetector.className
    override val compliantMethods = WrongViewCapabilitiesApiINamingDetector.compliantMethods
    override val issue: Issue = ISSUE

    override fun UClass.selectClasses(): UClass? {
        return this.viewCapabilitiesInterface
    }
}
