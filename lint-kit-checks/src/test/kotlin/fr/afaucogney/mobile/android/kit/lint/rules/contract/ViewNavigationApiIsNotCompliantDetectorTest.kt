@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import fr.afaucogney.mobile.android.kit.lint.helper.liveDataStub
import org.junit.Test

class ViewNavigationApiIsNotCompliantDetectorTest {

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
                |   interface ViewNavigation {
                |       fun quit()
                |       fun quit(activity : Activity)
                |       fun goToScreen1()
                |       fun goToScreen2()
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ViewNavigationApiIsNotCompliantDetector.ISSUE)
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
                |   interface ViewNavigation {
                |       fun leave()
                |       fun goT()
                |       fun openScreen()
                |       fun showScreen()
                |       fun navToScreen()                              
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ViewNavigationApiIsNotCompliantDetector.ISSUE)
            .run()
            .expectErrorCount(5)
    }
}
