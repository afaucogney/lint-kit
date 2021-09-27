@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import fr.afaucogney.mobile.android.kit.lint.helper.liveDataStub
import org.junit.Test

class ViewEventApiIsNotCompliantDetectorTest {

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
                |   interface ViewEvent {
                |       fun onClick()
                |       fun onSwipe(activity : Activity)
                |       fun onTruc()
                |   }
                |}""".trimMargin()
                )
            )
            .issues(WrongViewEventApiINamingDetector.ISSUE)
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
                |   interface ViewEvent {
                |       fun leave()
                |       fun goT()
                |       fun openScreen()
                |       val onShowScreen : String
                |       fun navToScreen()                              
                |   }
                |}""".trimMargin()
                )
            )
            .issues(WrongViewEventApiINamingDetector.ISSUE)
            .run()
            .expectErrorCount(5)
    }
}
