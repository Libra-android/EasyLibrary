package com.easy.lib.weight.recyclerview

/**
 * @author too young
 * @date  2019/6/28 14:21
 */
data class RecyclerType(
    val count: () -> Int,
    val viewType: ((Int) -> Int)? = null,
    val viewTypeToItemView: (Int) -> RecyclerItemView,
    val itemView: (RecyclerItemView) -> Unit
)

