package com.easy.example.adaapter

import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easy.example.base.BaseCustomViewModel
import com.easy.example.base.BaseViewHolder

class ListAdapter() :
    RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = TextView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150)
        return BaseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val textView = holder.itemView as TextView
        textView.gravity = Gravity.CENTER
        textView.setText("position$position")

    }

}
