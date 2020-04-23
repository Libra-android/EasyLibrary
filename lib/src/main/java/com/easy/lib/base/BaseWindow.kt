package com.easy.lib.base

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleEventObserver
import com.easy.lib.R


/**
 * Created by Pual's PC on 2016/10/10.
 */
abstract class BaseWindow(context: Context) : PopupWindow() {

    protected var activity: Activity

    abstract fun layout(): Int

    init {
        contentView = LayoutInflater.from(context).inflate(layout(), null)
        activity = context as Activity
        val cd = ColorDrawable(0x000000)
        setBackgroundDrawable(cd)
        isOutsideTouchable = true
        isFocusable = true
        animationStyle = R.style.pop_anim
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT

        //在dismiss中恢复透明度
        setOnDismissListener {
            val lp = activity.window.attributes
            lp.alpha = 1f
            activity.window.attributes = lp
        }

        if (context is AppCompatActivity) {
            context.lifecycle.addObserver(LifecycleEventObserver { source, event ->
                when (event) {
                    ON_DESTROY -> dismiss()
                    ON_RESUME -> {
                    }
                    ON_PAUSE -> {
                    }
                    else -> {
                    }
                }
            })
        }

    }


    open fun show(view: View) {
        if (isShowing) {
            return
        }
        //产生背景变暗效果
        val lp = activity.window.attributes
        lp.alpha = 0.4f
        activity.window.attributes = lp
        showAtLocation(view, Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
    }


    fun Int.dp() = toFloat().dp()

    fun Double.dp() = toFloat().dp()

    fun Float.dp() = (this * activity.resources.displayMetrics.density + 0.5f).toInt()


}
