package com.easy.lib.weight.recyclerview

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author too young
 * @date  2019/8/1 16:32
 */
open class BaseRecyclerView(context: Context, attrs: AttributeSet?, defStyle: Int) :
    RecyclerView(context, attrs, defStyle) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private var recyclerAdapter: RecyclerAdapter? = null

    fun notifyDataSetChanged() {
        recyclerAdapter?.notifyDataSetChanged()
    }

    fun notifyItemChanged(position: Int) {
        recyclerAdapter?.notifyItemChanged(position)
    }

    private var hasAdapter = false

    fun bindData(recyclerType: RecyclerType) {
        if (hasAdapter) {
            notifyDataSetChanged()
        } else {
            hasAdapter = true
            recyclerAdapter = RecyclerAdapter(recyclerType)
            setHasFixedSize(true)
            recyclerAdapter?.setHasStableIds(true)
            adapter = recyclerAdapter

        }
    }

    fun setLoadMoreText(text: String) {
        if (text.isNotEmpty()) {
            recyclerAdapter?.isShowLoadMore = true
            recyclerAdapter?.function = loadMore
        }
        recyclerAdapter?.setLoadMoreText(text)
    }

    private var isCanLoadMore = false
    private var lastVisibleItem = 0

    private var loadMore = {

    }

    fun setEmptyFunction(emptyFunction: (EmptyView) -> Unit) {
        recyclerAdapter?.setEmptyFunction(emptyFunction)
    }

    /**
     * recyclerView 滚到底部时触发
     */
    fun setLoadMore(loadMore: () -> Unit) {
        this.loadMore = loadMore
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == recyclerView.adapter!!.itemCount && isCanLoadMore
                ) {
                    if (recyclerAdapter?.isShowLoadMore == false) {
                        loadMore.invoke()
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager
                if (layoutManager is LinearLayoutManager) {
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                } else if (layoutManager is GridLayoutManager) {
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                }
                isCanLoadMore = dy > 0

            }
        })

    }


    /**
     * 设置Item 间隔
     */
    fun setDecoration(lineHeight: Int) {

        addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val index = parent.getChildAdapterPosition(view)

                outRect.top = if (index == 0) 0 else lineHeight

            }
        })

    }

    /**
     * 设置Item 间隔
     */
    fun addDecoration(func: (Int, Rect) -> Unit) {

        addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val index = parent.getChildAdapterPosition(view)
                func.invoke(index, outRect)
            }
        })

    }

}