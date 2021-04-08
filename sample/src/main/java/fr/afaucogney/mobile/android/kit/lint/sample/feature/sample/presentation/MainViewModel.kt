package fr.afaucogney.mobile.android.kit.lint.sample.feature.sample.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.afaucogney.mobile.android.kit.lint.sample.feature.sample.ISampleFeatureContract

class MainViewModel :
    ISampleFeatureContract.ViewModel,
    ViewModel() {
    override val mutableLiveData = MutableLiveData("mutable")

    override val liveData = MutableLiveData("immutable")

    override fun refresh() {
        Log.d("LINT", "Refresh")
    }
}