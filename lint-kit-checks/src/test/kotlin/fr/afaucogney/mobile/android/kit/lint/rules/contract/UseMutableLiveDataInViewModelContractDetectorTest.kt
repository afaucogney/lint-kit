@file:Suppress("UnstableApiUsage")

package fr.afaucogney.mobile.android.kit.lint.rules.contract

import com.android.tools.lint.checks.infrastructure.TestFiles.kt
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import fr.afaucogney.mobile.android.kit.lint.helper.liveDataStub
import fr.afaucogney.mobile.android.kit.lint.helper.mutableLiveDataStub
import fr.afaucogney.mobile.android.kit.lint.rules.contract.UseMutableLiveDataInViewModelContractDetector.Companion.ISSUE_MUTABLELIVEDATA_IN_FEATURE_CONTRACT
import org.junit.Test

class UseMutableLiveDataInViewModelContractDetectorTest {

    @Test
    fun testSingleError() {
        lint()
            .allowMissingSdk()
            .files(
                mutableLiveDataStub, kt(
                    """
                |package foo
                |
                |import androidx.lifecycle.MutableLiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       fun observeRememberMeState(): MutableLiveData<Any>
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_MUTABLELIVEDATA_IN_FEATURE_CONTRACT)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testSingleVarError() {
        lint()
            .allowMissingSdk()
            .files(
                mutableLiveDataStub, kt(
                    """
                |package foo
                |
                |import androidx.lifecycle.MutableLiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       fun observeRememberMeState(): MutableLiveData<Any>
                |       var tutu : MutableLiveData<Any>
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_MUTABLELIVEDATA_IN_FEATURE_CONTRACT)
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
                |       fun observeRememberMeState1(): MutableLiveData<Any>
                |       fun observeRememberMeState2(): MutableLiveData<Any>
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_MUTABLELIVEDATA_IN_FEATURE_CONTRACT)
            .run()
            .expectErrorCount(2)
    }


    @Test
    fun testHybrid() {
        lint()
            .allowMissingSdk()
            .files(
                liveDataStub, mutableLiveDataStub, kt(
                    """
                |package foo
                |
                |import androidx.lifecycle.LiveData
                |import androidx.lifecycle.MutableLiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       fun observeRememberMeState1(): LiveData<Any>
                |       fun observeRememberMeState2(): MutableLiveData<Any>
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_MUTABLELIVEDATA_IN_FEATURE_CONTRACT)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testSuccess() {
        lint()
            .allowMissingSdk()
            .files(
                liveDataStub, kt(
                    """
                |package foo;
                |
                |import androidx.lifecycle.LiveData
                |
                |interface IMyFeatureContract {
                |   interface ViewModel {
                |       fun observeRememberMeState1(): LiveData<Any>
                |       fun observeRememberMeState2(): LiveData<Any>
                |   }
                |}""".trimMargin()
                )
            )
            .issues(ISSUE_MUTABLELIVEDATA_IN_FEATURE_CONTRACT)
            .run()
            .expectClean()
    }
}