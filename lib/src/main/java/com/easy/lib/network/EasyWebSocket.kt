package com.easy.lib.network

import okhttp3.*

/**
 * @author too young
 * @date  2020/4/22 12:51
 */
object EasyWebSocket {

    //String wsUrl = "ws://121.40.165.18:8800";

    private var httpClient = OkHttpClient.Builder()
        .build()

    //构造request对象
//    var request: Request = Request.Builder()
//        .url(wsUrl)
//        .build()

    fun createWebSocket(){
        //request.url()
    }


    private val webSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
        }


    }




}