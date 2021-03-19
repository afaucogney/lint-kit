package fr.afaucogney.mobile.android.kit.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import fr.afaucogney.mobile.android.kit.lint.rules.contract.UseMutableLiveDataInViewModelContractDetector
import fr.afaucogney.mobile.android.kit.lint.rules.contract.WellSegregationOfFeatureContractInterfaceDetector
import fr.afaucogney.mobile.android.kit.lint.rules.contract.WrongFeatureContractNamingDetector
import fr.afaucogney.mobile.android.kit.lint.rules.depreciation.ImportAutodisposeDetector
import fr.afaucogney.mobile.android.kit.lint.rules.xml.WrongViewIdNameDetector

@Suppress("UnstableApiUsage")
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
            WrongFeatureContractNamingDetector.ISSUE_FEATURE_CONTRACT_NAMING,
            WellSegregationOfFeatureContractInterfaceDetector.ISSUE_FEATURE_CONTRACT_SEGREGATION,
            UseMutableLiveDataInViewModelContractDetector.ISSUE_MUTABLELIVEDATA_IN_FEATURE_CONTRACT,
            ImportAutodisposeDetector.ISSUE_AUTODISPOSE_USAGE,
            WrongViewIdNameDetector.ISSUE_WRONG_VIEW_ID_NAME,
        )
}
