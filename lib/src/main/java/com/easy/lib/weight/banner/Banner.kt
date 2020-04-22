package com.easy.lib.weight.banner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener

/**
 * @author liu
 * @date 2017/11/6
 */
class Banner : FrameLayout {
    
    private var viewPager: ViewPager? = null
    private var adapter: BannerAdapter? = null
    private var isFirst = true //判断是不是第一次创建用于调整adapter 到正确的显示位置
    private var isTuning = false //判断是否正在轮播
    private var selectPaint //选中的画笔
            : Paint? = null
    private var paint //未选中的画笔
            : Paint? = null
    private var pointCount = 0 //点的数量
    private var locationX = 0f //计算出的点的X坐标
    private var locationY = 0f //计算出的点的Y坐标
    //定义轮播的间隔时间
    private var delayMillis: Long = 0
    //手动控制是否可以轮播
    private var isLoop = false
    //点之间的间距
    private var width = 0f
    //点的大小
    private var pointSize = 0f
    //点距离底部的距离
    private var marginBottom = 0f
    /**
     * 无限轮播任务
     */
    private val task: Runnable = object : Runnable {
        override fun run() {
            viewPager!!.setCurrentItem(viewPager!!.currentItem + 1, true)
            postDelayed(this, delayMillis)
        }
    }
    private val isTouch = false
    /**
     * 当页面切换到第1个和最后一个时，页面调整至合适的位置
     */
    private val pageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {}
        override fun onPageScrollStateChanged(state: Int) {
            if (isLoop && state == ViewPager.SCROLL_STATE_IDLE) {
                if (adapter!!.count != 0) {
                    if (viewPager!!.currentItem == 0) {
                        viewPager!!.setCurrentItem(adapter!!.count / 2, false)
                    } else if (viewPager!!.currentItem == adapter!!.count - 1) {
                        viewPager!!.setCurrentItem(adapter!!.count / 2 - 1, false)
                    }
                }
            }
        }
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context)
    }

    /**
     * 创建holder 和添加data
     *
     * @param holder
     * @param data
     */
    fun createHolder(
        holder: LoopBannerHolder<Any>?,
        data: List<Any>,
        loop: Boolean
    ) {
        isLoop = loop
        pointCount = data.size
        locationX = 0f
        var lastCurtTime = 0
        if (!isFirst && adapter!!.realCount != 0) {
            val realCount = adapter!!.realCount
            val curtCount = viewPager!!.currentItem / realCount
            lastCurtTime = curtCount * data.size + viewPager!!.currentItem % realCount
        }
        adapter = BannerAdapter()
        adapter!!.setData(data)
        adapter!!.createHolder(holder)
        adapter!!.setLoop(loop)
        viewPager!!.adapter = adapter
        if (isFirst) {
            lastCurtTime = adapter!!.count / 2
            isFirst = false
        }
        viewPager!!.setCurrentItem(lastCurtTime, false)
    }

    /**
     * 设置画笔颜色 长度为2的数组 第一个为未选中点的颜色 ，第二个为选中的点的颜色
     *
     * @param colors
     */
    fun setColors(colors: IntArray) {
        if (colors.size < 2) {
            Log.e("banner", "colors length is not 2")
            return
        }
        paint!!.color = colors[0]
        selectPaint!!.color = colors[1]
    }

    /**
     * 设置距离底部的高度 dp
     *
     * @param marginBottom
     */
    fun setMarginBottom(marginBottom: Float) {
        this.marginBottom = dpToPx(marginBottom).toFloat()
        locationY = 0f
    }

    /**
     * 设置点的大小 dp
     *
     * @param pointSize
     */
    fun pointSize(pointSize: Float) {
        this.pointSize = dpToPx(pointSize).toFloat()
        paint!!.strokeWidth = this.pointSize
        selectPaint!!.strokeWidth = this.pointSize
        locationY = 0f
        locationX = 0f
    }

    /**
     * 设置点的距离 dp
     *
     * @param width
     */
    fun setWidth(width: Float) {
        this.width = dpToPx(width).toFloat()
        locationX = 0f
    }
    /**
     * 供外部调用的方法
     *
     * @param delayMillis 轮播时间间隔
     */
    /**
     * 用户调用开始轮播的方法 时间默认 2.5秒
     */
    @JvmOverloads
    fun start(delayMillis: Long = 2500) {
        if (!isLoop) {
            return
        }
        this.delayMillis = delayMillis
        if (isTuning) {
            return
        }
        isTuning = true
        if (mEnabled) {
            postDelayed(task, delayMillis)
        }
    }

    /**
     * 供外部调用的方法
     * 停止轮播
     */
    fun stop() {
        if (isTuning) {
            isTuning = false
            removeCallbacks(task)
        }
    }

    private var mEnabled = true
    
    fun mEnabledTuning(mEnabled: Boolean) {
        this.mEnabled = mEnabled
    }

    /**
     * 初始化操作的 添加了一个 viewpager ，初始化了 2 个画笔
     *
     * @param context
     */
    private fun init(context: Context) {
        if (context is AppCompatActivity) {
            context.lifecycle
                .addObserver(LifecycleEventObserver { source, event ->
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        stop()
                    }
                })
        }
        viewPager = ViewPager(context)
        addView(viewPager)
        viewPager!!.addOnPageChangeListener(pageChangeListener)
        width = dpToPx(4f).toFloat()
        pointSize = dpToPx(5f).toFloat()
        marginBottom = dpToPx(12f).toFloat()
        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.strokeWidth = pointSize
        paint!!.strokeCap = Paint.Cap.ROUND
        selectPaint = Paint(paint)
    }

    /**
     * 当页面被用户触摸时，停止轮播
     * 用户离开 重新开始轮播
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            if (mEnabled) {
                start()
            }
        } else {
            stop()
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 动态画点的方法
     *
     * @param canvas
     */
    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (!isLoop) {
            return
        }
        if (locationX == 0f) {
            locationX = getCenterLocationZero(canvas.width / 2)
        }
        if (locationY == 0f) {
            locationY = canvas.height - marginBottom - pointSize / 2
        }
        if (pointCount != 0) {
            val position = viewPager!!.currentItem % pointCount
            for (i in 0 until pointCount) {
                val locationX = locationX + (width + pointSize) * i
                canvas.drawPoint(
                    locationX,
                    locationY,
                    if (position == i) selectPaint!! else paint!!
                )
            }
        }
    }

    /**
     * dp转 px 的方法
     *
     * @param size
     * @return
     */
    private fun dpToPx(size: Float): Int {
        val scale = context!!.resources.displayMetrics.density
        return (scale * size + 0.5).toInt()
    }

    /**
     * 获取第一个点的位置
     *
     * @param centerX
     * @return
     */
    private fun getCenterLocationZero(centerX: Int): Float {
        return centerX - (width + pointSize) * (pointCount - 1) / 2f
    }

    private var viewHeight = 0f
    fun setTransformer(layoutMargin: Int, PageMargin: Int, ScaleY: Int) {
        viewPager!!.offscreenPageLimit = 3
        val layoutParams =
            viewPager!!.layoutParams as LayoutParams
        layoutParams.leftMargin = layoutMargin
        layoutParams.rightMargin = layoutMargin
        viewPager!!.layoutParams = layoutParams
        clipChildren = false
        viewPager!!.clipChildren = false
        viewPager!!.pageMargin = PageMargin
        viewPager!!.setPageTransformer(false) { page, position ->
            if (viewHeight == 0f) {
                viewHeight = viewPager!!.height.toFloat()
            }
            val maxHeight = viewHeight
            val minHeight = maxHeight - ScaleY
            if (position == 0f) {
                page.scaleX = 1f
                page.scaleY = 1f
            } else if (position < 1 && position > -1) {
                val changeHeight = (1 - Math.abs(position)) * ScaleY
                val scale = (changeHeight + minHeight) / maxHeight
                page.scaleX = 1f
                page.scaleY = scale
            } else {
                val scale = minHeight / maxHeight
                page.scaleX = 1f
                page.scaleY = scale
            }
        }
    }
}