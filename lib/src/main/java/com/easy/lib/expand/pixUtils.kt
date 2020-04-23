package com.easy.lib.expand

import android.content.Context
import android.view.View

/**
 * @author too young
 * @date  2019/7/5 10:57
 */

/**
 * 将px值转换为dip或dp值，保证尺寸大小不变          *           * @param pxValue          * @param scale          *            （DisplayMetrics类中属性density）          * @return
 */
fun Context.pxTodp(pxValue: Int): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun View.dp(pxValue: Int) = context.dp(pxValue)

/**
 * 将dip或dp值转换为px值，保证尺寸大小不变          *           * @param dipValue          * @param scale          *            （DisplayMetrics类中属性density）          * @return
 */
fun Context.dp(dipValue: Int): Int {
    val scale = resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

/**
 * 将px值转换为sp值，保证文字大小不变          *           * @param pxValue          * @param fontScale          *            （DisplayMetrics类中属性scaledDensity）          * @return
 */
fun Context.pxTosp(pxValue: Int): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 将sp值转换为px值，保证文字大小不变          *           * @param spValue          * @param fontScale          *            （DisplayMetrics类中属性scaledDensity）          * @return
 */
fun Context.sp( spValue: Int): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

