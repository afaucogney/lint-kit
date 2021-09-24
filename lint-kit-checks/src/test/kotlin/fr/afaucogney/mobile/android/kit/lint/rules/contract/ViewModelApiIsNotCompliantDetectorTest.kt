@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import fr.afaucogney.mobile.android.kit.lint.helper.liveDataStub
import org.junit.Test

class ViewModelApiIsNotCompliantDetectorTest {

    @Test
    fun testSuccess() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                liveDataStub,
                TestFiles.kt(
                    """
                |package foo;
                |
                |import androidx.lifecycle.LiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       fun requestQuit()
                |       fun observeQuit(activity : Activity)
                |       val tutuState : LiveData<Any>
                |       fun startProut2()
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ViewModelApiIsNotCompliantDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun testFailed() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                liveDataStub,
                TestFiles.kt(
                    """
                |package foo;
                |
                |import androidx.lifecycle.LiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       fun leave()
                |       fun openScreen()
                |       fun showScreen()
                |       fun navToScreen()                              
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ViewModelApiIsNotCompliantDetector.ISSUE)
            .run()
            .expectErrorCount(4)
    }
}
