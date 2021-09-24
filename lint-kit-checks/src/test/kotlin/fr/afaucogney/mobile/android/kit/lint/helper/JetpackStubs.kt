package fr.afaucogney.mobile.android.kit.lint.helper

import com.android.tools.lint.checks.infrastructure.TestFiles.kt

val mutableLiveDataStub = kt(
    """
        package androidx.lifecycle

        class MutableLiveData<T>

    """
).indented()

val liveDataStub = kt(
    """
        package androidx.lifecycle

        class LiveData<T>

    """
).indented()

val appBaseViewModelStub = kt(
    """
        package fr.afaucogney.app.presentation.common.viewmodel
        
        import androidx.lifecycle.AndroidViewModel
        
        abstract class BaseViewModel(application: Application) :
        AndroidViewModel(application) {

        }

    """
).indented()

val fakeBaseViewModelStub = kt(
    """
        package fr.afaucogney.app.presentation.common.viewmodel
        
        abstract class BaseViewModel(application: Application) 

    """
).indented()