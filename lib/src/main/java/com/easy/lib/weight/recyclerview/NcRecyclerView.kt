package com.easy.lib.weight.recyclerview

import android.content.Context
import android.util.AttributeSet

/**
 * @author too young
 * @date 2019/8/13 15:45
 */
class NcRecyclerView : BaseRecyclerView {
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
    }

    constructor(context: Context) : super(context) {}

}