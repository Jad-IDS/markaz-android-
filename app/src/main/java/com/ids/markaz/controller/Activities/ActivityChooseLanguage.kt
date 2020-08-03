package com.ids.controller.Activities

import android.content.Intent
import android.content.res.ColorStateList

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat


import com.ids.markaz.R
import com.ids.markaz.controller.Activities.ActivityChooseApp
import com.ids.markaz.controller.Activities.ActivityLogin
import com.ids.markaz.controller.Base.ActivityBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.LocaleUtils

import kotlinx.android.synthetic.main.activity_choose_app.*
import kotlinx.android.synthetic.main.activity_choose_language.*
import java.util.*

class ActivityChooseLanguage : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_language)
        init()
    }

    private fun init(){
        linearEnglish.setOnClickListener{setEnglish()}
        linearArabic.setOnClickListener{setArabic()}
        linearOk.setOnClickListener{
            MyApplication.isFirstTimeHint=false
            startActivity(Intent(this,ActivityChooseApp::class.java))
        }
    }


    private fun setEnglish(){


        // ivEnglishRadio.setTint(R.color.white)
        ivEnglishRadio.setImageResource(R.drawable.circle_select)
        ivArabicRadio.setImageResource(R.drawable.circle_unselect)
        MyApplication.languageCode = AppConstants.LANG_ENGLISH
        LocaleUtils.setLocale(Locale("en"))
      //  reloadActivity()



    }

    /*  if(MyApplication.languageCode==AppConstants.LANG_ARABIC){
      MyApplication.languageCode = AppConstants.LANG_ENGLISH
      LocaleUtils.setLocale(Locale("en"))
  }else{
      MyApplication.languageCode = AppConstants.LANG_ARABIC
      LocaleUtils.setLocale(Locale("ar"))
  }
  LocaleUtils.updateConfig(activity!!.application, activity!!.baseContext.resources.configuration)
  try{(activity!! as ActivityLogin).reloadActivity()}catch (e:Exception){ }
*/


    fun reloadActivity(){


        val bundle = ActivityOptionsCompat.makeCustomAnimation(
            this,
            android.R.anim.fade_in, android.R.anim.fade_out
        ).toBundle()
        startActivity(intent, bundle)
        finish()

    }

    private fun setArabic(){

        ivEnglishRadio.setImageResource(R.drawable.circle_unselect)
        ivArabicRadio.setImageResource(R.drawable.circle_select)
        MyApplication.languageCode = AppConstants.LANG_ARABIC
        LocaleUtils.setLocale(Locale("ar"))
       // reloadActivity()
    }

    fun ImageView.setTint(@ColorRes colorRes: Int) {
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, colorRes)))
    }
}
