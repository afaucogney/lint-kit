@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import fr.afaucogney.mobile.android.kit.lint.helper.liveDataStub
import org.junit.Test

class ViewTagApiIsNotCompliantDetectorTest {

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
                |   interface ViewTag {
                |       fun sendTag()
                |       fun send()
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ViewTagApiIsNotCompliantDetector.ISSUE)
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
                |   interface ViewTag {
                |       fun leave()
                |       val tag : String
                |       fun openScreen()
                |       fun showScreen()
                |       fun navToScreen()                              
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ViewTagApiIsNotCompliantDetector.ISSUE)
            .run()
            .expectErrorCount(5)
    }
}