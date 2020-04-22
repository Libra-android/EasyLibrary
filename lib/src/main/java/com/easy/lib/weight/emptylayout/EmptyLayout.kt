package com.easy.lib.weight.emptylayout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.easy.lib.R

/**
 * @author too young
 * @date  2020/4/10 9:46
 */
class EmptyLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    constructor(context: Context) : this(context, null)

    private val emptyLayout: View =
        LayoutInflater.from(context).inflate(R.layout.layout_empty, null)

    init {
        addView(emptyLayout)
    }

    fun bindData(func: (View) -> Unit) {
        func.invoke(emptyLayout)
    }

}