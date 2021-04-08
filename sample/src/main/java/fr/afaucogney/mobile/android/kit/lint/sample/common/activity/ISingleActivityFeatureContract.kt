package fr.afaucogney.mobile.android.kit.lint.sample.common.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ISingleActivityFeatureContract {

    interface ViewCapability{
        fun showFab()
    }

    interface ViewModel {
    }

    interface ViewNavigation {
        fun showFirstScreen()
        fun quit()
    }

    interface ViewTag {
    }

    interface ViewEvent {
    }

}