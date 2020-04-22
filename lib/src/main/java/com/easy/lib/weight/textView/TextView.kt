package com.easy.lib.weight.textView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.easy.lib.R

/**
 * @author too young
 * @date  2020/4/1 16:40
 */
class TextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    constructor(context: Context) : this(context, null)

    //选中时下划线颜色
    private var textLineColor = ContextCompat.getColor(context, R.color.textLineColor)

    //未选中时下划线颜色
    private var textNoColor = ContextCompat.getColor(context, R.color.textNoColor)

    //下划线高度(单位像素)
    private var bottomLineHeight = 3
    private var bottomLineWidth = 100

    private val paint = Paint()

    init {
        setOnFocusChangeListener { view, hasFocus ->
            Log.d("edit has fause", "$hasFocus---")
            if (hasFocus) {
                paint.color = textLineColor
            } else {
                paint.color = textNoColor
            }
            invalidate()
        }

        background = null
        paint.strokeWidth = bottomLineHeight.toFloat()
        paint.color = textNoColor

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        val startX = (width.toFloat() - bottomLineWidth) / 2
        canvas?.apply {
            drawLine(
                startX,
                height.toFloat(),
                startX + bottomLineWidth,
                height.toFloat(),
                paint
            )
        }

    }

    //设置输入框下划线颜色
    fun setTextLineColor(textLineColor: Int) {
        this.textLineColor = ContextCompat.getColor(context, textLineColor)
        paint.color = this.textLineColor
        invalidate()
    }

    //设置输入框下划线高度
    fun setBottomLine(bottomLineHeight: Int, bottomLineWidth: Int) {
        this.bottomLineHeight = bottomLineHeight
        this.bottomLineWidth = bottomLineWidth
        paint.strokeWidth = bottomLineHeight.toFloat()
        invalidate()
    }



}