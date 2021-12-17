package com.example.test.api

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import com.example.test.R


object ApiUiHelper {
    fun ShowApiRetryAlert(
        header: String,
        message: String,
        context: Context,
        retryClickListener: suspend () -> Unit
    ) {
        try {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle(header)
            builder.setMessage(message)
            builder.setCancelable(false)
            builder.setPositiveButton(
                "Retry", fun(dialog: DialogInterface, which: Int) {
                    Log.e("BEFORE", "FD")
                    Log.e("AFTER", "FD")
                    Log.e("OUTSIDE", "FD")
                    dialog.dismiss()
                }
            )
            val dialog = builder.show()
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(context.resources.getColor(android.R.color.black));
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(context.resources.getColor(android.R.color.black));
        } catch (e: Exception) {
            Log.e("API", "ShowApiRetryAlert: ", e)
        }
    }

    fun defaultLoadingDialog(context: Context): Dialog? {
        var dialog: Dialog? = Dialog(context, android.R.style.Theme_Black)
        try {
            dialog?.setCancelable(true)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val mInflater: LayoutInflater = LayoutInflater.from(context)

            val mView: View = mInflater.inflate(R.layout.loading_dialogue, null)
            dialog?.setContentView(mView)
            dialog?.show()

        } catch (e: java.lang.Exception) {
            Log.e("API", "defaultLoadingDialog: ", e)
            dialog = null
        }
        return dialog
    }

}
