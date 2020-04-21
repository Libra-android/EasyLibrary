package com.easy.lib.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.TextView
import com.easy.lib.R
import kotlinx.android.synthetic.main.layout_dialog.*

/**
 * @author too young
 * @date 2020/4/21 21:08
 */
object LoadDialog{

    fun loadView(context: Context) = LoadDialog(context)

    class LoadDialog : Dialog {
        constructor(context: Context) : super(context) {}
        constructor(context: Context, theme: Int) : super(context, theme) {}

        init {
            setContentView(R.layout.layout_dialog)

        }

        fun setData(func : (View, LoadView, TextView) -> Unit){
            func.invoke(dialog, loadView, textView)
        }


    }

}

