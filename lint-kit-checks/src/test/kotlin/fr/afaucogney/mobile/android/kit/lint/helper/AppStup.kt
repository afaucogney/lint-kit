package fr.afaucogney.mobile.android.kit.lint.helper

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin

val appBaseViewModelStub = kotlin("""
        package com.edf.edfetmoi.presentation.common.viewmodel
        
        import androidx.lifecycle.AndroidViewModel
        
        abstract class BaseViewModel(application: Application) :
        AndroidViewModel(application) {

        }
    """).indented()

val autodisposeStub = kotlin("""
        package com.uber.autodispose.kotlin
    """).indented()