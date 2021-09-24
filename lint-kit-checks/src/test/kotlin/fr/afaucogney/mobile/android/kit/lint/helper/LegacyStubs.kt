package fr.afaucogney.mobile.android.kit.lint.helper

import com.android.tools.lint.checks.infrastructure.TestFiles.kt


val callbackAbstractStub = kt(
    """
        package fr.afaucogney.app.presentation.callback
    
        abstract class SimpleCallback {
            abstract fun onSucceeded()
            abstract fun onFailed(connectionUnavailable: Boolean, errorCode: String?)
            abstract fun onSessionExpired()
        }

    """
).indented()

val callbackInterfaceStub = kt(
    """
    package fr.afaucogney.app.presentation.callback

    interface ISimpleCallback {
        fun onSucceeded()
        fun onFailed(connectionUnavailable: Boolean, errorCode: String?)
        fun onSessionExpired()
    }

    """
).indented()
