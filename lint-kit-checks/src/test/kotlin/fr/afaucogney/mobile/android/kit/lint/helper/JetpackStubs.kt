package fr.afaucogney.mobile.android.kit.lint.helper

import com.android.tools.lint.checks.infrastructure.TestFiles.java
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

val viewModelProvider = java(
    """
        package androidx.lifecycle;

        public class ViewModelProvider {
            public interface Factory {
                <T extends ViewModel> T create(@NonNull Class<T> modelClass);
            }
            public ViewModelProvider(@NonNull ViewModelStoreOwner owner) {
            }

            public ViewModelProvider(@NonNull ViewModelStoreOwner owner, @NonNull Factory factory) {
            }

            public ViewModelProvider(@NonNull ViewModelStore store, @NonNull Factory factory) {
            }

            @NonNull
            @MainThread
            public <T extends ViewModel> T get(@NonNull Class<T> modelClass) {
            }

            @SuppressWarnings("unchecked")
            @NonNull
            @MainThread
            public <T extends ViewModel> T get(@NonNull String key, @NonNull Class<T> modelClass) {
            }
        }

    """
).indented()
