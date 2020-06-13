package com.easy.example.timer

import android.os.CountDownTimer
import android.widget.TextView
import androidx.lifecycle.*

class CodeTimer(
    private val lifecycle: Lifecycle,
    private val textView: TextView,
    millisInFuture: Long = 1000 * 60,
    countDownInterval: Long = 1000
) :
    CountDownTimer(millisInFuture, countDownInterval) ,LifecycleObserver{

    init {
        lifecycle.addObserver(this)
        textView.setOnClickListener {
            textView.isEnabled = false
            start()
        }

    }

    override fun onFinish() {
        textView.text = "重新获取"
        textView.isEnabled = true
    }

    override fun onTick(millisUntilFinished: Long) {
        textView.text = "剩余${millisUntilFinished/1000}s"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun stop(){
        cancel()
    }



}
