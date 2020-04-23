package com.easy.lib.expand

/**
 * @author too young
 * @date  2020/4/10 14:47
 */
fun Int.forEach(func: () -> Unit) {
    for (a in 0..this) {
        func.invoke()
    }
}

fun Any?.string(): String {
    return when (this) {
        is Float? -> this?.toFloat()?.numberToString() ?: "0"
        is Double? -> this?.toFloat()?.numberToString() ?: "0"
        is Int? -> this?.toFloat()?.numberToString() ?: "0"
        is Boolean? -> this?.toString() ?: "false"
        is Float -> this.numberToString()
        is Double -> this.toFloat().numberToString()
        is Int -> this.toFloat().numberToString()
        else -> this?.toString() ?: ""
    }
}

fun Float.numberToString(): String {
    var string = String.format("%.2f", this)
    if (string[string.length - 1].toString() == "0") {
        string = string.substring(0, string.length - 1)
    }
    if (string[string.length - 1].toString() == "0") {
        string = string.substring(0, string.length - 2)
    }
    return string
}