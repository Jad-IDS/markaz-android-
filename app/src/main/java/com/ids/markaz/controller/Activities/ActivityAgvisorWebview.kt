package com.ids.markaz.controller.Activities

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ids.markaz.R
import com.ids.markaz.controller.Base.ActivityBase
import com.ids.markaz.controller.Base.AppCompactBase
import kotlinx.android.synthetic.main.activity_agvisor_webview.*

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.http.SslError
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.webkit.*
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.ids.markaz.BuildConfig


class ActivityAgvisorWebview : ActivityBase() {
    private val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    private var firebaseDefaultMap: HashMap<String, Any>? = null
    val VERSION_CODE_KEY = "android_version_code"
    private val TAG = "ActivityAgvisorWebview"
    var webviewLoaded=false
    var firebaseLoaded=false

    private var mUploadMessage: ValueCallback<Uri>? = null
    var uploadMessage: ValueCallback<Array<Uri>>? = null
    val REQUEST_SELECT_FILE = 100
    private val FILECHOOSER_RESULTCODE = 1

    private var isForceUpdate=1
    val ANDROID_FORCE_UPDATE = "android_force_update"
    val URL_LINK = "urlSmartAdvisor"
    var myUrl=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ids.markaz.R.layout.activity_agvisor_webview)

      //  loadUrl("https://smartadvisor.markaz.com/")


      //  loadUrl2()
        checkVersion()




    }





    private fun checkVersion(){

        firebaseDefaultMap = HashMap()
        mFirebaseRemoteConfig.setDefaults(firebaseDefaultMap)
        mFirebaseRemoteConfig.setConfigSettings(
            FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        )
        mFirebaseRemoteConfig.fetch(0).addOnCompleteListener(object : OnCompleteListener<Void> {
            override fun onComplete(@NonNull task: Task<Void>) {
                if (task.isSuccessful()) {
                    mFirebaseRemoteConfig.activateFetched()
                    Log.wtf(
                        TAG,
                        "Fetched value: " + mFirebaseRemoteConfig.getString(VERSION_CODE_KEY)
                    )
                    checkForUpdate()
                } else
                    Toast.makeText(
                        this@ActivityAgvisorWebview,
                        "Someting went wrong please try again",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        })

    }



    private fun checkForUpdate() {
        firebaseLoaded=true
        isForceUpdate = mFirebaseRemoteConfig.getDouble(ANDROID_FORCE_UPDATE).toInt()
        val latestAppVersion = mFirebaseRemoteConfig.getDouble(VERSION_CODE_KEY).toInt()
        myUrl=mFirebaseRemoteConfig.getString(URL_LINK)
        Log.wtf("latest_version",latestAppVersion.toString())
        if (latestAppVersion > BuildConfig.VERSION_CODE) {
            if(isForceUpdate==1)
               showDialogForceUpdate(this)
            else
                showDialogUpdate(this)
        } else {
            loadUrl2()
/*            if(webviewLoaded)
                goToApp()*/

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
                loadUrl2()
              /*  if(webviewLoaded)
                    goToApp()*/
                //go to app
            }

        val d = builder.create()
        d.setOnShowListener {

            d.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this@ActivityAgvisorWebview, R.color.colorPrimary))
            d.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this@ActivityAgvisorWebview, R.color.colorPrimary))
            d.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).transformationMethod = null
            d.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).isAllCaps = false

            d.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this@ActivityAgvisorWebview, R.color.colorPrimary))
            d.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this@ActivityAgvisorWebview, R.color.colorPrimary))
            d.getButton(android.app.AlertDialog.BUTTON_POSITIVE).transformationMethod = null
            d.getButton(android.app.AlertDialog.BUTTON_POSITIVE).isAllCaps = false
        }
        d.setCancelable(false)

        d.show()

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
                .setTextColor(ContextCompat.getColor(this@ActivityAgvisorWebview, R.color.colorPrimary))
            d.getButton(AlertDialog.BUTTON_NEGATIVE).transformationMethod = null
            d.getButton(AlertDialog.BUTTON_NEGATIVE).isAllCaps = false
        }
        d.setCancelable(false)
        d.show()
    }




    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return
                uploadMessage!!.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent))
                uploadMessage = null
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            val result = if (intent == null || resultCode != RESULT_OK) null else intent.data
            mUploadMessage!!.onReceiveValue(result)
            mUploadMessage = null
        } else
            Toast.makeText(applicationContext, "Failed to Upload Image", Toast.LENGTH_LONG).show()
    }






    private fun loadUrl2(){

        val mWebSettings = myWebview.settings
        mWebSettings.javaScriptEnabled = true
        mWebSettings.domStorageEnabled = true

        myWebview.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                Toast.makeText(this@ActivityAgvisorWebview, description, Toast.LENGTH_SHORT).show()
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            override fun onReceivedError(
                view: WebView,
                req: WebResourceRequest,
                rerr: WebResourceError
            ) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(
                    view,
                    rerr.errorCode,
                    rerr.description.toString(),
                    req.url.toString()
                )
            }
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                //handler!!.proceed();


                val builder = AlertDialog.Builder(this@ActivityAgvisorWebview)
                builder.setMessage(R.string.notification_error_ssl_cert_invalid)
                builder.setPositiveButton("continue"
                ) { dialog, which -> handler!!.proceed() }
                builder.setNegativeButton("cancel"
                ) { dialog, which -> handler!!.cancel() }
                val dialog = builder.create()
                dialog.show()




                // super.onReceivedSslError(view, handler, error)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                 webviewLoaded=true
                 if(firebaseLoaded){
                      goToApp()
                  }
            }
        }

        myWebview.loadUrl(myUrl)
      //  myWebview.loadUrl("https://smartadvisor.markaz.com/")
    }

    private fun goToApp(){
        Handler().postDelayed({
              ivSplash.visibility=View.GONE
              myWebview.visibility=View.VISIBLE
        }, 2000)
    }
}
