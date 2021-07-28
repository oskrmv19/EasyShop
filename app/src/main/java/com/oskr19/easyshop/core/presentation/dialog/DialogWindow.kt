package com.oskr19.easyshop.core.presentation.dialog

import android.content.Context
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog

/**
 * Created by oscar.vergara on 30/06/2021
 */
object DialogWindow {

    private fun makeDialog(context: Context, actions: DialogActions? = null,
                           title: String? = "",
                           message: String? = "",
                           positiveText: String? = null,
                           negativeText: String? = null): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        if(!TextUtils.isEmpty(title)) {
            builder.setTitle(title)
        }
        if(!TextUtils.isEmpty(positiveText)) {
            builder.setPositiveButton( positiveText ) { dialog, _ ->
                dialog.dismiss()
                actions?.actionPositive()
            }
        }
        if(!TextUtils.isEmpty(negativeText)) {
            builder.setNegativeButton( negativeText ) { dialog, _ ->
                dialog.dismiss()
                actions?.actionNegative()
            }
        }
        return builder.create()
    }
}