package com.easy.example

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import androidx.core.app.AlarmManagerCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = PendingIntent.getActivity(this, 123, Intent(this, MainActivity::class.java), 0X0)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(10000, intent), intent)
            alarmManager.setTime(System.currentTimeMillis() + 10000)
            AlarmManagerCompat.setExact(alarmManager,AlarmManager.RTC_WAKEUP ,10000,intent)
        } else {

        }


    }

}
