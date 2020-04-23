package com.easy.lib.weight.recyclerview

import androidx.recyclerview.widget.RecyclerView

/**
 * @author too young
 * @date  2019/8/15 10:11
 */
class RecyclerHolder<T : RecyclerItemView>(val recyclerItemView: T) :
    RecyclerView.ViewHolder(recyclerItemView.itemView)