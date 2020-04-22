package com.easy.lib.network

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage

/**
 * @author too young
 * @date  2020/4/22 16:16
 */
object EasyStompClient {

    private var wsUrl = "ws://ws.usechain.vip:81/endpointWisely/websocket"
    //String wsUrl = "ws://192.168.0.104:8080/index/websocket";
    //String wsUrl = "ws://121.40.165.18:8800";

    private const val TAG = "aaaaaaaaaaaaa"

    private val mStompClient: StompClient by lazy {
        Stomp.over(Stomp.ConnectionProvider.OKHTTP, wsUrl)
    }
    private var compositeDisposable: CompositeDisposable? = null

    /**
     * 关闭长链接
     *
     * @param view
     */
    fun disconnectStomp() {
        mStompClient.disconnect()
    }

    /**
     * 开启长链接
     *
     */
    fun connectStomp(topic: String = "/topic/marketCategory/all") {

        mStompClient.withClientHeartbeat(1000).withServerHeartbeat(1000)
        resetSubscriptions()

        val dispLifecycle: Disposable = mStompClient.lifecycle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(({ lifecycleEvent: LifecycleEvent ->
                when (lifecycleEvent.type) {
                    LifecycleEvent.Type.OPENED -> log("Stomp connection opened")
                    LifecycleEvent.Type.ERROR -> {
                        Log.e("",
                            "Stomp connection error",
                            lifecycleEvent.exception
                        )
                        log("Stomp connection error")
                    }
                    LifecycleEvent.Type.CLOSED -> {
                        log("Stomp connection closed")
                        resetSubscriptions()
                    }
                    LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> log("Stomp failed server heartbeat")
                }
            }))
        compositeDisposable?.add(dispLifecycle)

        val disposable: Disposable =
            mStompClient.topic(topic)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { topicMessage: StompMessage ->
                        Log.d(TAG, "Received " + topicMessage.payload)
                    },
                    { throwable: Throwable? ->
                        Log.e(TAG, "连接错误", throwable)
                    }
                )
        compositeDisposable?.add(disposable)
        mStompClient.connect()
    }

    private fun log(text: String) {
        Log.d("aaaaaaaaa", "---$text")
    }

    private fun resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable?.dispose()
        }
        compositeDisposable = CompositeDisposable()
    }


}