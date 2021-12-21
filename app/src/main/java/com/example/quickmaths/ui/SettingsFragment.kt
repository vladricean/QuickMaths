package com.example.quickmaths.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.quickmaths.R
import com.example.quickmaths.constants.Constants
import com.example.quickmaths.databinding.SettingsFragmentBinding
import com.example.quickmaths.services.QuickMathsEncryptedSharedPreferences
import com.example.quickmaths.sharedEncryptedPrefs
import com.example.quickmaths.viewmodels.SettingsViewModel
import com.example.quickmaths.viewmodelsfactory.SettingsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment: Fragment() {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: SettingsFragmentBinding
    @Inject lateinit var randomString: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsFragmentBinding.inflate(inflater)
        val viewModelFactory = SettingsViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Hilt
        Timber.i(randomString)

        setHasOptionsMenu(true)
        handleSwitchStates()

        return binding.root
    }

    private fun handleSwitchStates() {
        binding.switchAddition.setOnCheckedChangeListener{ _, isChecked ->
            sharedEncryptedPrefs.instance.edit().putBoolean(Constants.SWITCH_ADDITION, isChecked).apply()
        }
        binding.switchSubtraction.setOnCheckedChangeListener{ _, isChecked ->
            sharedEncryptedPrefs.instance.edit().putBoolean(Constants.SWITCH_SUBTRACTION, isChecked).apply()
        }
        binding.switchMultiplication.setOnCheckedChangeListener{ _, isChecked ->
            sharedEncryptedPrefs.instance.edit().putBoolean(Constants.SWITCH_MULTIPLICATION, isChecked).apply()
        }
        binding.switchDivision.setOnCheckedChangeListener{ _, isChecked ->
            sharedEncryptedPrefs.instance.edit().putBoolean(Constants.SWITCH_DIVISION, isChecked).apply()
        }
        binding.switchSquareRoot.setOnCheckedChangeListener{ _, isChecked ->
            sharedEncryptedPrefs.instance.edit().putBoolean(Constants.SWITCH_SQUARE_ROOT, isChecked).apply()
        }
        binding.switchExponential.setOnCheckedChangeListener{ _, isChecked ->
            sharedEncryptedPrefs.instance.edit().putBoolean(Constants.SWITCH_EXPONENTIAL, isChecked).apply()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI
            .onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        binding.switchAddition.setChecked(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_ADDITION, true))
        binding.switchSubtraction.setChecked(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_SUBTRACTION, true))
        binding.switchMultiplication.setChecked(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_MULTIPLICATION, true))
        binding.switchDivision.setChecked(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_DIVISION, true))
        binding.switchSquareRoot.setChecked(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_SQUARE_ROOT, true))
        binding.switchExponential.setChecked(sharedEncryptedPrefs.instance.getBoolean(Constants.SWITCH_EXPONENTIAL, true))
    }
}