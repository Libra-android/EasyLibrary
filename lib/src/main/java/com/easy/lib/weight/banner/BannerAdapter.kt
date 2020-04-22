package com.easy.lib.weight.banner

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlin.collections.ArrayList

internal class BannerAdapter : PagerAdapter() {
    private val bannerList = ArrayList<Any>()

    private var loopBannerHolder: LoopBannerHolder? = null
    private var DAYTIME = 500
    fun setLoop(loop: Boolean) {
        if (loop) {
            DAYTIME = DAYTIME * 2
        }
    }

    fun <T: Any> setData(data: ArrayList<T>) {
        bannerList.clear()
        bannerList.addAll(data)
    }

    fun createHolder(holder: LoopBannerHolder) {
        loopBannerHolder = holder
    }

    override fun getCount(): Int {
        return bannerList.size * DAYTIME
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view === `object`
    }

    override fun instantiateItem(
        container: ViewGroup,
        position: Int
    ): Any { //得到真实的position
        val realPosition = position % bannerList.size
        //得到复用的View
        val view =
            loopBannerHolder!!.bindView(realPosition, bannerList[realPosition] as ArrayList<*>)
        container.addView(view)
        return view
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        val view = `object` as View
        container.removeView(view)
        BannerHelper.bannerViewMap.set(view,false)

    }

    val realCount: Int
        get() = bannerList.size

}