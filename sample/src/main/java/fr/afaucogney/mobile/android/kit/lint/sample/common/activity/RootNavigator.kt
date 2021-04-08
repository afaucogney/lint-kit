package fr.afaucogney.mobile.android.kit.lint.sample.common.activity

import androidx.appcompat.app.AppCompatActivity
import fr.afaucogney.mobile.android.kit.lint.sample.R
import fr.afaucogney.mobile.android.kit.lint.sample.feature.sample.presentation.FirstFragment

class RootNavigator(private val activity: AppCompatActivity) :
    ISingleActivityFeatureContract.ViewNavigation {

    override fun showFirstScreen() {
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, FirstFragment.newInstance())
            .commitNow()
    }

    override fun quit() {
        activity
            .finish()
    }
}