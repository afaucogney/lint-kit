package fr.afaucogney.mobile.android.kit.lint.rules.viewmodel

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import fr.afaucogney.mobile.android.kit.lint.helper.appCompatActivitySupportStub
import fr.afaucogney.mobile.android.kit.lint.helper.fakeBaseViewModelStub
import fr.afaucogney.mobile.android.kit.lint.helper.myActivityViewModelStub
import fr.afaucogney.mobile.android.kit.lint.helper.myViewModelStub
import fr.afaucogney.mobile.android.kit.lint.helper.viewModelProvider
import org.junit.Assert.*
import org.junit.Test

class ViewModelNewInstanceWithoutProviderDetectorTest {

    @Test
    fun testSuccessViewModel() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                fakeBaseViewModelStub,
                viewModelProvider,
                myViewModelStub,
                appCompatActivitySupportStub,
                myActivityViewModelStub,
                TestFiles.kt(
                    """
                |package foo
                |
                |import fr.afaucogney.app.feature.presentation.MyFeatureViewModel
                |import androidx.lifecycle.ViewModelProvider
                |import android.support.v7.app.AppCompatActivity
                |
                |class MyActivity() : AppCompatActivity() {
                |
                |   fun onCreated() {
                |       val viewModel = ViewModelProvider(this).get(MyFeatureActivityViewModel::class.java)
                |   }
                |
                |}""".trimMargin()
                )
            )
            .issues(ViewModelNewInstanceWithoutProviderDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun testFailedViewModel() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                fakeBaseViewModelStub,
                viewModelProvider,
                myViewModelStub,
                appCompatActivitySupportStub,
                myActivityViewModelStub,
                TestFiles.kt(
                    """
                |package foo
                |
                |import fr.afaucogney.app.feature.presentation.MyFeatureViewModel
                |import androidx.lifecycle.ViewModelProvider
                |import android.support.v7.app.AppCompatActivity
                |
                |class MyActivity : AppCompatActivity() {
                |
                |fun onCreated() {
                |   val viewModel = ViewModelProvider(this).get(MyFeatureViewModel::class.java)
                |   val viewModel2 = MyFeatureViewModel()
                |}
                |
                |
                |}""".trimMargin()
                )
            )
            .issues(ViewModelNewInstanceWithoutProviderDetector.ISSUE)
            .run()
            .expectErrorCount(1)
    }
}
