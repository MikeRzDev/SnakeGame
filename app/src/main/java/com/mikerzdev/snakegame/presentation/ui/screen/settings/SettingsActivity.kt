package com.mikerzdev.snakegame.presentation.ui.screen.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.mikerzdev.snakegame.R
import com.mikerzdev.snakegame.databinding.ActivitySettingsBinding
import com.mikerzdev.snakegame.presentation.utils.bind
import com.mikerzdev.snakegame.presentation.utils.showColorPickerDialog
import com.mikerzdev.snakegame.presentation.utils.uLongColorToArgbColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {
    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        viewModel.getGameSettings()
        viewBindings()
        viewModelBindings()
    }

    private fun viewBindings() {
        binding.settingsButtonBack.setOnClickListener {
            finish()
        }
        binding.viewColorChooserPlayer1.setOnClickListener {
            showColorPickerDialog(this) { color ->
                binding.viewColorChooserPlayer1.setBackgroundColor(color)
                viewModel.setPlayer1Color(color)
            }
        }
        binding.viewColorChooserPlayer2.setOnClickListener {
            showColorPickerDialog(this) { color ->
                binding.viewColorChooserPlayer2.setBackgroundColor(color)
                viewModel.setPlayer2Color(color)
            }
        }
        binding.textInputLayoutPlayer1Name.editText?.doAfterTextChanged { text ->
            viewModel.setPlayer1Name(text.toString())
        }
        binding.textInputLayoutPlayer2Name.editText?.doAfterTextChanged { text ->
            viewModel.setPlayer2Name(text.toString())
        }
        binding.buttonSaveSettings.setOnClickListener {
            viewModel.saveGameSettings {
                Snackbar.make(
                    binding.root,
                    getString(R.string.settings_message_settings_saved),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun viewModelBindings() {
        viewModel.snake1MetaDataLiveData.bind(this) { snakeMetaData ->
            if (snakeMetaData != null) {
                if (snakeMetaData.color != 0UL) {
                    binding.viewColorChooserPlayer1.setBackgroundColor(uLongColorToArgbColor(snakeMetaData.color))
                }
                if (snakeMetaData.name.isNotEmpty()) {
                    binding.textInputLayoutPlayer1Name.editText?.setText(snakeMetaData.name)
                }
            }
        }
        viewModel.snake2MetaDataLiveData.bind(this) { snakeMetaData ->
            if (snakeMetaData != null) {
                if (snakeMetaData.color != 0UL) {
                    binding.viewColorChooserPlayer2.setBackgroundColor(uLongColorToArgbColor(snakeMetaData.color))
                }
                if (snakeMetaData.name.isNotEmpty()) {
                    binding.textInputLayoutPlayer2Name.editText?.setText(snakeMetaData.name)
                }
            }
        }
    }
}
