package com.easy.lib.expand

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * @author too young
 * @date  2019/6/4 11:07
 */

fun TextView.spanTextSize(string: String, textSize: Int, start: Int, end: Int) {

    val spannableString = SpannableString(string)
    spannableString.setTextSize(textSize, start, end)
    text = spannableString
}

fun TextView.spanTextColor(string: String, textColor: String, start: Int, end: Int) {
    val spannableString = SpannableString(string)
    spannableString.setTextColor(textColor, start, end)
    text = spannableString
}

fun TextView.spanTextColor(string: String, textColor: Int, start: Int, end: Int) {
    val spannableString = SpannableString(string)
    spannableString.setTextColor(context, ContextCompat.getColor(context, textColor), start, end)
    text = spannableString
}

fun SpannableString.setTextColor(context: Context, textColor: Int, start: Int, end: Int) {
    setSpan(
        ForegroundColorSpan(ContextCompat.getColor(context, textColor)),
        start,
        end,
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

fun SpannableString.setTextColor(textColor: String, start: Int, end: Int) {
    setSpan(ForegroundColorSpan(Color.parseColor(textColor)), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun SpannableString.setTextSize(textSize: Int, start: Int, end: Int) {
    setSpan(AbsoluteSizeSpan(textSize, true), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun SpannableString.setStyleSpan(typeface: Int, start: Int, end: Int) {
    setSpan(StyleSpan(typeface), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun TextView.setSpanString(string: String, function: (SpannableString) -> Unit) {
    val spannableString = SpannableString(string)
    function.invoke(spannableString)
    text = spannableString
}