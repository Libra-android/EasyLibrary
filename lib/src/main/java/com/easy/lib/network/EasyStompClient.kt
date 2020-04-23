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

    private const val TAG = "EasyStompClient"

    private val mStompClient: StompClient by lazy {
        Stomp.over(Stomp.ConnectionProvider.OKHTTP, wsUrl)
    }

    private var showLog = false

    //Application 给 websocket url 赋值
    fun initClient(url: String, showLog: Boolean) {
        wsUrl = url
        this.showLog = showLog
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
    fun connectStomp(
        /**
         * message 传空字符传代表只订阅，不发消息
         */
        clientHeartBeat: Int = 10000,
        serverHeartbeat: Int = 10000
    ) {

        mStompClient.withClientHeartbeat(clientHeartBeat).withServerHeartbeat(serverHeartbeat)
        resetSubscriptions()

        val disposableLifecycle: Disposable = mStompClient.lifecycle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(({ lifecycleEvent: LifecycleEvent ->
                when (lifecycleEvent.type) {
                    LifecycleEvent.Type.OPENED -> log("Stomp connection opened")
                    LifecycleEvent.Type.ERROR -> {
                        log("Stomp connection error" + lifecycleEvent.exception)
                        log("Stomp connection error")
                    }
                    LifecycleEvent.Type.CLOSED -> {
                        log("Stomp connection closed")
                        resetSubscriptions()
                    }
                    LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> log("Stomp failed server heartbeat")
                }
            }))
        compositeDisposable?.add(disposableLifecycle)
        connect()
    }

    fun topic(
        topic: String,
        onMessage: (WebSocketData) -> Unit
    ) {
        val topicMessage: Disposable =
            mStompClient.topic(topic)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { topicMessage: StompMessage ->
                        log("topicMessage $topicMessage")
                        onMessage.invoke(WebSocketData(topicMessage, null))
                    },
                    { throwable: Throwable? ->
                        log("连接错误 $throwable")
                        onMessage.invoke(WebSocketData(null, throwable))
                    }
                )
        compositeDisposable?.add(topicMessage)
    }

    fun sendMessage(message: String) {
        if (message.isNotEmpty()) {
            val message = mStompClient.send(message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
            compositeDisposable?.add(message)
        }
    }

    private fun connect() {
        mStompClient.connect()
    }

    private fun log(text: String) {
        if (showLog) {
            Log.d(TAG, "---$text")
        }
    }

    private fun resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable?.dispose()
        }
        compositeDisposable = CompositeDisposable()
    }


}