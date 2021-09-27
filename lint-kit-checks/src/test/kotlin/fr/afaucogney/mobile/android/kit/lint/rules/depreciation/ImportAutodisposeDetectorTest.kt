package fr.afaucogney.mobile.android.kit.lint.rules.depreciation

import com.android.tools.lint.checks.infrastructure.TestFiles.kt
import com.android.tools.lint.checks.infrastructure.TestLintTask
import fr.afaucogney.mobile.android.kit.lint.helper.autodisposeStub
import fr.afaucogney.mobile.android.kit.lint.helper.fakeBaseViewModelStub
import org.junit.Test

class ImportAutodisposeDetectorTest {

    @Test
    fun testSuccessViewModel() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                autodisposeStub,
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
            .issues(AutodisposeStillImportedDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun testFailedViewModel() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                autodisposeStub,
                fakeBaseViewModelStub,
                kt(
                    """
                |package foo
                |
                |import fr.afaucogney.app.presentation.common.viewmodel.BaseViewModel
                |import com.uber.autodispose.kotlin.autoDisposable
                |
                |class IMyFeatureContractViewModel(app: Application) : BaseViewModel(app) {
                |
                |fun tutu() {}
                |
                |}""".trimMargin()
                )
            )
            .issues(AutodisposeStillImportedDetector.ISSUE)
            .allowCompilationErrors()
            .run()
            .expectErrorCount(1)
    }
}
