package fr.afaucogney.mobile.android.kit.lint.rules.viewmodel

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import fr.afaucogney.mobile.android.kit.lint.helper.appCompatActivitySupportStub
import fr.afaucogney.mobile.android.kit.lint.helper.fakeBaseViewModelStub
import fr.afaucogney.mobile.android.kit.lint.helper.fragmentAndroidXStub
import fr.afaucogney.mobile.android.kit.lint.helper.fragmentExtStub
import fr.afaucogney.mobile.android.kit.lint.helper.myActivityViewModelStub
import fr.afaucogney.mobile.android.kit.lint.helper.myViewModelStub
import fr.afaucogney.mobile.android.kit.lint.helper.viewModelProvider
import org.junit.Test

class WrongActivityViewModelNamingDetectorTest {

    @Test
    fun testSuccessActivityViewModel() {
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
            .issues(WrongActivityViewModelNamingDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun testSuccessFragmentViewModel() {
        TestLintTask.lint()
            .allowMissingSdk()
            .allowCompilationErrors()
            .files(
                fakeBaseViewModelStub,
                viewModelProvider,
                myViewModelStub,
                fragmentExtStub,
                fragmentAndroidXStub,
                TestFiles.kt(
                    """
                |package foo
                |
                |import fr.afaucogney.app.feature.presentation.MyFeatureViewModel
                |import androidx.lifecycle.ViewModelProvider
                |import fr.niji.mobile.android.socle.arch.base.fragment.getViewModel
                |import androidx.fragment.app.Fragment
                |
                |class MyFragment() : Fragment() {
                |
                |   fun onCreated() {
                |       val viewModel1 = ViewModelProvider(this).get(MyFeatureViewModel::class.java)
                |       val viewModel2 = ViewModelProvider(this).get(MyFeatureFragmentViewModel::class.java)     
                |       val viewModel3 = this.getViewModel(MyFeatureFragmentViewModel::class.java)
                |       val viewModel4 = this.getViewModel<MyFeatureFragmentViewModel>()
                |       val viewModel5 = this.getActivityViewModel<MyFeatureActivityViewModel>()                                                           
                |       val viewModel6 = ViewModelProvider(this.activity).get(MyFeatureActivityViewModel::class.java)
                |   }
                |
                |}""".trimMargin()
                )
            )
            .issues(WrongActivityViewModelNamingDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun testFailedFragmentViewModel() {
        TestLintTask.lint()
            .allowMissingSdk()
            .allowCompilationErrors()
            .files(
                fakeBaseViewModelStub,
                viewModelProvider,
                myViewModelStub,
                fragmentExtStub,
                fragmentAndroidXStub,
                TestFiles.kt(
                    """
                |package foo
                |
                |import fr.afaucogney.app.feature.presentation.MyFeatureViewModel
                |import androidx.lifecycle.ViewModelProvider
                |import fr.niji.mobile.android.socle.arch.base.fragment.getViewModel                
                |import androidx.fragment.app.Fragment
                |
                |class MyFragment() : Fragment() {
                |
                |   fun onCreated() {
                |       val viewModel0 = ViewModelProvider(this).get(MyFeatureActivityViewModel::class.java)
                |       val viewModel1 = ViewModelProvider(activity).get(MyFeatureViewModel::class.java)
                |       val viewModel10 = ViewModelProvider(this.activity).get(MyFeatureViewModel::class.java)              
                |       val viewModel2 = ViewModelProvider(requiredActivity).get(MyFeatureViewModel::class.java)|       
                |       val viewModel3 = this.getViewModel(MyFeatureActivityViewModel::class.java)
                |       val viewModel4 = this.getViewModel<MyFeatureActivityViewModel>()    
                |       val viewModel5 = this.getActivityViewModel<MyFeatureViewModel>()
                |       val viewModel6 = this.getActivityViewModel<MyFeatureViewModel>()                
                |       val viewModel7 = this.getActivityViewModel(MyFeatureViewModel::class.java)                                           
                |   }
                |
                |}""".trimMargin()
                )
            )
            .issues(WrongActivityViewModelNamingDetector.ISSUE)
            .run()
            .expectErrorCount(9)
    }

    @Test
    fun testFailedActivityViewModel() {
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
                |   fun onCreated() {
                |       val viewModel = ViewModelProvider(this).get(MyFeatureViewModel::class.java)
                |   }
                |
                |}""".trimMargin()
                )
            )
            .issues(WrongActivityViewModelNamingDetector.ISSUE)
            .run()
            .expectErrorCount(1)
    }
}
