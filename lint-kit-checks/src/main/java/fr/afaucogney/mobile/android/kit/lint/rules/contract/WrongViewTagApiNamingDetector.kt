@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.detector.api.Issue
import fr.afaucogney.mobile.android.kit.lint.helper.viewTagInterface
import org.jetbrains.uast.UClass

class WrongViewTagApiNamingDetector :
    AbstractFeatureContractViewApiIsNotCompliantDetector() {

    companion object {
        private const val issueId = "ViewTagContractApiIsNotCompliant"
        private const val className = "ViewTag"
        private val compliantMethodPrefixes = listOf(
            "send",
        )

        val ISSUE = buildIssue(
            issueId,
            className,
            compliantMethodPrefixes,
            WrongViewTagApiNamingDetector::class.java
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override val issueId = WrongViewTagApiNamingDetector.issueId
    override val className = WrongViewTagApiNamingDetector.className
    override val compliantMethods = WrongViewTagApiNamingDetector.compliantMethodPrefixes
    override val issue: Issue = ISSUE

    override fun UClass.selectClasses(): UClass? {
        return this.viewTagInterface
    }
}
