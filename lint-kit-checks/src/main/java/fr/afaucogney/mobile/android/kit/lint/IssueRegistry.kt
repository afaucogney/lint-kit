@file:Suppress("UnstableAPIUSage") // We know that Lint API's aren't final.

package fr.afaucogney.mobile.android.kit.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import fr.afaucogney.mobile.android.kit.lint.rules.contract.*
import fr.afaucogney.mobile.android.kit.lint.rules.depreciation.AutodisposeStillImportedDetector
import fr.afaucogney.mobile.android.kit.lint.rules.xml.WrongViewIdNameDetector

class IssueRegistry : IssueRegistry() {

    ///////////////////////////////////////////////////////////////////////////
    // CONFIGURATION
    ///////////////////////////////////////////////////////////////////////////

    override val api: Int = CURRENT_API

    ///////////////////////////////////////////////////////////////////////////
    // SPECIALIZATION
    ///////////////////////////////////////////////////////////////////////////

    override val issues: List<Issue>
        get() = listOf(
            // Xml Naming
            WrongViewIdNameDetector.ISSUE,

            // Depreciation
            AutodisposeStillImportedDetector.ISSUE,

            // ViewModel only LiveData
            ViewModelContractExposeMutableLiveDataDetector.ISSUE,
            ViewModelMethodParameterIsCallbackDetector.ISSUE,
            ViewModelExposedTypeIsNotLiveDataDetector.ISSUE,

            // Feature Contract Api naming
            WrongFeatureContractNamingDetector.ISSUE,
            WrongViewCapabilitiesApiINamingDetector.ISSUE,
            WrongViewModelApiNamingDetector.ISSUE,
            WrongViewNavigationApiINamingDetector.ISSUE,
            WrongViewEventApiINamingDetector.ISSUE,
            WrongViewTagApiNamingDetector.ISSUE,
            NotEnoughtFeatureContractInterfaceSegregationDetector.ISSUE,

            // Architecture

            // Android-Lint
        )
}
