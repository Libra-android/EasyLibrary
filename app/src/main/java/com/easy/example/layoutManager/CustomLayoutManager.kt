package com.easy.example.layoutManager

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class CustomLayoutManager : RecyclerView.LayoutManager(){

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        if(state?.itemCount == 0){
            recycler?.let {
                removeAndRecycleAllViews(recycler)
            }
            return
        }
        recycler?.let {
            detachAndScrapAttachedViews(recycler)
        }
        state?.let {
            recycler?.let { recycler ->
                repeat(state.itemCount){position ->
                    val view = recycler.getViewForPosition(position)
                    addView(view)

                    measureChild(view,0,0)

                    val width = getDecoratedMeasuredWidth(view)
                    val height = getDecoratedMeasuredHeight(view)

                    layoutDecoratedWithMargins(view,0,height*(position),width,height*(position+1))

                }

            }
        }

    }

    private var mdy = 0

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {

        val lastOff = mdy

        mdy = mdy +dy


        if(mdy < 0){
            mdy = 0
            Log.d("aaaaaaa", mdy.toString())
        }else if(mdy > 100 * 130){
            Log.d("aaaaaaa", mdy.toString())
            mdy = 100*130
        }
        Log.d("aaaaaaa", "$mdy  -- $lastOff")
        recycler?.let {
            detachAndScrapAttachedViews(recycler)
        }

        state?.let {
            recycler?.let { recycler ->
                repeat(state.itemCount){position ->
                    val view = recycler.getViewForPosition(position)
                    addView(view)

                    measureChild(view,0,0)

                    val width = getDecoratedMeasuredWidth(view)
                    val height = getDecoratedMeasuredHeight(view)

                    layoutDecoratedWithMargins(view,0,-mdy+ height*(position),width,-mdy+height*(position+1))

                }

            }
        }
        return if(lastOff==mdy) 0 else dy
    }


    //View 的 layout 方法来进行布局，
    //
    //但在 LayoutManager 里对 Item 进行布局时，也是不推荐直接使用 layout 方法，建议使用：
    //
    //    layoutDecorated(View child, int left, int top, int right, int bottom)
    //
    //    layoutDecoratedWithMargins(View child, int left, int top, int right, int bottom)
    //这两个方法也是 LayoutManager 提供的，我们使用 layoutDecorated 方法的话，它会给ItemDecorations 腾出位置，来看下源码就明白了：


    //    getDecoratedMeasuredWidth(View child)
    //    getDecoratedMeasuredHeight(View child)
    //这两个方法是 LayoutManager 提供的，其实它们内部也是会调用
    // child 的getMeasuredWidth 或 getMeasuredHeight 的，
    // 只是在返回的时候，会考虑到 Decorations 的大小，
    // 并根据 Decorations 的尺寸对应的放大一点，
    // 所以如果我们有设置ItemDecorations 的话，
    // 用这两个方法得到的尺寸往往会比直接调用 getMeasuredWidth 或getMeasuredHeight 方法大就是这个原因了。看下源码：



}
