package com.example.quiz_app.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.KeyEvent
import com.example.quiz_app.R

class LoadingDialog(private var context: Context?) {

//    private var progressDialog: ProgressDialog = ProgressDialog(activity)
    private var dialog: Dialog = Dialog(context!!)


    fun showLoading() {
        dialogShow()
        dialog.setOnKeyListener { arg0, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                cancelLoading()
            }
            true
        }

    }

    fun cancelLoading() {
        dialog.dismiss()
    }

    fun dialogShow() {
        dialog.create()
        dialog.setContentView(R.layout.circular_progress_layout)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        dialog.show()

    }


}
