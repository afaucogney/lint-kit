package fr.afaucogney.mobile.android.kit.lint.helper

import com.android.tools.lint.checks.infrastructure.TestFiles.kt

val activitySupportStub = kt(
    """
        package android.support.v7.app

        class AppCompatActivity

    """
).indented()

val fragmentSupportStub = kt(
    """
        package android.support.v4.app

        class Fragment

    """
).indented()
