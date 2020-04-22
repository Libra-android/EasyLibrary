package com.easy.lib.weight.recyclerview

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author too young
 * @date  2019/8/15 10:14
 */
class RecyclerAdapter(private val recyclerType: RecyclerType) :
    RecyclerView.Adapter<RecyclerHolder<RecyclerItemView>>() {

    private val isShowLog = null
    private var count = { 0 }
    var isShowLoadMore = false
    private var text = "正在加载..."

    private val EMPTY_TYPE = -0x111
    private val LOAD_MORE_TYPE = -0x222

    init {
        this.count = recyclerType.count
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerHolder<RecyclerItemView> {
        return when (viewType) {
            LOAD_MORE_TYPE -> RecyclerHolder(LoadMoreView(parent.context))
            EMPTY_TYPE -> RecyclerHolder(EmptyView().newInstance(parent))
            else -> {
                RecyclerHolder(recyclerType.viewTypeToItemView.invoke(viewType).newInstance(parent))
            }

        }
    }

    override fun getItemCount(): Int {
        val itemNum = count.invoke() +
                if (isShowLoadMore && count.invoke() != 0) 1 else 0 +
                        if (emptyFunction != null && count.invoke() == 0) 1 else 0
        isShowLog?.let {
            Log.d("aaaaaaa  getItemCount", "$itemNum -----------")
            Log.d(
                "aaaaaaaaaa",
                "emptyFunction  ${if (emptyFunction == null) "is" else "isNot"} null"
            )
        }
        return itemNum
    }

    override fun onBindViewHolder(recyclerHolder: RecyclerHolder<RecyclerItemView>, position: Int) {

        isShowLog?.let {
            Log.d("aaaa onBindViewHolder", "$position -----------")
        }
        recyclerHolder.recyclerItemView.adapterPosition = position

        when (recyclerHolder.recyclerItemView) {
            is LoadMoreView -> {
                recyclerHolder.recyclerItemView.bind(text, function)
            }
            is EmptyView -> {
                recyclerHolder.recyclerItemView.bindData(emptyFunction)
            }
            else -> {
                recyclerType.itemView.invoke(recyclerHolder.recyclerItemView)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val itemViewType = if (emptyFunction != null && count.invoke() == 0) {
            EMPTY_TYPE
        } else if (isShowLoadMore && position + 1 == itemCount && count.invoke() != 0) {
            LOAD_MORE_TYPE
        } else {
            recyclerType.viewType?.invoke(position) ?: -11
        }
        isShowLog?.let {
            Log.d("aaa itemViewType", "$itemViewType -----------")
        }
        return itemViewType
    }

    private var emptyFunction: ((EmptyView) -> Unit)? = null

    fun setEmptyFunction(emptyFunction: (EmptyView) -> Unit) {
        this.emptyFunction = emptyFunction
    }

    var function: () -> Unit = {

    }

    override fun getItemId(position: Int): Long {
        super.getItemId(position)
        return position.toLong()
    }

    fun setLoadMoreText(text: String) {
        this.text = text
    }

}