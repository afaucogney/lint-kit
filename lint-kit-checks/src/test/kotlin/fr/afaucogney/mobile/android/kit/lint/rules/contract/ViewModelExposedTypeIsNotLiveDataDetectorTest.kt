@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.checks.infrastructure.TestFiles.kt
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import fr.afaucogney.mobile.android.kit.lint.helper.liveDataStub
import fr.afaucogney.mobile.android.kit.lint.helper.mutableLiveDataStub
import fr.afaucogney.mobile.android.kit.lint.rules.contract.ViewModelExposedTypeIsNotLiveDataDetector.Companion.ISSUE_VIEWMODEL_EXPOSED_TYPE_ARE_NOT_LIVEDATA
import org.junit.Test

@Suppress("UnstableApiUsage")
class ViewModelExposedTypeIsNotLiveDataDetectorTest {

    @Test
    fun testSingleBooleanError() {
        lint()
            .allowMissingSdk()
            .files(
                mutableLiveDataStub,
                kt(
                    """
                |package foo
                |
                |import androidx.lifecycle.MutableLiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       val rememberMeState : Boolean
                |   }
                |}
                |""".trimMargin()
                )
            )
            .issues(ISSUE_VIEWMODEL_EXPOSED_TYPE_ARE_NOT_LIVEDATA)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testSingleListError() {
        lint()
            .allowMissingSdk()
            .files(
                mutableLiveDataStub,
                kt(
                    """
                |package foo
                |
                |import androidx.lifecycle.MutableLiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       val rememberMeState : List<Any>
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_VIEWMODEL_EXPOSED_TYPE_ARE_NOT_LIVEDATA)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testSingleListLiveDataError() {
        lint()
            .allowMissingSdk()
            .files(
                mutableLiveDataStub,
                liveDataStub,
                kt(
                    """
                |package foo
                |
                |import androidx.lifecycle.MutableLiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       val rememberMeState : List<LiveData<Any>>
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_VIEWMODEL_EXPOSED_TYPE_ARE_NOT_LIVEDATA)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testSingleMutableLiveDataError() {
        lint()
            .allowMissingSdk()
            .files(
                mutableLiveDataStub,
                kt(
                    """
                |package foo
                |
                |import androidx.lifecycle.MutableLiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       val rememberMeState : MutableLiveData<Any>
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_VIEWMODEL_EXPOSED_TYPE_ARE_NOT_LIVEDATA)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testSingleMyLiveDataError() {
        lint()
            .allowMissingSdk()
            .files(
                mutableLiveDataStub,
                kt(
                    """
                |package foo
                |
                |import androidx.lifecycle.MutableLiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       val rememberMeState : MyLiveData
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_VIEWMODEL_EXPOSED_TYPE_ARE_NOT_LIVEDATA)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testSingleVarError() {
        lint()
            .allowMissingSdk()
            .files(
                mutableLiveDataStub,
                kt(
                    """
                |package foo
                |
                |import androidx.lifecycle.MutableLiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       val rememberMeState : MutableLiveData<Any>
                |       var tutu : List<Any>
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_VIEWMODEL_EXPOSED_TYPE_ARE_NOT_LIVEDATA)
            .run()
            .expectErrorCount(2)
    }

    @Test
    fun testDualError() {
        lint()
            .allowMissingSdk()
            .files(
                mutableLiveDataStub,
                kt(
                    """
                |package foo
                |
                |import androidx.lifecycle.MutableLiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       val rememberMeState1 : List<Any>
                |       val rememberMeState2 : Any
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_VIEWMODEL_EXPOSED_TYPE_ARE_NOT_LIVEDATA)
            .run()
            .expectErrorCount(2)
    }


    @Test
    fun testHybrid() {
        lint()
            .allowMissingSdk()
            .files(
                liveDataStub,
                mutableLiveDataStub,
                kt(
                    """
                |package foo
                |
                |import androidx.lifecycle.LiveData
                |import androidx.lifecycle.MutableLiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       val rememberMeState1 : MutableLiveData<Any>
                |       val rememberMeState1 : LiveData<Any>
                |       val rememberMeState2 : List<Any>
                |       val rememberMeState3 : LiveData<Any?>
                |       val rememberMeState4 : LiveData<Any?>?
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_VIEWMODEL_EXPOSED_TYPE_ARE_NOT_LIVEDATA)
            .run()
            .expectErrorCount(4)
    }

    // Test nullable
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
                |       val rememberMeState2 : LiveData<Any>
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_VIEWMODEL_EXPOSED_TYPE_ARE_NOT_LIVEDATA)
            .run()
            .expectClean()
    }
}
