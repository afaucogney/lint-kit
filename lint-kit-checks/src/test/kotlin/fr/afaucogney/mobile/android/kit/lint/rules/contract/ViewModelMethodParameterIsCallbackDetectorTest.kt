@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.checks.infrastructure.TestFiles.kt
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import fr.afaucogney.mobile.android.kit.lint.helper.callbackAbstractStub
import fr.afaucogney.mobile.android.kit.lint.helper.liveDataStub
import fr.afaucogney.mobile.android.kit.lint.helper.mutableLiveDataStub
import fr.afaucogney.mobile.android.kit.lint.rules.contract.ViewModelMethodParameterIsCallbackDetector.Companion.ISSUE_VIEWMODEL_METHOD_PARAMETER_IS_CALLBACK
import org.junit.Test

@Suppress("UnstableApiUsage")
class ViewModelMethodParameterIsCallbackDetectorTest {

    @Test
    fun testSingleBooleanError() {
        lint()
            .allowMissingSdk()
            .files(
                mutableLiveDataStub,
                callbackAbstractStub,
                kt(
                    """
                |package foo
                |
                |import fr.afaucogney.app.presentation.callback.SimpleCallback
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       fun observeRememberMeState2(callback: Boolean)
                |       fun observeRememberMeState3(tutu: SimpleCallback)  
                |       fun observeRememberMeState4(tutu: SimpleCallback, toto: () -> Unit)
                |       fun observeRememberMeState5(toto: () -> Unit)
                |       val titi: (lol : String) -> Unit
                |       val tito: () -> Unit
                |       val titu: () -> Boolean
                |       val tita: (lol1: String) -> Boolean
                |       val titr: (lol2: () -> Unit) -> Boolean       
                |   }
                |}
                |""".trimMargin()
                )
            )
            .issues(ISSUE_VIEWMODEL_METHOD_PARAMETER_IS_CALLBACK)
            .run()
            .expectErrorCount(10)
    }

    @Test
    fun testSuccess() {
        lint()
            .allowMissingSdk()
            .files(
                liveDataStub,
                kt(
                    """
                |package foo;
                |
                |import androidx.lifecycle.LiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       fun observeRememberMeState1()
                |       fun observeRememberMeState2(): LiveData<Any>
                |       val rememberMeState1 : LiveData<Any>
                |       val rememberMeState2 : LiveData<List<Any>>
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_VIEWMODEL_METHOD_PARAMETER_IS_CALLBACK)
            .run()
            .expectClean()
    }
}
