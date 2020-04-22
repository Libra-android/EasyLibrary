package com.easy.example;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompMessage;

/**
 * @author too young
 * @date 2020/4/21 15:41
 */
public class Test {

    String wsUrl = "ws://ws.usechain.vip:81/endpointWisely/websocket";
    //String wsUrl = "ws://192.168.0.104:8080/index/websocket";
    //String wsUrl = "ws://121.40.165.18:8800";

    private static final String TAG = "aaaaaaaaaaaaa";

    private StompClient mStompClient;
    private CompositeDisposable compositeDisposable;
    private Gson mGson = new GsonBuilder().create();

    public void initClient(final TextView textView) {

        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, wsUrl);
        resetSubscriptions();


//        client.newWebSocket(request, new WebSocketListener() {
//
//            @Override
//            public void onOpen(WebSocket webSocket, Response response) {
//                mWebSocket = webSocket;
//                Log.e("aaaaaawebsocket", "服务器连接成功");
//                //开启消息定时发送
//                startTask();
//                textView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.setText("服务器连接成功");
//                    }
//                });
//            }
//
//            @Override
//            public void onMessage(WebSocket webSocket, String text) {
//                Log.d("aaaaaaonMessage", "client onMessage");
//                Log.d("aaaaaaonMessage", "message:" + text);
//                list.add(text + "\n");
//                textView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.setText(Arrays.toString(list.toArray()));
//                    }
//                });
//            }
//
//            @Override
//            public void onMessage(WebSocket webSocket, ByteString bytes) {
//                super.onMessage(webSocket, bytes);
//                Log.d("aaaaaaonMessage", "client onMessage");
//                Log.d("aaaaaaonMessage", "message:" + bytes);
//
//            }
//
//            @Override
//            public void onClosing(WebSocket webSocket, int code, String reason) {
//                Log.d("aaaaaa", "client onClosing");
//                Log.d("aaaaaa", "code:" + code + " reason:" + reason);
//            }
//
//            @Override
//            public void onClosed(WebSocket webSocket, int code, String reason) {
//                Log.d("aaaaaa", "client onClosed");
//                Log.d("aaaaaa", "code:" + code + " reason:" + reason);
//                textView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.setText("服务器连接关闭");
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
//                //出现异常会进入此回调
//                Log.d("aaaaaa", "client onFailure");
//                Log.d("aaaaaa", "throwable:" + t);
//                Log.d("aaaaaa", "response:" + response);
//                textView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.setText("发生错误");
//                    }
//                });
//            }
//        });

    }

    /**
     * 关闭长链接
     *
     * @param view
     */
    public void disconnectStomp(View view) {
        mStompClient.disconnect();
    }

    /**
     * 开启长链接
     *
     */
    public void connectStomp() {

        mStompClient.withClientHeartbeat(1000).withServerHeartbeat(1000);
        resetSubscriptions();
        Disposable dispLifecycle = mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            toast("Stomp connection opened");
                            break;
                        case ERROR:
                            Log.e(TAG, "Stomp connection error", lifecycleEvent.getException());
                            toast("Stomp connection error");
                            break;
                        case CLOSED:
                            toast("Stomp connection closed");
                            resetSubscriptions();
                            break;
                        case FAILED_SERVER_HEARTBEAT:
                            toast("Stomp failed server heartbeat");
                            break;
                    }
                });

        compositeDisposable.add(dispLifecycle);

        // Receive greetings
        Disposable dispTopic = mStompClient.topic("/topic/marketCategory/all")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((StompMessage topicMessage) -> {

                    Log.d(TAG, "Received " + topicMessage.getPayload());
                    //DataModel response1 = mGson.fromJson(topicMessage.getPayload(), DataModel.class);
                    //添加你的数据逻辑

                }, throwable -> {
                    Log.e(TAG, "连接错误", throwable);
                });

        compositeDisposable.add(dispTopic);

        mStompClient.connect();
    }

    private void toast(String text) {
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "---" + text);
    }

    private void resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        compositeDisposable = new CompositeDisposable();
    }


}
