package com.vdaika.apputils

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import com.vdaika.apputils.rating.RatingDialog

object Utils {
    fun freeView(root: View?) {
        var rootView = root
        if (rootView == null) {
            return
        }
        rootView.background = null
        if (rootView.background != null) {
            rootView.background.callback = null
        }
        if (rootView is ViewGroup) {
            for (i in 0 until rootView.childCount) {
                freeView(rootView.getChildAt(i))
            }
            if (rootView !is AdapterView<*>) {
                rootView.removeAllViews()
            } else {
                rootView.adapter = null
                rootView = null
            }
        }
        if (rootView is SeekBar) {
            rootView.progressDrawable = null
        }
        if (rootView is ImageView) {
            rootView.setImageDrawable(null)
            rootView.setImageBitmap(null)
        }
    }

    fun rateApp(context: Context?) {
        val ratingDialog = RatingDialog.Builder(context!!)
                .threshold(4f)
                .onRatingBarFormSumbit(object : RatingDialog.Builder.RatingDialogFormListener {
                    override fun onFormSubmitted(feedback: String?) {
                        Toast.makeText(context, "Thank you for your feedback!", Toast.LENGTH_SHORT)
                    }
                }).build()
        ratingDialog.show()
    }

    fun shareApp(context: Context) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.putExtra(Intent.EXTRA_SUBJECT, "Sharing app")
        i.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + context.packageName)
        context.startActivity(Intent.createChooser(i, "Share"))
    }
}