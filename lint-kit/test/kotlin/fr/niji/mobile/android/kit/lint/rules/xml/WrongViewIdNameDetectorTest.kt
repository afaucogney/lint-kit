package fr.niji.mobile.android.kit.lit.rules.xml

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.Test

class WrongViewIdNameDetectorTest : LintDetectorTest() {
    @Test
    fun testGaps_withMarginDivisbleByFour_returnsError() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "/res/layout/test.xml",
                    """
                        <?xml version="1.0" encoding="utf-8"?>
                        <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                            android:layout_width="match_parent"
                            android:layout_height="144dp"
                            android:layout_marginStart="12dp"
                            android:background="@color/coolBlue" />
                    """.trimIndent()
                )
            )
            .run()
            .expectClean()
    }

    @Test
    fun testGaps_withMarginDivisbleByFour_returns1Error() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "/res/layout/test.xml",
                    """
                        <?xml version="1.0" encoding="utf-8"?>
                        <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                            android:id="fl_xxxx"
                            android:layout_width="match_parent"
                            android:layout_height="144dp"
                            android:layout_marginStart="12dp"
                            android:background="@color/coolBlue" />
                    """.trimIndent()
                )
            )
            .run()
            .expectClean()
    }

    @Test
    fun testGaps_withMarginDivisbleByFour_returns2Error() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "/res/layout/test.xml",
                    """
                        <?xml version="1.0" encoding="utf-8"?>
                        <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                            android:id="flxxxx"
                            android:layout_width="match_parent"
                            android:layout_height="144dp"
                            android:layout_marginStart="12dp"
                            android:background="@color/coolBlue" />
                    """.trimIndent()
                )
            )
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testGaps_withMarginDivisbleByFour_returns3Error() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "/res/layout/test.xml",
                    """
                        <?xml version="1.0" encoding="utf-8"?>
                        <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                            android:id="xxxx"
                            android:layout_width="match_parent"
                            android:layout_height="144dp"
                            android:layout_marginStart="12dp"
                            android:background="@color/coolBlue" />
                    """.trimIndent()
                )
            )
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun testGaps_withMarginDivisbleByFour_returns4Error() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "/res/layout/test.xml",
                    """
                    <?xml version="1.0" encoding="utf-8"?>
                    <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
                        xmlns:app="http://schemas.android.com/apk/res-auto"
                        xmlns:tools="http://schemas.android.com/tools"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        tools:context="com.ouestfrance.feature.article.presentation.ArticleFragment">
                    
                        <androidx.constraintlayout.widget.ConstraintLayout
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:elevation="1dp"
                            app:layout_constraintTop_toTopOf="parent">
                    
                            <ImageButton
                                android:id="@+id/btn_close"
                                android:layout_width="@dimen/toolbar_item_size"
                                android:layout_height="@dimen/toolbar_item_size"
                                android:layout_marginHorizontal="@dimen/spacing_m"
                                android:layout_marginVertical="@dimen/spacing_xs"
                                android:background="@drawable/bg_rounded_black"
                                android:contentDescription="@null"
                                android:src="@drawable/ic_close"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toTopOf="parent" />
                    
                            <ImageButton
                                android:id="@+id/btn_read_later"
                                android:layout_width="@dimen/toolbar_item_size"
                                android:layout_height="@dimen/toolbar_item_size"
                                android:layout_marginHorizontal="@dimen/spacing_m"
                                android:layout_marginVertical="@dimen/spacing_xs"
                                android:background="@drawable/bg_rounded_black"
                                android:contentDescription="@null"
                                android:src="@drawable/ic_read_later"
                                app:layout_constraintEnd_toStartOf="@id/btn_share"
                                app:layout_constraintTop_toTopOf="parent" />
                    
                            <ImageButton
                                android:id="@+id/btn_share"
                                android:layout_width="@dimen/toolbar_item_size"
                                android:layout_height="@dimen/toolbar_item_size"
                                android:layout_marginHorizontal="@dimen/spacing_m"
                                android:layout_marginVertical="@dimen/spacing_xs"
                                android:background="@drawable/bg_rounded_black"
                                android:contentDescription="@null"
                                android:src="@drawable/ic_share"
                                app:layout_constraintEnd_toEndOf="parent"
                                app:layout_constraintTop_toTopOf="parent" />
                    
                        </androidx.constraintlayout.widget.ConstraintLayout>
                    
                        <androidx.recyclerview.widget.RecyclerView
                            android:id="@+id/rv_body"
                            android:layout_width="match_parent"
                            android:layout_height="match_parent"
                            android:orientation="vertical"
                            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
                            tools:itemCount="1"
                            tools:listitem="@layout/item_article_header" />
                    
                        <ProgressBar
                            android:id="@+id/pb_loading"
                            android:layout_width="24dp"
                            android:layout_height="24dp"
                            android:progressTint="@color/red_monza"
                            app:layout_constraintBottom_toBottomOf="parent"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toStartOf="parent"
                            app:layout_constraintTop_toTopOf="parent" />
                    
                    </androidx.constraintlayout.widget.ConstraintLayout>
                      """.trimIndent()
                )
            )
            .run()
            .expectErrorCount(3)
    }


    override fun getDetector(): Detector {
        return WrongViewIdNameDetector()
    }

    override fun getIssues(): List<Issue> {
        return listOf(WrongViewIdNameDetector.ISSUE_WRONG_VIEW_ID_NAME)
    }
}
