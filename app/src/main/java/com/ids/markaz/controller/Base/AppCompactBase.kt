package com.ids.markaz.controller.Base



import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.LocaleUtils

import java.lang.Exception
import java.util.*

open class AppCompactBase : AppCompatActivity() {
    private var decorView: View? = null

    init {
        LocaleUtils.updateConfig(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppHelper.setLocal(this)
        AppHelper.handleCrashes(this)


        /*     if(MyApplication.isLoggedIn) {
                 LocalBroadcastManager.getInstance(this).registerReceiver(
                     object : BroadcastReceiver() {
                         override fun onReceive(context: Context, intent: Intent) {
                            try{AppHelper.setConnection(this@AppCompactBase)}catch (e:Exception){}
                         }
                     }, IntentFilter(NetworkJobService.ACTION_NET_SERVICE)
                 )
             }*/
    }



    private fun hideSystemUI() {
        decorView!!.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar

                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

    private fun showSystemUI() {
        decorView!!.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    override fun attachBaseContext(newBase: Context) {
        var newBase = newBase
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val config = newBase.resources.configuration
            config.setLocale(Locale.getDefault())
            newBase = newBase.createConfigurationContext(config)
        }
        super.attachBaseContext(newBase)
    }



}
