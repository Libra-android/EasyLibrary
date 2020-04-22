package com.easy.lib.weight.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @author too young
 * @date  2019/12/10 10:48
 */
class NcViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private var isCanScroll = false
    private var smoothScroll = false

    constructor(context: Context) : this(context, null)

    fun setCanScroll(isCanScroll: Boolean) {
        this.isCanScroll = isCanScroll
    }

    fun setSmoothScroll(smoothScroll: Boolean) {
        this.smoothScroll = smoothScroll
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return if (isCanScroll) {
            super.onTouchEvent(arg0)
        } else {
            false
        }
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, smoothScroll)
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return if (isCanScroll) {
            super.onInterceptTouchEvent(arg0)
        } else {
            false
        }
    }

}