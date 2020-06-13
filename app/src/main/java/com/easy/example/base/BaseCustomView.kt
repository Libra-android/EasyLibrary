package com.easy.example.base

//import android.content.Context
//import android.util.AttributeSet
//import android.view.LayoutInflater
//import android.widget.LinearLayout
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.ViewDataBinding
//
//abstract class BaseCustomView<D : ViewDataBinding, V : BaseCustomViewModel> : LinearLayout,
//    ICustomView<V> {
//
//    constructor(context: Context, attrs: AttributeSet?,layoutId: Int) : super(context, attrs, 0) {
//        initView(layoutId)
//    }
//
//    constructor(context: Context,layoutId: Int) : super(context, null) {
//        initView(layoutId)
//    }
//
//    protected var dataBinding: D? = null
//    private var viewModel: V? = null
//
//    private fun initView(layoutId: Int) {
//
//        val layoutInflater =
//            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//
//        dataBinding = DataBindingUtil.inflate(layoutInflater, layoutId, this, false)
//        dataBinding?.apply {
//            root.setOnClickListener {
//            }
//
//        }
//        if(dataBinding != null){
//            addView(dataBinding!!.root)
//        }
//
//    }
//
//    override fun setData(data: V) {
//
//        viewModel = data
//        dataBinding?.apply {
//            setDataToView(data)
//            //executePendingBindings()
//        }
//
//    }
//
//    abstract fun setDataToView(viewModel: V)
//
//    //abstract fun getLayoutId(): Int
//
//}
