package com.ids.markaz.controller.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler

import com.crashlytics.android.Crashlytics
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.ids.markaz.BuildConfig

import com.ids.markaz.controller.Base.ActivityBase
import com.ids.markaz.controller.MyApplication
import android.R.attr.versionCode
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.ids.controller.Activities.ActivityChooseLanguage
import com.ids.markaz.R





class ActivitySplash : ActivityBase() {
    private val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    private var firebaseDefaultMap: HashMap<String, Any>? = null
    val VERSION_CODE_KEY = "android_version_code"
    val ANDROID_FORCE_UPDATE = "android_force_update"
    private val TAG = "ActivitySplash"
    private var isForceUpdate=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ids.markaz.R.layout.activity_splash)
       //Crashlytics.getInstance().crash() // Force a crash

       //  init()
         Handler().postDelayed({
             if(MyApplication.isFirstLaunch){
                 MyApplication.isFirstLaunch=false
                 startActivity(Intent(this,ActivityChooseLanguage::class.java))
             finish()
             }else{
                 startActivity(Intent(this,ActivityLogin::class.java))
                 finish()
             }

     /*        startActivity(Intent(this, ActivityAgvisorWebview::class.java))
             finish()
*/

        }, 2000)
    }



    fun init(){


        firebaseDefaultMap = HashMap()
        //Setting the Default Map Value with the current version code


        //Setting that default Map to Firebase Remote Config
        mFirebaseRemoteConfig.setDefaults(firebaseDefaultMap)

        //Setting Developer Mode enabled to fast retrieve the values
        mFirebaseRemoteConfig.setConfigSettings(
            FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        )

        //Fetching the values here
        mFirebaseRemoteConfig.fetch(0).addOnCompleteListener(object : OnCompleteListener<Void> {
            override fun onComplete(@NonNull task: Task<Void>) {
                if (task.isSuccessful()) {
                    mFirebaseRemoteConfig.activateFetched()
                    Log.wtf(
                        TAG,
                        "Fetched value: " + mFirebaseRemoteConfig.getString(VERSION_CODE_KEY)
                    )
                    //calling function to check if new version is available or not
                    checkForUpdate()
                } else
                    Toast.makeText(
                        this@ActivitySplash,
                        getString(R.string.try_again),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        })





/*        val playStoreVersionCode = FirebaseRemoteConfig.getInstance().getString(
            "force_update_url"
        ).toString()
        Toast.makeText(applicationContext,"goo"+playStoreVersionCode,Toast.LENGTH_LONG).show()*/
        val currentAppVersionCode = BuildConfig.VERSION_CODE
   /*     if (playStoreVersionCode > currentAppVersionCode) {
            showUpdateDialog(this)
        }else{
            Toast.makeText(applicationContext,"goo"+playStoreVersionCode,Toast.LENGTH_LONG).show()
        }*/
    }






    private fun showDialogForceUpdate(activity: Activity) {

        val builder = AlertDialog.Builder(activity)
        val textView: TextView
        val inflater = activity.layoutInflater
        val textEntryView = inflater.inflate(R.layout.item_dialog, null)
        textView = textEntryView.findViewById(R.id.dialogMsg)
        textView.gravity = Gravity.START
        textView.text = activity.resources.getString(R.string.update_message)
        builder.setTitle(activity.resources.getString(R.string.update_title))
   


        builder.setView(textEntryView)
            .setNegativeButton(activity.resources.getString(R.string.update_button)) { dialog, _ ->
                dialog.dismiss()
                val appPackageName = activity.packageName
                try {

                    activity.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName)
                        )
                    )
                    activity.finish()

                } catch (anfe: ActivityNotFoundException) {

                    activity.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)
                        )
                    )
                    activity.finish()

                }
            }
        val d = builder.create()
        d.setOnShowListener {
            d.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(this@ActivitySplash, R.color.colorPrimary))
            d.getButton(AlertDialog.BUTTON_NEGATIVE).transformationMethod = null
            d.getButton(AlertDialog.BUTTON_NEGATIVE).isAllCaps = false
        }



        d.show()
    }


    private fun checkForUpdate() {
        val latestAppVersion = mFirebaseRemoteConfig.getDouble(VERSION_CODE_KEY).toInt()
         isForceUpdate = mFirebaseRemoteConfig.getDouble(ANDROID_FORCE_UPDATE).toInt()
        Log.wtf("latest_version",latestAppVersion.toString())
        if (latestAppVersion > BuildConfig.VERSION_CODE) {
            if(isForceUpdate==1)
              showDialogForceUpdate(this)
            else
                showDialogUpdate(this)
        } else {
             Handler().postDelayed({
              startActivity(Intent(this, ActivityAgvisorWebview::class.java))
             finish()


        }, 2000)
        }
    }





    private fun showDialogUpdate(activity: Activity) {

        val builder = AlertDialog.Builder(activity)
        val textView: TextView
        val inflater = activity.layoutInflater
        val textEntryView = inflater.inflate(R.layout.item_dialog, null)
        textView = textEntryView.findViewById(R.id.dialogMsg)
        textView.gravity = Gravity.CENTER
        textView.text = activity.resources.getString(R.string.update_message)
        builder.setTitle(activity.resources.getString(R.string.update_title))

        builder.setView(textEntryView)
            .setPositiveButton(activity.resources.getString(R.string.update_button)) { dialog, _ ->
                dialog.dismiss()
                val appPackageName = activity.packageName
                try {
                    activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))

                    activity.finish()

                } catch (anfe: android.content.ActivityNotFoundException) {
                    activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                    activity.finish()

                }
            }
            .setNegativeButton(activity.resources.getString(R.string.update_cancel)) { dialog, _ ->
                dialog.dismiss()

                //go to app
            }

        val d = builder.create()
        d.setOnShowListener {

            d.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this@ActivitySplash, R.color.colorPrimary))
            d.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this@ActivitySplash, R.color.colorPrimary))
            d.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).transformationMethod = null
            d.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).isAllCaps = false

            d.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this@ActivitySplash, R.color.colorPrimary))
            d.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this@ActivitySplash, R.color.colorPrimary))
            d.getButton(android.app.AlertDialog.BUTTON_POSITIVE).transformationMethod = null
            d.getButton(android.app.AlertDialog.BUTTON_POSITIVE).isAllCaps = false
        }
        d.setCancelable(false)

        d.show()

    }

    private fun getCurrentVersionCode(): Int {
        try {
            return packageManager.getPackageInfo(packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return -1
    }
}
