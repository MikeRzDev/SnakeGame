package com.mikerzdev.snakegame.presentation.ui.base

import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog

abstract class BaseComposeActivity : ComponentActivity() {
    protected lateinit var progressDialog: AlertDialog
    private lateinit var errorDialog: AlertDialog

    protected fun showErrorMessage(message: String) {
        errorDialog.setMessage(message)
        errorDialog.show()
    }

    private fun createProgressDialog() = AlertDialog.Builder(this)
        .setView(ProgressBar(this))
        .create()

    private fun createErrorDialog() = AlertDialog.Builder(this)
        .setTitle("Error")
        .setPositiveButton("Dismiss", null)
        .create()
}