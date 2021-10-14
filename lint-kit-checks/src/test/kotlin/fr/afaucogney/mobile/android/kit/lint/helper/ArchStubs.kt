package fr.afaucogney.mobile.android.kit.lint.helper

import com.android.tools.lint.checks.infrastructure.TestFiles

val myViewModelStub = TestFiles.kt(
    """
        package fr.afaucogney.app.feature.presentation

        import fr.afaucogney.app.presentation.common.viewmodel.BaseViewModel

        class MyFeatureViewModel(app: Application) : BaseViewModel(app) {
        
        }

    """
).indented()

val myActivityViewModelStub = TestFiles.kt(
    """
        package fr.afaucogney.app.feature.presentation

        import fr.afaucogney.app.presentation.common.viewmodel.BaseViewModel

        class MyFeatureActivityViewModel(app: Application) : BaseViewModel(app) {
        
        }

    """
).indented()

val activityExt = TestFiles.kt(
    """
        package fr.niji.mobile.android.socle.arch.base.activity

        import androidx.lifecycle.ViewModel
        import androidx.lifecycle.ViewModelProvider
        
        fun <T : ViewModel> Activity.getViewModel(viewModelClass: Class<T>): T =
            ViewModelProvider(this).get(viewModelClass)
        
        inline fun <reified T : ViewModel> Activity.getViewModel(): T =
            this.getViewModel(T::class.java)
    """
).indented()

val fragmentExtStub = TestFiles.kt(
    """
        package fr.niji.mobile.android.socle.arch.base.fragment
        
        import androidx.fragment.app.Fragment
        import androidx.lifecycle.ViewModel
        import androidx.lifecycle.ViewModelProvider
        
        fun <T : ViewModel> Fragment.getViewModel(viewModelClass: Class<T>): T =
            ViewModelProvider(this).get(viewModelClass)
        
        inline fun <reified T : ViewModel> Fragment.getViewModel(): T =
            this.getViewModel(T::class.java)
        
        fun <T : ViewModel> Fragment.getActivityViewModel(viewModelClass: Class<T>): T =
            ViewModelProvider(this.requireActivity()).get(viewModelClass)
        
        inline fun <reified T : ViewModel> Fragment.getActivityViewModel(): T =
            this.getActivityViewModel(T::class.java)
    """
).indented()