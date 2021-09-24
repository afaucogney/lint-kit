[![](https://jitpack.io/v/afaucogney/lint-kit.svg)](https://jitpack.io/#afaucogney/lint-kit)

## Lint Kit for Android Developpement

## Rules

 - WrongFeatureContractNamingDetector
 - WellSegregationOfFeatureContractInterfaceDetector
 - UseMutableLiveDataInViewModelContractDetector
 - ImportAutodisposeDetector
 - WrongViewIdNameDetector

## How to integrate

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.afaucogney:lint-kit:-SNAPSHOT'
	}
  
Jitpack link for other versions : [here](https://jitpack.io/#afaucogney/lint-kit/-SNAPSHOT)

## How configure you project Lint setup

You 

```
// Settings
android {
    lintOptions {
        checkReleaseBuilds false
        // Or, if you prefer, you can continue to check for errors in release builds,
        // but continue the build even when errors are found:
        abortOnError false
        htmlOutput file("${project.buildDir}/reports/lint/lint-report.html")
        xmlOutput file("${project.buildDir}/reports/lint/lint-report.xml")

        enable 'WrongThreadInterprocedural',
                'UnusedIds',
                'UnsupportedChromeOsHardware',
                'UnpackedNativeCode',
                'UnknownNullness',
                'TypographyQuotes',
                'SyntheticAccessor',
                'StopShip',
                'SelectableText',
                'RequiredSize',
                'Registered',
                'PermissionImpliesUnsupportedChromeOsHardware',
                'NoHardKeywords',
                'NewerVersionAvailable',
                'NegativeMargin',
                'MissingRegistered',
                'MinSdkTooLow',
                'MangledCRLF',
                'LogConditional',
                'LambdaLast',
                'KotlinPropertyAccess',
                'IconExpectedSize',
                'FieldGetter',
                'EasterEgg',
                'ConvertToWebp',
                'BackButton',
                'AppLinksAutoVerifyWarning',
                'AppLinksAutoVerifyError',
                'CanvasSize',
                'IntentReset',
                'InvalidNavigation',
                'InvalidWakeLockTag',
                'MissingDefaultResource',
                'RequiresFeature',
                'Slices',
                'TranslucentOrientation',
                'ValidActionsXml',
                'Untranslatable',
                'DeletedProvider',
                'DeprecatedProvider',
                'ProxyPassword',
                'RiskyLibrary',
                'ExpiredTargetSdkVersion',
                'ExpiringTargetSdkVersion',
                'OutdatedLibrary',
                'SyntheticAccessor',
                'Autofill',
                'ConstantLocale',
                'KotlinPropertyAccess',
                'LambdaLast',
                'NoHardKeywords',
                'UnknownNullness'
        disable 'TrustAllX509TrustManager',
                'ObsoleteLintCustomCheck',
                'SyntheticAccessor', // many use case in Kotlin
                'Overdraw',
                'MissingRegistered', // Lib are not part of sources sets for Lint
                'PluralsCandidate', // Generation is done from POEditor, case to case exception is hard
                'UnusedIds'
        warningsAsErrors true
        lintConfig file("${rootProject.projectDir}/config/lint/lint-config.xml")
    }
}

def version = "1" // Android version code, is not automatic because we cannot get if for module :o(
def enableBaselineUpdate = false

// Baseline only for Danger in a PR context
if (System.getenv('IsPR') || enableBaselineUpdate) {
    android {
        lintOptions {
            baseline file("${project.projectDir}/baseline/lint/lint-baseline-v${version}.xml")
        }
    }
}

// To build a new base line for every module
// Update the variable with the currentVersionCode
// Enable temporary enableBaselineUpdate
// Run a couple of time lintDebug, until every module get updated, and is succeed
// Disable temporary enableBaselineUpdate
// Delete the previous baseline
// Commit version update + baseline files
```
