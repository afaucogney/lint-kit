package fr.afaucogney.mobile.android.kit.lint.rules.common

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import fr.afaucogney.mobile.android.kit.lint.helper.isUpperCase
import org.jetbrains.uast.UFile
import java.util.EnumSet

class WellSeparatorDetector : Detector(), Detector.UastScanner {

    companion object {
        private const val SBC_LINE =
            "///////////////////////////////////////////////////////////////////////////"

        private val SBC_AUTHORIZED = listOf(
            "// CONST",
            "// CONFIGURATION",
            "// DATA",
            "// DEPENDENCY",
            "// ERROR CASE",
            "// EXCEPTION",
            "// FACTORY",
            "// FUNCTIONAL CASE",
            "// HELPER",
            "// INTERFACE",
            "// LIFECYCLE",
            "// MOCKED DEPENDENCY",
            "// PUBLIC API",
            "// SPECIALIZATION",
            "// SPIED DEPENDENCY",
            "// STATE",
            "// TECHNICAL CASE",
            "// UI SETUP",
            "// UNIT UNDER TEST",
            "// TAG",
            "// VIEW",
        )

        val ISSUE = Issue.create(
            "NotAuthorizedSbcNaming",
            "SBC name must be contain in $SBC_AUTHORIZED.",
            "Because there are too many SBCs that contain the same thing with different names.",
            Category.CORRECTNESS,
            4,
            Severity.ERROR,
            Implementation(
                WellSeparatorDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES)
            )
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override fun getApplicableUastTypes() = listOf(UFile::class.java)

    override fun createUastHandler(context: JavaContext) = RuleHandler(context)

    ///////////////////////////////////////////////////////////////////////////
    // HELPER
    ///////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("TooGenericExceptionCaught", "SwallowedException")
    class RuleHandler(private val context: JavaContext) : UElementHandler() {
        override fun visitFile(node: UFile) {
            node.allCommentsInFile.forEachIndexed { index, comment ->
                try {
                    val currentLine = comment.text
                    val endLine = node.allCommentsInFile[index + 2].text
                    if (currentLine == SBC_LINE && endLine == SBC_LINE) {
                        val nextLine = node.allCommentsInFile[index + 1].text
                        if (nextLine.isUpperCase() && !SBC_AUTHORIZED.contains(nextLine)) {
                            context.report(
                                ISSUE,
                                node,
                                context.getNameLocation(node),
                                StringBuilder()
                                    .append("SBC name not respected")
                                    .appendLine()
                                    .appendLine(currentLine)
                                    .appendLine(nextLine)
                                    .appendLine(endLine)
                                    .toString()
                            )
                        }
                    }
                } catch (e: IndexOutOfBoundsException) {
                    // Do nothing
                }
            }
        }
    }
}
