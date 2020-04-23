package com.easy.lib.weight.sidebar

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import com.easy.lib.weight.recyclerview.RecyclerHolder

/**
 * @author too young
 * @date  2019/8/21 16:21
 */
//class SideBarHolder(context: Context) : RecyclerHolder(SideTextView(context)) {
//
//    init {
//        itemView.layoutParams = androidx.recyclerview.widget.RecyclerView.LayoutParams(
//            androidx.recyclerview.widget.RecyclerView.LayoutParams.MATCH_PARENT,
//            androidx.recyclerview.widget.RecyclerView.LayoutParams.WRAP_CONTENT
//        )
//        (itemView as TextView).gravity = Gravity.START or Gravity.CENTER_VERTICAL
//        //itemView.setBackgroundColor(Color.parseColor("#F7F7F7"))
//    }
//
//    fun bindData(sideData: SideData) {
//        if (itemView is TextView) {
//            itemView.text = sideData.name
//            if (sideData.sideType == SideType.TITLE) {
//                itemView.setTextColor(Color.parseColor("#A8A9C3"))
//                itemView.setPadding(16.dp(), 15.dp(), 0, 15.dp())
//            } else {
//                itemView.setTextColor(Color.parseColor("#FFFFFF"))
//                itemView.setPadding(16.dp(), 18.dp(), 0, 18.dp())
//            }
//            itemView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
//
//        }
//    }
//
//}