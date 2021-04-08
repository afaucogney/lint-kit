package fr.afaucogney.mobile.android.kit.lint.sample.feature.sample.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.afaucogney.mobile.android.kit.lint.sample.feature.sample.ISampleFeatureContract
import fr.afaucogney.mobile.android.kit.lint.sample.R
import fr.afaucogney.mobile.android.kit.lint.sample.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    companion object {
        fun newInstance() = FirstFragment()
    }

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ISampleFeatureContract.ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        with(viewModel) {
            liveData.observe(viewLifecycleOwner) {
                Log.d("LINT", it)
            }
            mutableLiveData.observe(viewLifecycleOwner) {
                Log.d("LINT", it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
