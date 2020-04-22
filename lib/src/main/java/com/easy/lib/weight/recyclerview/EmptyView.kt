package com.easy.lib.weight.recyclerview

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.easy.lib.R
import com.easy.lib.utils.NetworkUtils
import com.easy.lib.weight.emptylayout.EmptyData
import com.easy.lib.weight.emptylayout.EmptyType
import kotlinx.android.synthetic.main.layout_empty.view.*

/**
 * @author too young
 * @date  2019/8/21 16:21
 */
class EmptyView : RecyclerItemView(R.layout.layout_empty) {

    private var emptyFunction: ((EmptyView) -> Unit)? =null

    /**
     * 其他所有方法需要在此方法内部调用才有效
     */
    fun bindData(emptyFunction: ((EmptyView) -> Unit)?) {
        this.emptyFunction = emptyFunction
        this.emptyFunction?.invoke(this)
    }

    fun setViewHeight(height: Int, paddingBottom: Int){
        itemView.emptyView.layoutParams.height = height
        itemView.emptyView.setPadding(0,0,0,paddingBottom)
    }

    fun setEmptyData(emptyData: EmptyData) {

        Log.d("aaaaaaa", emptyData.emptyType.name )

        itemView.apply {
            when (emptyData.emptyType) {
                EmptyType.HIDE_LAYOUT -> {
                    emptyView.visibility = View.GONE
                }
                EmptyType.LOADING -> {

                    emptyView.visibility = View.VISIBLE
                    emptyImage.visibility = View.GONE
                    emptyProgressBar.visibility = View.VISIBLE
                    emptyText.text = emptyData.textLoading

                }
                EmptyType.NETWORK_ERROR -> {

                    emptyView.visibility = View.VISIBLE
                    emptyImage.visibility = View.VISIBLE
                    emptyProgressBar.visibility = View.GONE
                    if (NetworkUtils.isConnectInternet(itemView.context)) {
                        emptyImage.setImageResource(emptyData.imgErrorRefresh)
                        emptyText.text = emptyData.textErrorRefresh
                    } else {
                        emptyImage.setImageResource(emptyData.imgErrorNetwork)
                        emptyText.text = emptyData.textErrorNetwork
                    }
                    emptyImage.isEnabled = true

                }
                EmptyType.NO_DATA -> {

                    emptyView.visibility = View.VISIBLE
                    emptyImage.visibility = View.VISIBLE
                    emptyProgressBar.visibility = View.GONE
                    emptyImage.setImageResource(emptyData.imgNoData)
                    emptyText.text = emptyData.textNoData
                    emptyImage.isEnabled = true

                }
                EmptyType.NODATA_ENABLE_CLICK -> {

                    emptyView.visibility = View.VISIBLE
                    emptyImage.visibility = View.VISIBLE
                    emptyProgressBar.visibility = View.GONE
                    emptyImage.setImageResource(emptyData.imgNoData)
                    emptyText.text = emptyData.textNoData
                    emptyImage.isEnabled = false

                }
            }
        }

    }

    fun setText(func : (TextView) -> Unit){
        func.invoke(itemView.emptyText)
    }

    fun setImage(func : (ImageView) -> Unit){
        func.invoke(itemView.emptyImage)
    }



}