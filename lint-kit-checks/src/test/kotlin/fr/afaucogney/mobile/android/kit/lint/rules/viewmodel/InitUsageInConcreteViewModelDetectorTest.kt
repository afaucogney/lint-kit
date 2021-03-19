package fr.afaucogney.mobile.android.kit.lint.rules.viewmodel

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import fr.afaucogney.mobile.android.kit.lint.helper.appBaseViewModelStub
import org.junit.Test

class InitUsageInConcreteViewModelDetectorTest {

    @Test
    fun testSuccessNoViewModel() {
        lint()
                .allowMissingSdk()
                .files(kotlin("""
                |package foo
                |
                |interface IMyFeatureContract {
                |}""".trimMargin()))
                .issues(UseInitInConcreteViewModelDetector.ISSUE_INIT_IN_VIEWMODEL)
                .run()
                .expectClean()
    }

    @Test
    fun testSuccessViewModel() {
        lint()
                .allowMissingSdk()
                .files(appBaseViewModelStub, kotlin("""
                |package foo
                |
                |import com.edf.edfetmoi.presentation.common.viewmodel.BaseViewModel
                |
                |class IMyFeatureContractViewModel(app: Application) : BaseViewModel(app) {
                |
                |fun tutu() {}
                |
                |}""".trimMargin()))
                .issues(UseInitInConcreteViewModelDetector.ISSUE_INIT_IN_VIEWMODEL)
                .run()
                .expectClean()
    }

    @Test
    fun testFailedViewModel() {
        lint()
                .allowMissingSdk()
                .files(appBaseViewModelStub, kotlin("""
                |package foo
                |
                |import com.edf.edfetmoi.presentation.common.viewmodel.BaseViewModel
                |
                |class IMyFeatureContractViewModel(app: Application) : BaseViewModel(app) {
                |
                |init { }
                |
                |fun tutu() {}
                |
                |}""".trimMargin()))
                .issues(UseInitInConcreteViewModelDetector.ISSUE_INIT_IN_VIEWMODEL)
                .run()
                .expectErrorCount(1)
    }

    @Test
    fun testFailedViewModel2() {
        lint()
                .allowMissingSdk()
                .files(appBaseViewModelStub, kotlin("""
                |package foo
                |
                |import com.edf.edfetmoi.presentation.common.viewmodel.BaseViewModel
                |
                |class IMyFeatureContractViewModel(app: Application) : BaseViewModel(app) {
                |
                |init {
                |}
                |
                |fun tutu() {}
                |
                |}""".trimMargin()))
                .issues(UseInitInConcreteViewModelDetector.ISSUE_INIT_IN_VIEWMODEL)
                .run()
                .expectErrorCount(1)
    }


    @Test
    fun testFailedViewModel3() {
        lint()
                .allowMissingSdk()
                .files(appBaseViewModelStub, kotlin("""
                |package foo
                |
                |import com.edf.edfetmoi.presentation.common.viewmodel.BaseViewModel
                |
                |class IMyFeatureContractViewModel(app: Application) : BaseViewModel(app) {
                |
                |init{}
                |
                |fun tutu() {}
                |
                |}""".trimMargin()))
                .issues(UseInitInConcreteViewModelDetector.ISSUE_INIT_IN_VIEWMODEL)
                .run()
                .expectErrorCount(1)
    }


    @Test
    fun testFailedButNotAViewModel() {
        lint()
                .allowMissingSdk()
                .files(appBaseViewModelStub, kotlin("""
                |package foo
                |
                |import com.edf.edfetmoi.presentation.common.viewmodel.BaseViewModel
                |
                |class IMyFeatureContractVewModel(app: Application) : BaseVewModel(app) {
                |
                |init { }
                |
                |fun tutu() {}
                |
                |}""".trimMargin()))
                .issues(UseInitInConcreteViewModelDetector.ISSUE_INIT_IN_VIEWMODEL)
                .run()
                .expectClean()
    }
}