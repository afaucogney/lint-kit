@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.detector.api.Issue
import fr.afaucogney.mobile.android.kit.lint.helper.viewTagInterface
import org.jetbrains.uast.UClass

class ViewTagApiIsNotCompliantDetector :
    AbstractFeatureContractViewApiIsNotCompliantDetector() {

    companion object {
        private const val issueId = "ViewTagContractApiIsNotCompliant"
        private const val className = "ViewTag"
        private val compliantMethods = listOf(
            "send",
        )

        val ISSUE = buildIssue(
            issueId,
            className,
            compliantMethods,
            ViewTagApiIsNotCompliantDetector::class.java
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override val issueId = ViewTagApiIsNotCompliantDetector.issueId
    override val className = ViewTagApiIsNotCompliantDetector.className
    override val compliantMethods = ViewTagApiIsNotCompliantDetector.compliantMethods
    override val issue: Issue = ISSUE

    override fun UClass.selectClasses(): UClass? {
        return this.viewTagInterface
    }
}
