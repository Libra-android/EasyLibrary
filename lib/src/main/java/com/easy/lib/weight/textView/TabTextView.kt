package com.easy.lib.weight.textView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.easy.lib.R

/**
 * @author too young
 * @date  2020/4/1 16:40
 */
class TabTextView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    constructor(context: Context) : this(context, null)

    val textView: AppCompatTextView
    val imageView: AppCompatImageView

    init {
        setWillNotDraw(false)
        val view = LayoutInflater.from(context).inflate(R.layout.tab_textview, null)
        addView(view)
        textView = view.findViewById(R.id.textView)
        imageView = view.findViewById(R.id.imageView)
    }

}