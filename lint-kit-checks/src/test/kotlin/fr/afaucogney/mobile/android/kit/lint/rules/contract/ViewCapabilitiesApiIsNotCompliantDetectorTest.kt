@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import fr.afaucogney.mobile.android.kit.lint.helper.liveDataStub
import org.junit.Test

class ViewCapabilitiesApiIsNotCompliantDetectorTest {

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
                |   interface ViewCapabilities {
                |       fun show()
                |       fun hide(activity : Activity)
                |       fun update()
                |       fun dismiss()
                |   }
                |}""".trimMargin()
                )
            )
            .issues(WrongViewCapabilitiesApiINamingDetector.ISSUE)
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
                |   interface ViewCapabilities {
                |       fun leave()
                |       fun goT()
                |       fun refresh()
                |       val update : String
                |       fun navToScreen()                              
                |   }
                |}""".trimMargin()
                )
            )
            .issues(WrongViewCapabilitiesApiINamingDetector.ISSUE)
            .run()
            .expectErrorCount(5)
    }
}
