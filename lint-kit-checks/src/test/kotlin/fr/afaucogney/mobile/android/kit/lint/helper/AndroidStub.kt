package fr.afaucogney.mobile.android.kit.lint.helper

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin

val activitySupportStub = kotlin("""
        package android.support.v7.app
        class AppCompatActivity
    """).indented()

 val fragmentSupportStub = kotlin("""
        package android.support.v4.app
        class Fragment
    """).indented()