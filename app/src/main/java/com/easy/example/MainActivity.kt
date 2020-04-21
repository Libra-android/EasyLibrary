package com.easy.example

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val test by lazy {
        Test()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            seekBar.setProgress(50,true)
//        }else{
//            seekBar.progress = 50
//        }

        test.initClient()
        //test.startTask()


        //startService(Intent(this, TestService::class.java))

//        val manager = getSystemService(Activity.ALARM_SERVICE) as AlarmManager
//
//        val intent = PendingIntent.getActivity(this, 0, openDing(), 0)
//        manager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            System.currentTimeMillis() + 10000,
//            8000,
//            intent
//        )
//
//        AlarmManagerCompat.setExact(
//            manager,
//            AlarmManager.RTC_WAKEUP,
//            System.currentTimeMillis() + 10000,
//            intent
//        )

    }


    private fun openDing(
        packageName: String = "com.alibaba.android.rimet",
        context: Context = this
    ): Intent {
        val packageManager = context.packageManager
        var pi: PackageInfo? = null
        try {
            pi = packageManager.getPackageInfo("com.alibaba.android.rimet", 0) as PackageInfo
        } catch (e: PackageManager.NameNotFoundException) {
        }
        val resolveIntent = Intent(Intent.ACTION_MAIN, null)
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        resolveIntent.setPackage(pi?.packageName)
        val apps = packageManager.queryIntentActivities(resolveIntent, 0)
        val resolveInfo = apps.iterator().next()
        if (resolveInfo != null) {
            val className: String = resolveInfo.activityInfo?.name ?: ""
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val cn = ComponentName(packageName, className)
            intent.component = cn
            //context.startActivity(intent)
        }
        return intent
    }


}



