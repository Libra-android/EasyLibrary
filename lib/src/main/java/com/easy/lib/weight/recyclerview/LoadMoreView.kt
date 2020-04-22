package com.easy.lib.weight.recyclerview

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author too young
 * @date  2019/8/21 16:21
 */
class LoadMoreView(val context: Context) : RecyclerItemView.ItemView(TextView(context)) {

    init {
        itemView.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        (itemView as TextView).gravity = Gravity.CENTER
        itemView.setPadding(0, 30, 0, 30)
        itemView.setBackgroundColor(Color.parseColor("#F7F7F7"))
    }

    fun bind(text: String,loadMore: (() -> Unit)) {
        this.loadMore = loadMore
        if (itemView is TextView) {
            (itemView as TextView).text = text
            loadMore.invoke()
        }

    }

    private var loadMore: () -> Unit = {

    }


}