package com.easy.example;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @author too young
 * @date 2020/4/21 15:41
 */
public class Test {

    String wsUrl = "ws://ws.usechain.vip:81/endpointWisely/websocket";
    //String wsUrl = "ws://192.168.0.104:8080/index/websocket";
    //String wsUrl = "ws://121.40.165.18:8800";

    private static final String TAG = "aaaaaaaaaaaaa";

    private Timer timer = new Timer();
    private WebSocket mWebSocket;

    public void start(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d("aaaaaaaa", "----" + mWebSocket.send("/findListByMarketId/38"));
            }
        },2000,5000);
    }

    public void initClient() {

        new Gson().fromJson("", new TypeToken<List<String>>(){}.getType());

        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(wsUrl)
                .build();

        client.newWebSocket(request, new WebSocketListener() {

            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                mWebSocket = webSocket;
                Log.e("aaaaaawebsocket", "服务器连接成功");
                //开启消息定时发送

                start();
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.d("aaaaaaonMessage", "client onMessage");
                Log.d("aaaaaaonMessage", "message:" + text);
                //list.add(text + "\n");
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                Log.d("aaaaaaonMessage", "client onMessage");
                Log.d("aaaaaaonMessage", "message:" + bytes);

            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                Log.d("aaaaaa", "client onClosing");
                Log.d("aaaaaa", "code:" + code + " reason:" + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                Log.d("aaaaaa", "client onClosed");
                Log.d("aaaaaa", "code:" + code + " reason:" + reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                //出现异常会进入此回调
                Log.d("aaaaaa", "client onFailure");
                Log.d("aaaaaa", "throwable:" + t);
                Log.d("aaaaaa", "response:" + response);
            }
        });

    }


}
