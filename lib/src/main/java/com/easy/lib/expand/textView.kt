package com.easy.lib.expand

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * View 的常用拓展方法
 * @author too young
 * @date  2019/5/31 15:23
 */

fun TextView.setDrawableTop(resId: Int? = null, drawable: Drawable? = null) {
    val d = drawable ?: if (resId != null) context.drawable(resId) else null
    d.setBounds()
    setCompoundDrawables(null, d, null, null)
}

fun TextView.setDrawableBottom(resId: Int? = null, drawable: Drawable? = null) {
    val d = drawable ?: if (resId != null) context.drawable(resId) else null
    d.setBounds()
    setCompoundDrawables(null, null, null, d)
}

fun TextView.setDrawableLeft(resId: Int? = null, drawable: Drawable? = null) {
    val d = drawable ?: if (resId != null) context.drawable(resId) else null
    d.setBounds()
    setCompoundDrawables(d, null, null, null)
}

fun TextView.setDrawableRight(resId: Int? = null, drawable: Drawable? = null) {
    val d = drawable ?: if (resId != null) context.drawable(resId) else null
    d.setBounds()
    setCompoundDrawables(null, null, d, null)
}

fun TextView.setDrawableTop(viewStatus: ViewStatus, array: IntArray) {
    val drawable = context.getStateListDrawable(viewStatus, array)
    drawable.setBounds()
    setCompoundDrawables(null, drawable, null, null)
}

fun TextView.setDrawableBottom(viewStatus: ViewStatus, vararg array: Int) {
    val drawable = context.getStateListDrawable(viewStatus, array)
    drawable.setBounds()
    setCompoundDrawables(null, null, null, drawable)
}

fun TextView.setDrawableLeft(viewStatus: ViewStatus, vararg array: Int) {
    val drawable = context.getStateListDrawable(viewStatus, array)
    drawable.setBounds()
    setCompoundDrawables(drawable, null, null, null)
}

fun TextView.setDrawableRight(viewStatus: ViewStatus, vararg array: Int) {
    val drawable = context.getStateListDrawable(viewStatus, array)
    drawable.setBounds()
    setCompoundDrawables(null, null, drawable, null)
}

fun TextView.setTextColor(viewStatus: ViewStatus, vararg colors: Int) {
    val state = viewStatus.getState()
    val color = ColorStateList(state, colors)
    setTextColor(color)
}


private fun Context.drawables(array: IntArray): ArrayList<Drawable> {
    var drawables = ArrayList<Drawable>()
    array.forEach {
        drawable(it)?.let { it1 ->
            it1.setBounds()
            drawables.add(it1)
        }
    }
    return drawables
}

private fun Context.drawable(array: Int): Drawable? = ContextCompat.getDrawable(this, array)

fun Context.createDrawable(
    color: Any = "#00000000",
    radius: Int = 90,
    strokeColor: Any = "#FF0000",
    strokeWidth: Int = 0, func: ((GradientDrawable) -> Unit)? = null
): Drawable? {


    val drawable = GradientDrawable()
    if (func != null) {
        func.invoke(drawable)
    } else {
        if (color is String) {
            drawable.setColor(Color.parseColor(color))
        } else if (color is Int) {
            drawable.setColor(ContextCompat.getColor(this, color))
        }
        drawable.cornerRadius = dp(radius).toFloat()
        if (strokeColor is String) {
            drawable.setStroke(strokeWidth, Color.parseColor(strokeColor))
        } else if (strokeColor is Int) {
            drawable.setStroke(strokeWidth, resources.getColor(strokeColor))
        }
    }
    return drawable

}

enum class ViewStatus {
    SELECT
}

/** 不同状态的drawable */
fun Context.getStateListDrawable(viewStatus: ViewStatus, array: IntArray): StateListDrawable {

    val drawables = this.drawables(array)

    val state = viewStatus.getState()

    if (drawables.size != state.size) {
        throw Exception("state size 不等于 drawables size")
    }

    var list = StateListDrawable()
    state.forEachIndexed { index, ints ->
        list.addState(ints, drawables[index])
    }
    return list
}

fun TextView.getColor(color: Int) = ContextCompat.getColor(context, color)

fun Drawable?.setBounds() {
    this?.setBounds(0, 0, minimumWidth, minimumHeight)
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    })
}

fun Any.toRequestBody(): RequestBody =
    RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), Gson().toJson(this))


private fun ViewStatus.getState() =
    when (this) {
        ViewStatus.SELECT ->
            arrayOf(
                intArrayOf(android.R.attr.state_selected),
                intArrayOf(-android.R.attr.state_selected)
            )

    }


