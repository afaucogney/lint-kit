package fr.afaucogney.mobile.android.kit.lint.sample.feature.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ISampleFeatureContract {

    interface ViewCapability{
        fun showText()
    }

    interface ViewModel {
        val mutableLiveData : MutableLiveData<String>
        val liveData : LiveData<String>
        fun refresh()

    }

    interface ViewNavigation {
        fun quit()

    }

    interface ViewTag {
    }

    interface ViewEvent {
    }
}