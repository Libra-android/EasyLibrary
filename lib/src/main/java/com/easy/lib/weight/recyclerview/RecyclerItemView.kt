package com.easy.lib.weight.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author too young
 * @date  2020/4/13 9:41
 */
abstract class RecyclerItemView(private val layoutId: Int) {

    fun newInstance(parent: ViewGroup): RecyclerItemView {
        itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return this
    }

    constructor() : this(0)

    open var adapterPosition: Int = 0

    open lateinit var itemView: View

    abstract class ItemView(view: View, override var itemView: View = view) : RecyclerItemView()

    fun Int.dp() = this.toFloat().dp()

    fun Double.dp() = this.toFloat().dp()

    fun Float.dp() = (this * itemView.context.resources.displayMetrics.density + 0.5f).toInt()

}