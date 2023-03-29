package com.mikerzdev.snakegame.presentation.utils


import android.content.Context
import com.mikerzdev.snakegame.R
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener


fun showColorPickerDialog(context: Context, onColorSelected: TypedCallback<Int>) {
    ColorPickerDialog.Builder(context, R.style.ColorChooserTheme)
        .setTitle(context.getString(R.string.dialog_color_chooser_title))
        .setPositiveButton(context.getString(R.string.dialog_button_confirm),
            ColorEnvelopeListener { envelope, _ -> onColorSelected(envelope.color) })
        .setNegativeButton(
            context.getString(R.string.dialog_button_cancel)
        ) { dialogInterface, _ -> dialogInterface.dismiss() }
        .attachAlphaSlideBar(false)
        .attachBrightnessSlideBar(true)
        .setBottomSpace(12)
        .show()
}