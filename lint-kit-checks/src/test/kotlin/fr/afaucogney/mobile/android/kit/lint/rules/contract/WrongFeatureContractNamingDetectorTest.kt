@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.checks.infrastructure.TestFiles.kt
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import fr.afaucogney.mobile.android.kit.lint.rules.contract.WrongFeatureContractNamingDetector.Companion.ISSUE_FEATURE_CONTRACT_NAMING
import org.junit.Test

class WrongFeatureContractNamingDetectorTest {

    @Test
    fun testSuccess() {
        lint()
            .allowMissingSdk()
            .files(
                kt(
                    """
                |package foo
                |
                |interface IMyFeatureContract {
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_FEATURE_CONTRACT_NAMING)
            .run()
            .expectClean()
    }

    @Test
    fun testIMissing() {
        lint()
            .allowMissingSdk()
            .files(
                kt(
                    """
                |package foo
                |
                |interface MyFeatureContract {
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_FEATURE_CONTRACT_NAMING)
            .run()
            .expectWarningCount(1)
    }

    @Test
    fun testContractMissing() {
        lint()
            .allowMissingSdk()
            .files(
                kt(
                    """
                |package foo
                |
                |interface IMyFeaturecontract {
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_FEATURE_CONTRACT_NAMING)
            .run()
            .expectWarningCount(1)
    }

    @Test
    fun testBothMissing() {
        lint()
            .allowMissingSdk()
            .files(
                kt(
                    """
                |package foo
                |
                |interface MyFeaturecontract {
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_FEATURE_CONTRACT_NAMING)
            .run()
            .expectWarningCount(2)
    }
}