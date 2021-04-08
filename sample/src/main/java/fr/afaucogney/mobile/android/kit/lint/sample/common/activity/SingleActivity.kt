package fr.afaucogney.mobile.android.kit.lint.sample.common.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.afaucogney.mobile.android.kit.lint.sample.R
import fr.afaucogney.mobile.android.kit.lint.sample.common.activity.ISingleActivityFeatureContract.*
import fr.afaucogney.mobile.android.kit.lint.sample.databinding.ActivityMainBinding
import fr.afaucogney.mobile.android.kit.lint.sample.databinding.ActivitySingleBinding

class SingleActivity :
    ViewCapability,
    AppCompatActivity() {

    private lateinit var binding: ActivitySingleBinding

    private val rootNavigator: ViewNavigation by lazy {
        RootNavigator(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            rootNavigator.showFirstScreen()
        }
    }

    override fun showFab() {
        TODO("Not yet implemented")
    }
}