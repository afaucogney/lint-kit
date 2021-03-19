package fr.niji.mobile.android.kit.lit

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import fr.niji.mobile.android.kit.lit.rules.contract.UseMutableLiveDataInViewModelContractDetector
import fr.niji.mobile.android.kit.lit.rules.contract.WellSegregationOfFeatureContractInterfaceDetector
import fr.niji.mobile.android.kit.lit.rules.contract.WrongFeatureContractNamingDetector
import fr.niji.mobile.android.kit.lit.rules.depreciation.ImportAutodisposeDetector
import fr.niji.mobile.android.kit.lit.rules.xml.WrongViewIdNameDetector

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
