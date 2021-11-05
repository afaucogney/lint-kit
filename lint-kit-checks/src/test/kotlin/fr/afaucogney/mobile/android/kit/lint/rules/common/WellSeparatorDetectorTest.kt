package fr.afaucogney.mobile.android.kit.lint.rules.common

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import org.junit.Test

class WellSeparatorDetectorTest {

    @Test
    fun testSuccessSbc() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                LintDetectorTest.kotlin(
                    """
                |package foo
                |
                |class Toto {
                |
                |///////////////////////////////////////////////////////////////////////////
                |// PUBLIC API
                |///////////////////////////////////////////////////////////////////////////
                |
                |fun tutu() {}
                |
                |}""".trimMargin()
                )
            )
            .issues(WellSeparatorDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun testSuccessMultipleSbc() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                LintDetectorTest.kotlin(
                    """
                |package foo
                |
                |class Toto {
                |
                |///////////////////////////////////////////////////////////////////////////
                |// PUBLIC API
                |///////////////////////////////////////////////////////////////////////////
                |
                |fun tutu() {}
                |
                |///////////////////////////////////////////////////////////////////////////
                |// HELPER
                |///////////////////////////////////////////////////////////////////////////
                |
                |private fun tata() {}
                |
                |}""".trimMargin()
                )
            )
            .issues(WellSeparatorDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun testFailSbc() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                LintDetectorTest.kotlin(
                    """
                |package foo
                |
                |class Toto {
                |
                |///////////////////////////////////////////////////////////////////////////
                |// ALL METHODS
                |///////////////////////////////////////////////////////////////////////////
                |
                |fun tutu() {}
                |
                |}""".trimMargin()
                )
            )
            .issues(WellSeparatorDetector.ISSUE)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testFailSbcWithPlural() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                LintDetectorTest.kotlin(
                    """
                |package foo
                |
                |class Toto {
                |
                |///////////////////////////////////////////////////////////////////////////
                |// PUBLIC APIS
                |///////////////////////////////////////////////////////////////////////////
                |
                |fun tutu() {}
                |
                |}""".trimMargin()
                )
            )
            .issues(WellSeparatorDetector.ISSUE)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testComment() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                LintDetectorTest.kotlin(
                    """
                |package foo
                |
                |class Toto {
                |
                |// TODO: foo
                |
                |fun tutu() {}
                |
                |}""".trimMargin()
                )
            )
            .issues(WellSeparatorDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun testCommentWithSbc() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                LintDetectorTest.kotlin(
                    """
                |package foo
                |
                |class Toto {
                |
                |///////////////////////////////////////////////////////////////////////////
                |// PUBLIC API
                |///////////////////////////////////////////////////////////////////////////`
                |
                |// TODO: foo
                |fun tutu() {}
                |
                |///////////////////////////////////////////////////////////////////////////
                |// HELPER
                |///////////////////////////////////////////////////////////////////////////`
                |
                |}""".trimMargin()
                )
            )
            .issues(WellSeparatorDetector.ISSUE)
            .run()
            .expectClean()
    }
}
