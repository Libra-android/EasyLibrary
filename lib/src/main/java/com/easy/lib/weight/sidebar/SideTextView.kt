package com.easy.lib.weight.sidebar

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatTextView

/**
 * @author too young
 * @date  2020/4/11 13:33
 */
class SideTextView : AppCompatTextView{

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle)


    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)


    constructor(context: Context) : super(context)


    private var func:(View) -> Unit  = {

    }

    fun setEventSelect(func:(View) -> Unit){
        this.func = func
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("aaaaa", "$event")
        func.invoke(this)
        return super.onTouchEvent(event)
    }

}