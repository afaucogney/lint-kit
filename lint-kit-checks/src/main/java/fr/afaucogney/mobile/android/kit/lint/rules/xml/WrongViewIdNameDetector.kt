@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.xml

import com.android.SdkConstants
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Attr

class WrongViewIdNameDetector : LayoutDetector() {

    ///////////////////////////////////////////////////////////////////////////
    // CONST
    ///////////////////////////////////////////////////////////////////////////

    companion object {

        private const val ISSUE_ID = "WrongViewIdName"
        private const val ISSUE_DESCRIPTION = "The id name must follow the project convention"
        private const val ISSUE_EXPLANATION =
            "The id name must start with View name's uppercase letters in lower case + _ " +
                    "and then the functional identification."

        val ISSUE = Issue.create(
            id = ISSUE_ID,
            briefDescription = ISSUE_DESCRIPTION,
            explanation = ISSUE_EXPLANATION,
            category = Category.COMPLIANCE,
            priority = 6,
            severity = Severity.ERROR,
            androidSpecific = true,
            implementation = Implementation(
                WrongViewIdNameDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // CONFIGURATION
    ///////////////////////////////////////////////////////////////////////////

    override fun getApplicableAttributes(): Collection<String>? {
        return listOf(
            SdkConstants.ATTR_ID
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val id = attribute.value.replaceFirst("@+id/", "")
        val tagName = attribute.ownerElement.localName.split(".").last()
        val tagArchronyme = tagName.filter { it.isUpperCase() }.lowercase()
        if (!id.startsWith(tagArchronyme + "_")) {
            context.report(
                ISSUE,
                attribute,
                context.getLocation(attribute),
                ISSUE_DESCRIPTION
            )
        }
    }
}
