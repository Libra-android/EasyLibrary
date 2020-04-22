package com.easy.lib.weight.sidebar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.easy.lib.R
import com.easy.lib.expand.dp
import com.easy.lib.weight.recyclerview.NcRecyclerView
import com.easy.lib.weight.recyclerview.RecyclerType

/**
 * @author too young
 * @date  2020/4/11 10:22
 */
class SideBarView : FrameLayout {

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle)


    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)


    constructor(context: Context) : super(context)

    private var dataList = arrayListOf<SideData>()

    private var sideList = arrayListOf(
        "A", "B", "C", "D", "E", "F", "G", "H", "I",
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
        "W", "X", "Y", "Z", "#"
    )

    private var recyclerView: NcRecyclerView
    private var sideBar: SideBar

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_side_ber, null)
        addView(view)
        recyclerView = view.findViewById(R.id.recyclerView)
        sideBar = view.findViewById(R.id.sideBar)
        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.bindData({ dataList.size }) {
//            RecyclerType({ SideBarHolder(it.context) }) {
//                if (it is SideBarHolder) {
//                    it.bindData(dataList[it.adapterPosition])
//                }
//            }
//        }
        sideBar.setOnStrSelectCallBack { index, selectStr ->
            (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(getPosition(selectStr), 0)
        }

    }

    fun bindData(string: List<String>, isShowAll: Boolean = false) {

        val list = arrayListOf<SideData>()
        if (!isShowAll) {
            sideList.clear()
        }
        string.forEach {
            list.add(SideData(it))
        }
        list.sort()
        list.forEach {
            val sideTitle = SideData(name = it.firstLetter, sideType = SideType.TITLE)
            if (dataList.indexOf(sideTitle) < 0) {
                dataList.add(sideTitle)
                sideList.add(sideTitle.firstLetter)
            }
            dataList.add(it)
        }
        recyclerView.notifyDataSetChanged()
        sideBar.setDataResource(sideList.toTypedArray(), context.dp(25))


    }

    private fun getPosition(name: String) : Int{
        dataList.forEachIndexed { index, sideData ->
            if(sideData.name == name && sideData.sideType == SideType.TITLE){
                return index
            }
        }
        return 0
    }


}