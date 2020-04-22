package com.easy.lib.weight.emptylayout

import android.view.View
import com.easy.lib.R

/**
 * @author too young
 * @date  2020/4/10 10:07
 */
data class EmptyData(
    var emptyType: EmptyType = EmptyType.NO_DATA,
    var textNoData: String = "暂无内容",
    var textLoading: String = "加载中…",
    var textErrorRefresh: String = "内容加载失败\n点击重新加载",
    var textErrorNetwork: String = "没有可用的网络",
    var imgNoData: Int = R.drawable.nondata,
    var imgErrorRefresh: Int = R.drawable.pagefailed_bg,
    var imgErrorNetwork: Int = R.drawable.page_icon_network,
    val click: (View) -> Unit = {}
)
