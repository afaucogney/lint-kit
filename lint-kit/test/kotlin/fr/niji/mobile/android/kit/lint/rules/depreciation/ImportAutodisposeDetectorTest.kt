package fr.niji.mobile.android.kit.lit.rules.depreciation

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import fr.niji.mobile.android.kit.lit.helper.autodisposeStub
import org.junit.Test

class ImportAutodisposeDetectorTest {

    @Test
    fun testSuccessViewModel() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(autodisposeStub, LintDetectorTest.kotlin("""
                |package foo
                |
                |import com.edf.edfetmoi.presentation.common.viewmodel.BaseViewModel
                |
                |class IMyFeatureContractViewModel(app: Application) : BaseViewModel(app) {
                |
                |fun tutu() {}
                |
                |}""".trimMargin()))
            .issues(ImportAutodisposeDetector.ISSUE_AUTODISPOSE_USAGE)
            .run()
            .expectClean()
    }

    @Test
    fun testFailedViewModel() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(autodisposeStub, LintDetectorTest.kotlin("""
                |package foo
                |
                |import com.edf.edfetmoi.presentation.common.viewmodel.BaseViewModel
                |import com.uber.autodispose.kotlin.autoDisposable
                |
                |class IMyFeatureContractViewModel(app: Application) : BaseViewModel(app) {
                |
                |fun tutu() {}
                |
                |}""".trimMargin()))
            .issues(ImportAutodisposeDetector.ISSUE_AUTODISPOSE_USAGE)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testCleanJavaFile() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(autodisposeStub, LintDetectorTest.java("""
                |package com.edf.edfetmoi.presentation.common.customview;
                |
                |import android.content.Context;
                |import android.util.AttributeSet;
                |import android.widget.ScrollView;
                |
                |import com.edf.edfetmoi.presentation.feature.old.newsfeed.listener.ScrollViewListener;
                |
                |/**
                | * Created by sgabel on 22/11/2016.
                | */
                |
                |public class ScrollViewExt extends ScrollView {
                |
                |    private ScrollViewListener scrollViewListener = null;
                |
                |    public ScrollViewExt(Context context) {
                |        super(context);
                |    }
                |
                |    public ScrollViewExt(Context context, AttributeSet attrs, int defStyle) {
                |        super(context, attrs, defStyle);
                |    }
                |
                |    public ScrollViewExt(Context context, AttributeSet attrs) {
                |        super(context, attrs);
                |    }
                |
                |    @Override
                |    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
                |        super.onScrollChanged(l, t, oldl, oldt);
                |        if (scrollViewListener != null) {
                |            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
                |        }
                |    }
                |
                |}""".trimMargin()))
            .issues(ImportAutodisposeDetector.ISSUE_AUTODISPOSE_USAGE)
            .run()
            .expectClean()
    }

    @Test
    fun testFailedJavaFile() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(autodisposeStub, LintDetectorTest.java("""
                |package com.edf.edfetmoi.presentation.common.customview;
                |
                |import android.content.Context;
                |import android.util.AttributeSet;
                |import android.widget.ScrollView;
                |import com.uber.autodispose.kotlin.autoDisposable;
                |
                |import com.edf.edfetmoi.presentation.feature.old.newsfeed.listener.ScrollViewListener;
                |
                |/**
                | * Created by sgabel on 22/11/2016.
                | */
                |
                |public class ScrollViewExt extends ScrollView {
                |
                |    private ScrollViewListener scrollViewListener = null;
                |
                |    public ScrollViewExt(Context context) {
                |        super(context);
                |    }
                |
                |    public ScrollViewExt(Context context, AttributeSet attrs, int defStyle) {
                |        super(context, attrs, defStyle);
                |    }
                |
                |    public ScrollViewExt(Context context, AttributeSet attrs) {
                |        super(context, attrs);
                |    }
                |
                |    @Override
                |    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
                |        super.onScrollChanged(l, t, oldl, oldt);
                |        if (scrollViewListener != null) {
                |            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
                |        }
                |    }
                |
                |}""".trimMargin()))
            .issues(ImportAutodisposeDetector.ISSUE_AUTODISPOSE_USAGE)
            .run()
            .expectErrorCount(1)
    }
}
