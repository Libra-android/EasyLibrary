package com.easy.lib.dialog

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.sin

/**
 * @author too young
 * @date  2020/4/21 17:42
 */
class LoadView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    private val xx = 0.45f
    private val yy = 0.85F

    private val colors = arrayListOf("#503A4864", "#003A4864", "#503A4864", "#3A4864")
    private var test = 0
    private val lineList = arrayListOf<FloatArray>()

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    init {
        startTask()
    }

    private fun startTask() {
        val mTimer = Timer()
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                if (test == 11) {
                    test = 0
                } else {
                    test++
                }
                invalidate()
            }
        }
        mTimer.schedule(timerTask, 0, 100)
    }

    private val paint by lazy {
        Paint()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        val mWidth = width / 2F
        val sin30 = sin(Math.toRadians(30.0)).toFloat()
        val sin60 = sin(Math.toRadians(60.0)).toFloat()

        if (lineList.isEmpty()) {
            lineList.add(
                floatArrayOf(
                    mWidth + mWidth * xx,
                    mWidth,
                    mWidth  + mWidth * yy,
                    mWidth
                )
            )
            lineList.add(
                floatArrayOf(
                    mWidth + sin60 * mWidth * xx,
                    mWidth + sin30 * mWidth * xx,
                    mWidth + sin60 * mWidth * yy,
                    mWidth + sin30 * mWidth * yy
                )
            )
            lineList.add(
                floatArrayOf(
                    mWidth + sin30 * mWidth * xx,
                    mWidth + sin60 * mWidth * xx,
                    mWidth + sin30 * mWidth * yy,
                    mWidth + sin60 * mWidth * yy
                )
            )
            lineList.add(
                floatArrayOf(
                    mWidth,
                    mWidth + mWidth * xx,
                    mWidth,
                    mWidth + mWidth * yy
                )
            )
            lineList.add(
                floatArrayOf(
                    mWidth - sin30 * mWidth * xx,
                    mWidth + sin60 * mWidth * xx,
                    mWidth - sin30 * mWidth * yy,
                    mWidth + sin60 * mWidth * yy
                )
            )
            lineList.add(
                floatArrayOf(
                    mWidth - sin60 * mWidth * xx,
                    mWidth + sin30 * mWidth * xx,
                    mWidth - sin60 * mWidth * yy,
                    mWidth + sin30 * mWidth * yy
                )
            )
            lineList.add(
                floatArrayOf(
                    mWidth - mWidth * xx,
                    mWidth,
                    mWidth - mWidth * yy,
                    mWidth
                )
            )
            lineList.add(
                floatArrayOf(
                    mWidth - sin60 * mWidth * xx,
                    mWidth - sin30 * mWidth * xx,
                    mWidth - sin60 * mWidth * yy,
                    mWidth - sin30 * mWidth * yy
                )
            )
            lineList.add(
                floatArrayOf(
                    mWidth - sin30 * mWidth * xx,
                    mWidth - sin60 * mWidth * xx,
                    mWidth - sin30 * mWidth * yy,
                    mWidth - sin60 * mWidth * yy
                )
            )
            lineList.add(
                floatArrayOf(
                    mWidth,
                    mWidth - mWidth * xx,
                    mWidth,
                    mWidth - mWidth * yy
                )
            )
            lineList.add(
                floatArrayOf(
                    mWidth + sin30 * mWidth * xx,
                    mWidth - sin60 * mWidth * xx,
                    mWidth + sin30 * mWidth * yy,
                    mWidth - sin60 * mWidth * yy
                )
            )
            lineList.add(
                floatArrayOf(
                    mWidth + sin60 * mWidth * xx,
                    mWidth - sin30 * mWidth * xx,
                    mWidth + sin60 * mWidth * yy,
                    mWidth - sin30 * mWidth * yy
                )
            )
        }

        canvas?.let {
            paint.strokeWidth = 10F
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = Color.parseColor("#FF0000")

            lineList.forEachIndexed { index, floats ->
                paint.color = Color.parseColor(
                    when (index) {
                        test -> colors[0]
                        test + 1 -> colors[1]
                        test + 2 -> colors[2]
                        (test + 1) % 12 -> colors[1]
                        (test + 2) % 12 -> colors[2]
                        else -> colors[3]
                    }
                )
                canvas.drawLine(floats[0], floats[1], floats[2], floats[3], paint)
            }

        }

    }

}