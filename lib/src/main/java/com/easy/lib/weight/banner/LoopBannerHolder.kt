package com.easy.lib.weight.banner

import android.view.View

/**
 * @author liu
 * @date 2017/11/8
 */
abstract class LoopBannerHolder<T> {
    abstract fun createView(): View
    abstract fun bindData(itemView: View?, position: Int, data: T)
    fun bindView(position: Int, data: T): View {
        val itemView = view
        bindData(itemView, position, data)
        return itemView
    }

    private val view: View
        private get() {
            val viewMap =
                BannerHelper.bannerViewMap
            for (key in viewMap.keys) {
                if (viewMap[key] == false) {
                    viewMap[key] = true
                    return key
                }
            }
            val view = createView()
            viewMap[view] = true
            return view
        }
}