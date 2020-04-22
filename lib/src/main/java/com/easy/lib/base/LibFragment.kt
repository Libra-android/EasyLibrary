package com.easy.lib.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.easy.lib.permission.PermissionUtils

/**
 * @author too young
 * @date  2020/4/1 16:05
 */
abstract class LibFragment: Fragment() {

    protected lateinit var fragmentView: View

    private var showLifecycle = false

    /**
     * 与Activity通信
     */
    abstract fun create()

    /**
     * 创建View
     * 只会在创建时执行一次
     */
    abstract fun setData()

    /**
     * 获取数据
     */
    abstract fun update()

    abstract fun alwaysUpdate(): Boolean

    /**
     * 设置布局
     */
    abstract fun layout(): Int

    /**
     * 每次不可见
     */
    open fun pause() {

    }

    /**
     * 销毁
     */
    open fun stop() {

    }

    /**
     *可供其他类调用的方法
     */
    open fun parentLister(type: Int) {

    }

    protected var mContext: Context? = null

    private var isUpdate = false
    protected var isFinish = false

    var isCreate = false

    private var logName = "---"

    override fun onCreate(savedInstanceState: Bundle?) {
        isFinish = false
        mContext = activity
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        logName = "--- ${javaClass.simpleName}"

        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

                if (showLifecycle) {
                    Log.d(logName, "${event.name}")
                }

                when (event) {
                    Lifecycle.Event.ON_CREATE -> {
                        isCreate = true
                        isUpdate = false
                        create()
                        setData()
                    }
                    Lifecycle.Event.ON_START -> {

                    }
                    Lifecycle.Event.ON_RESUME -> {
                        if (alwaysUpdate()) {
                            update()
                            Log.d(logName, "update")
                        } else {
                            if (!isUpdate) {
                                update()
                                Log.d(logName, "update")
                                isUpdate = true
                            }
                        }
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        pause()
                    }
                    Lifecycle.Event.ON_STOP -> {
                        stop()
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                        isCreate = false
                    }
                    Lifecycle.Event.ON_ANY -> {

                    }
                }
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = LayoutInflater.from(activity).inflate(layout(), null)
        return fragmentView
    }

    override fun onDestroy() {
        super.onDestroy()
        isFinish = true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    fun <T : Activity> Class<T>.open(bundle: Bundle? = null) {
        val intent = Intent(context, this)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    fun Int.dp() = this.toFloat().dp()

    fun Double.dp() = this.toFloat().dp()

    fun Float.dp() = (this * resources.displayMetrics.density + 0.5f).toInt()


}