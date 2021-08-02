package com.oskr19.easyshop.core.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import com.oskr19.easyshop.R

/**
 * Created by oscar.vergara on 30/06/2021
 */
object DialogWindow {

    private fun makeDialog(context: Context, action: DialogAction? = null,
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
                action?.onPositive()
            }
        }
        if(!TextUtils.isEmpty(negativeText)) {
            builder.setNegativeButton( negativeText ) { dialog, _ ->
                dialog.dismiss()
                if(action is NegativeAction) {
                    action.onNegative()
                } else {
                    throw Exception("The action object supplied must implement NegativeAction interface")
                }
            }
        }
        return builder.create()
    }

    fun dialogOnGenericError(context: Context, action: DialogAction?): Dialog {
        return makeDialog(context, action,
            context.getString(R.string.generic_error_title),
            context.getString(R.string.generic_error), context.getString(android.R.string.ok), context.getString(android.R.string.cancel)
        )
    }
}