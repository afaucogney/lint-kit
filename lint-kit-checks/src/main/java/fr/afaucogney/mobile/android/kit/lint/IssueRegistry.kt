package fr.afaucogney.mobile.android.kit.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.vanniktech.lintrules.android.ISSUE_COLOR_CASING
import com.vanniktech.lintrules.android.ISSUE_ERRONEOUS_LAYOUT_ATTRIBUTE
import com.vanniktech.lintrules.android.ISSUE_INVALID_IMPORT
import com.vanniktech.lintrules.android.ISSUE_INVALID_STRING
import com.vanniktech.lintrules.android.ISSUE_LAYOUT_FILE_NAME_MATCHES_CLASS
import com.vanniktech.lintrules.android.ISSUE_MATCHING_MENU_ID
import com.vanniktech.lintrules.android.ISSUE_MISSING_SCROLLBARS
import com.vanniktech.lintrules.android.ISSUE_MISSING_XML_HEADER
import com.vanniktech.lintrules.android.ISSUE_NAMING_PATTERN
import com.vanniktech.lintrules.android.ISSUE_RAW_COLOR
import com.vanniktech.lintrules.android.ISSUE_RAW_DIMEN
import com.vanniktech.lintrules.android.ISSUE_RESOURCES_GET_COLOR
import com.vanniktech.lintrules.android.ISSUE_RESOURCES_GET_COLOR_STATE_LIST
import com.vanniktech.lintrules.android.ISSUE_RESOURCES_GET_DRAWABLE
import com.vanniktech.lintrules.android.ISSUE_SHOULD_USE_STATIC_IMPORT
import com.vanniktech.lintrules.android.ISSUE_SUPERFLUOUS_MARGIN_DECLARATION
import com.vanniktech.lintrules.android.ISSUE_SUPERFLUOUS_NAME_SPACE
import com.vanniktech.lintrules.android.ISSUE_SUPERFLUOUS_PADDING_DECLARATION
import com.vanniktech.lintrules.android.ISSUE_UNSUPPORTED_LAYOUT_ATTRIBUTE
import com.vanniktech.lintrules.android.ISSUE_UNUSED_MERGE_ATTRIBUTES
import com.vanniktech.lintrules.android.ISSUE_WRONG_ANNOTATION_ORDER
import com.vanniktech.lintrules.android.ISSUE_WRONG_CONSTRAINT_LAYOUT_USAGE
import com.vanniktech.lintrules.android.ISSUE_WRONG_DRAWABLE_NAME
import com.vanniktech.lintrules.android.ISSUE_WRONG_GLOBAL_ICON_COLOR
import com.vanniktech.lintrules.android.ISSUE_WRONG_LAYOUT_NAME
import com.vanniktech.lintrules.android.ISSUE_WRONG_MENU_ID_FORMAT
import com.vanniktech.lintrules.android.ISSUE_WRONG_VIEW_ID_FORMAT
import com.vanniktech.lintrules.android.ISSUE_XML_SPACING
import com.vanniktech.lintrules.rxjava2.ISSUE_DEFAULT_SCHEDULER
import com.vanniktech.lintrules.rxjava2.ISSUE_METHOD_MISSING_CHECK_RETURN_VALUE
import com.vanniktech.lintrules.rxjava2.RxJava2MissingCompositeDisposableClearDetector
import fr.afaucogney.mobile.android.kit.lint.rules.contract.ViewModelContractExposeMutableLiveDataDetector
import fr.afaucogney.mobile.android.kit.lint.rules.contract.WrongViewCapabilitiesApiINamingDetector
import fr.afaucogney.mobile.android.kit.lint.rules.contract.WrongViewEventApiINamingDetector
import fr.afaucogney.mobile.android.kit.lint.rules.contract.WrongViewModelApiNamingDetector
import fr.afaucogney.mobile.android.kit.lint.rules.contract.ViewModelExposedTypeIsNotLiveDataDetector
import fr.afaucogney.mobile.android.kit.lint.rules.contract.ViewModelMethodParameterIsCallbackDetector
import fr.afaucogney.mobile.android.kit.lint.rules.contract.WrongViewNavigationApiINamingDetector
import fr.afaucogney.mobile.android.kit.lint.rules.contract.WrongViewTagApiNamingDetector
import fr.afaucogney.mobile.android.kit.lint.rules.contract.NotEnoughtFeatureContractInterfaceSegregationDetector
import fr.afaucogney.mobile.android.kit.lint.rules.contract.WrongFeatureContractNamingDetector
import fr.afaucogney.mobile.android.kit.lint.rules.depreciation.AutodisposeStillImportedDetector
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
            // Xml Naming
            // WrongViewIdNameDetector.ISSUE,
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
            NotEnoughtFeatureContractInterfaceSegregationDetector.ISSUE_FEATURE_CONTRACT_SEGREGATION,
            // Architecture

            // Android-Lint

            // Resources
            ISSUE_RAW_COLOR,
            ISSUE_RAW_DIMEN,
            ISSUE_COLOR_CASING,
            ISSUE_INVALID_STRING,
            ISSUE_INVALID_IMPORT,
            ISSUE_RESOURCES_GET_COLOR,
            ISSUE_RESOURCES_GET_DRAWABLE,
            ISSUE_RESOURCES_GET_COLOR_STATE_LIST,

            // Layout
            ISSUE_ERRONEOUS_LAYOUT_ATTRIBUTE,
            ISSUE_LAYOUT_FILE_NAME_MATCHES_CLASS,
            ISSUE_MISSING_SCROLLBARS,
            ISSUE_SUPERFLUOUS_MARGIN_DECLARATION,
            ISSUE_SUPERFLUOUS_PADDING_DECLARATION,
            ISSUE_UNSUPPORTED_LAYOUT_ATTRIBUTE,
            ISSUE_UNUSED_MERGE_ATTRIBUTES,
            ISSUE_WRONG_CONSTRAINT_LAYOUT_USAGE,
            ISSUE_WRONG_VIEW_ID_FORMAT,

            // Menu
            ISSUE_MATCHING_MENU_ID,
            ISSUE_WRONG_MENU_ID_FORMAT,

            // Files
            ISSUE_SUPERFLUOUS_NAME_SPACE,
            ISSUE_MISSING_XML_HEADER,
            ISSUE_WRONG_DRAWABLE_NAME,
            ISSUE_XML_SPACING,

            // Naming
            ISSUE_NAMING_PATTERN,
            ISSUE_WRONG_LAYOUT_NAME,

            // Clearness
            ISSUE_SHOULD_USE_STATIC_IMPORT,
            ISSUE_WRONG_ANNOTATION_ORDER,

            // Performance
            ISSUE_WRONG_GLOBAL_ICON_COLOR,



            // RxJava Vannitech
            ISSUE_METHOD_MISSING_CHECK_RETURN_VALUE,
            ISSUE_DEFAULT_SCHEDULER,
        )
}
