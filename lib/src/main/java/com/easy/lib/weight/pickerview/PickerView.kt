package com.easy.lib.weight.pickerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easy.lib.R
import com.easy.lib.expand.forEach
import com.easy.lib.weight.recyclerview.BaseRecyclerView
import com.easy.lib.weight.recyclerview.RecyclerType
import kotlin.math.abs
import kotlin.math.min

/**
 * @author too young
 * @date  2020/4/10 14:12
 */
class PickerView : BaseRecyclerView {
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

    private val pickerCount = 5
    private val realPosition = ((pickerCount - 1) / 2)
    private var recyclerHeight = 0

    private val oldList = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    private val list = arrayListOf<Any>()

    private val linearLayoutManager by lazy {
        LinearLayoutManager(context)
    }


    init {
        pickerCount.forEach {
            list.addAll(oldList)
        }
        layoutManager = linearLayoutManager
//        bindData({ list.size }) {
//            RecyclerType({ PickerHolder(it) }) {
//                if (it is PickerHolder) {
//                    it.bindData(list[it.adapterPosition].toString())
//                }
//            }
//        }

        scrollToPosition((oldList.size * realPosition) - realPosition)
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val firstPosition = linearLayoutManager.findFirstVisibleItemPosition()
                val view = linearLayoutManager.findViewByPosition(firstPosition)

                if(view?.top == 0){
                    return
                }

                recyclerHeight = recyclerView.height
                if (newState == SCROLL_STATE_IDLE) {
                    val firstPosition = linearLayoutManager.findFirstVisibleItemPosition()
                    val lastPosition = linearLayoutManager.findLastVisibleItemPosition()
                    var minH = 10000F
                    val arrayH = arrayListOf<Float>()
                    for (index in firstPosition..lastPosition) {
                        val view = linearLayoutManager.findViewByPosition(index)
                        view?.let {

                            view.rotationX = 45F

                            val viewY = (view?.height / 2) + view.y
                            val viewScrollY = viewY - recyclerHeight / 2
                            arrayH.add(viewScrollY)
                            val viewScrollYAbs = abs(viewScrollY)
                            minH = min(minH, viewScrollYAbs)
                        }
                    }
                    arrayH.forEachIndexed { index, it ->
                        if (abs(it) == minH && minH != 0F) {
                            smoothScrollBy(0, it.toInt())

                            val p = (firstPosition + index) % oldList.size
                            val toP = (oldList.size * realPosition) - realPosition + p

                            Log.d("aaaaaaa", "$toP")

                            //scrollToPosition(toP)

                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val firstPosition = linearLayoutManager.findFirstVisibleItemPosition()
                val lastPosition = linearLayoutManager.findLastVisibleItemPosition()

                for (index in firstPosition..lastPosition) {
                    val view = linearLayoutManager.findViewByPosition(index)
                    view?.let {
                        val viewY = (view?.height / 2) + view.y
                        val viewScrollY = viewY - recyclerHeight / 2
                        val viewScrollYAbs = abs(viewScrollY)

                        Log.d("aaaaaa", "$viewScrollYAbs ----")
                        val textView = view.findViewById<TextView>(R.id.textView)
                        if (viewScrollYAbs < view.height / 3) {
                            textView.setTextColor(Color.parseColor("#FF0000"))
                        } else {
                            textView.setTextColor(Color.parseColor("#FFFFFF"))
                        }

                    }
                }


            }
        })

    }


    override fun onDraw(c: Canvas?) {
        super.onDraw(c)

    }


}