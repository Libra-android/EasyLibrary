package com.easy.example;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * @author too young
 * @date 2020/4/21 15:41
 */
public class Test {

    String wsUrl = "ws://ws.usechain.vip:81/endpointWisely/websocket";
    //新建client
    OkHttpClient client = new OkHttpClient.Builder()
            .build();

    //构造request对象
    Request request = new Request.Builder()
            .url(wsUrl)
            .build();

    private WebSocket mWebSocket;
    private int msgCount;

    //每秒发送一条消息
    public void startTask(){
        Timer mTimer= new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(mWebSocket == null) return;
                msgCount++;
                boolean isSuccessed = mWebSocket.send("msg" + msgCount + "-" + System.currentTimeMillis());
                Log.d("aaaaaa", isSuccessed + "---");
                //除了文本内容外，还可以将如图像，声音，视频等内容转为ByteString发送
                //boolean send(ByteString bytes);
            }
        };
        mTimer.schedule(timerTask, 0, 1000);
    }

    public void initClient() {

        client.newWebSocket(request, new WebSocketListener() {

            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                mWebSocket = webSocket;
                Log.d("aaaaaa", "");
                Log.d("aaaaaa","client onOpen");
                Log.d("aaaaaa","client request header:" + response.request().headers());
                Log.d("aaaaaa","client response header:" + response.headers());
                Log.d("aaaaaa","client response:" + response);
                //开启消息定时发送
                startTask();
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.d("aaaaaa","client onMessage");
                Log.d("aaaaaa","message:" + text);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                Log.d("aaaaaa","client onClosing");
                Log.d("aaaaaa","code:" + code + " reason:" + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                Log.d("aaaaaa","client onClosed");
                Log.d("aaaaaa","code:" + code + " reason:" + reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                //出现异常会进入此回调
                Log.d("aaaaaa","client onFailure");
                Log.d("aaaaaa","throwable:" + t);
                Log.d("aaaaaa","response:" + response);
            }
        });

    }



}
