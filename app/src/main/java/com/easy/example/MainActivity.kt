package com.easy.example

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import com.easy.example.base.BaseCustomViewModel
import com.easy.example.databinding.ActivityMainBinding
import com.easy.lib.network.EasyStompClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.easy.example.adaapter.ListAdapter
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private val list = arrayListOf<BaseCustomViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(Intent(this, TestActivity::class.java), ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
            }

        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter = ListAdapter()



    }

    override fun onDestroy() {
        super.onDestroy()
        EasyStompClient.disconnectStomp()
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



