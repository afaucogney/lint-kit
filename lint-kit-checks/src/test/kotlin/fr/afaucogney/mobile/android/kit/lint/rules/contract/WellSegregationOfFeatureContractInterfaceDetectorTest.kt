@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.checks.infrastructure.TestFiles.kt
import com.android.tools.lint.checks.infrastructure.TestLintTask
import fr.afaucogney.mobile.android.kit.lint.rules.contract.WellSegregationOfFeatureContractInterfaceDetector.Companion.ISSUE_FEATURE_CONTRACT_SEGREGATION
import org.junit.Test

class WellSegregationOfFeatureContractInterfaceDetectorTest {

    @Test
    fun testSuccess() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                kt(
                    """
                |package foo
                |
                |interface IMyFeatureContract {
                |   interface ViewCapabilities {
                |       fun bar(): Any
                |   }
                |   interface ViewModel {
                |       fun bar(): Any
                |   }
                |   interface ViewNavigation {
                |       fun bar(): Any
                |   }
                |   interface ViewTag {
                |       fun bar(): Any
                |   }
                |   interface ViewEvent {
                |       fun bar(): Any
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_FEATURE_CONTRACT_SEGREGATION)
            .run()
            .expectClean()
    }

    @Test
    fun testSuccessOther() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                kt(
                    """
                |package foo
                |
                |interface IMyFeatureContract {
                |   interface ViewCapabilities {
                |       fun bar(): Any
                |   }
                |   interface ViewModel {
                |       fun bar(): Any
                |   }
                |   interface ViewNavigation {
                |       fun bar(): Any
                |   }
                |   interface ViewTag {
                |       fun bar(): Any
                |   }
                |   interface ViewEvent {
                |       fun bar(): Any
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_FEATURE_CONTRACT_SEGREGATION)
            .run()
            .expectClean()
    }

    @Test
    fun testViewCapabilitiesMissing() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                kt(
                    """
                |package foo
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       fun bar(): Any
                |   }                
                |   interface ViewNavigation {
                |       fun bar(): Any
                |   }
                |   interface ViewTag {
                |       fun bar(): Any
                |   }
                |   interface ViewEvent {
                |       fun bar(): Any
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_FEATURE_CONTRACT_SEGREGATION)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testViewModelMissing() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                kt(
                    """
                |package foo
                |
                |interface IMyFeatureContract {
                |   interface ViewCapabilities {
                |       fun bar(): Any
                |   } 
                |   interface ViewNavigation {
                |       fun bar(): Any
                |   }
                |   interface ViewTag {
                |       fun bar(): Any
                |   }
                |   interface ViewEvent {
                |       fun bar(): Any
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_FEATURE_CONTRACT_SEGREGATION)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testViewTagMissing() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                kt(
                    """
                |package foo
                |
                |interface IMyFeatureContract {
                |   interface ViewCapabilities {
                |       fun bar(): Any
                |   } 
                |   interface ViewModel {
                |       fun bar(): Any
                |   }
                |   interface ViewNavigation {
                |       fun bar(): Any
                |   }
                |   interface ViewEvent {
                |       fun bar(): Any
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_FEATURE_CONTRACT_SEGREGATION)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testViewEventMissing() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                kt(
                    """
                |package foo
                |
                |interface IMyFeatureContract {
                |   interface ViewCapabilities {
                |       fun bar(): Any
                |   } 
                |   interface ViewModel {
                |       fun bar(): Any
                |   }
                |   interface ViewNavigation {
                |       fun bar(): Any
                |   }
                |   interface ViewTag {
                |       fun bar(): Any
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_FEATURE_CONTRACT_SEGREGATION)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testViewNavigationMissing() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                kt(
                    """
                |package foo
                |
                |interface IMyFeatureContract {
                |   interface ViewCapabilities {
                |       fun bar(): Any
                |   } 
                |   interface ViewModel {
                |       fun bar(): Any
                |   }
                |   interface ViewTag {
                |       fun bar(): Any
                |   }
                |   interface ViewEvent {
                |       fun bar(): Any
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_FEATURE_CONTRACT_SEGREGATION)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun test2Missing() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                kt(
                    """
                |package foo
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       fun bar(): Any
                |   }
                |   interface ViewTag {
                |       fun bar(): Any
                |   }
                |   interface ViewEvent {
                |       fun bar(): Any
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_FEATURE_CONTRACT_SEGREGATION)
            .run()
            .expectErrorCount(2)
    }

    @Test
    fun test3Missing() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                kt(
                    """
                |package foo
                |
                |interface IMyFeatureContract {
                |   interface ViewTag {
                |       fun bar(): Any
                |   }
                |   interface ViewEvent {
                |       fun bar(): Any
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_FEATURE_CONTRACT_SEGREGATION)
            .run()
            .expectErrorCount(3)
    }

    @Test
    fun test4Missing() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                kt(
                    """
                |package foo
                |
                |interface IMyFeatureContract {
                |   interface ViewTag {
                |       fun bar(): Any
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_FEATURE_CONTRACT_SEGREGATION)
            .run()
            .expectErrorCount(4)
    }

    @Test
    fun testAllMissing() {
        TestLintTask.lint()
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
            .issues(ISSUE_FEATURE_CONTRACT_SEGREGATION)
            .run()
            .expectErrorCount(5)
    }
}