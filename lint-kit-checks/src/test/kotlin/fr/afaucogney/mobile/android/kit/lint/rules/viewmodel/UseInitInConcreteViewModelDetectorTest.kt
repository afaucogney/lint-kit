@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.viewmodel

import com.android.tools.lint.checks.infrastructure.TestFiles.kt
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import fr.afaucogney.mobile.android.kit.lint.helper.fakeBaseViewModelStub
import fr.afaucogney.mobile.android.kit.lint.rules.viewmodel.UseInitInConcreteViewModelDetector.Companion.ISSUE_INIT_IN_VIEWMODEL
import org.junit.Test

class UseInitInConcreteViewModelDetectorTest {

    @Test
    fun testSuccessNoViewModel() {
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
            .issues(ISSUE_INIT_IN_VIEWMODEL)
            .run()
            .expectClean()
    }

    @Test
    fun testSuccessViewModel() {
        lint()
            .allowMissingSdk()
            .files(
                fakeBaseViewModelStub,
                kt(
                    """
                |package foo
                |
                |import fr.afaucogney.app.presentation.common.viewmodel.BaseViewModel
                |
                |class IMyFeatureContractViewModel(app: Application) : BaseViewModel(app) {
                |
                |fun tutu() {}
                |
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_INIT_IN_VIEWMODEL)
            .run()
            .expectClean()
    }

    @Test
    fun testFailedViewModel() {
        lint()
            .allowMissingSdk()
            .files(
                fakeBaseViewModelStub,
                kt(
                    """
                |package foo
                |
                |import fr.afaucogney.app.presentation.common.viewmodel.BaseViewModel
                |
                |class IMyFeatureContractViewModel(app: Application) : BaseViewModel(app) {
                |
                |init { }
                |
                |fun tutu() {}
                |
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_INIT_IN_VIEWMODEL)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testFailedViewModel2() {
        lint()
            .allowMissingSdk()
            .files(
                fakeBaseViewModelStub, kt(
                    """
                |package foo
                |
                |import fr.afaucogney.app.presentation.common.viewmodel.BaseViewModel
                |
                |class IMyFeatureContractViewModel(app: Application) : BaseViewModel(app) {
                |
                |init {
                |}
                |
                |fun tutu() {}
                |
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_INIT_IN_VIEWMODEL)
            .run()
            .expectErrorCount(1)
    }


    @Test
    fun testFailedViewModel3() {
        lint()
            .allowMissingSdk()
            .files(
                fakeBaseViewModelStub, kt(
                    """
                |package foo
                |
                |import fr.afaucogney.app.presentation.common.viewmodel.BaseViewModel
                |
                |class IMyFeatureContractViewModel(app: Application) : BaseViewModel(app) {
                |
                |init{}
                |
                |fun tutu() {}
                |
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_INIT_IN_VIEWMODEL)
            .run()
            .expectErrorCount(1)
    }


    @Test
    fun testFailedButNotAViewModel() {
        lint()
            .allowMissingSdk()
            .files(
                fakeBaseViewModelStub, kt(
                    """
                |package foo
                |
                |import fr.afaucogney.app.presentation.common.viewmodel.BaseViewModel
                |
                |class IMyFeatureContractVewModel(app: Application) : BaseVewModel(app) {
                |
                |init { }
                |
                |fun tutu() {}
                |
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_INIT_IN_VIEWMODEL)
            .run()
            .expectClean()
    }
}
