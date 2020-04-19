package com.easy.example

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import java.util.*

class TestService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread.start()
        return START_STICKY
    }

    val thread = Thread(Runnable {
        handler.postDelayed(Runnable {

        }, 5000)
    })

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
           val time = System.currentTimeMillis()
            val date = Date(time)
            Log.d("aaaaaaaaa", date.toString())
        }
    }


}