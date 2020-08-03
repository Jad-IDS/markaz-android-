package com.ids.markaz.controller.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.R
import android.content.Intent
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.DialogFragment
import com.ids.markaz.controller.Activities.ActivityHomeTabs
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.LocaleUtils
import kotlinx.android.synthetic.main.bottom_sheet_language.*
import java.util.*


class FragmentBottomSeetLanguage : BottomSheetDialogFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.markaz.R.layout.bottom_sheet_language, container, false)


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL,com.ids.markaz.R.style.MyBottomSheetDialog)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
             tvEnglish.setOnClickListener{
                 if(MyApplication.languageCode == AppConstants.LANG_ARABIC)
                    setEnglish()
             }
             tvArabic.setOnClickListener{
                 if(MyApplication.languageCode == AppConstants.LANG_ENGLISH)
                   setArabic()
             }
    }

     private fun setArabic(){
       //  Toast.makeText(activity!!,"aaaaa", Toast.LENGTH_LONG).show()
        MyApplication.languageCode = AppConstants.LANG_ARABIC
        LocaleUtils.setLocale(Locale("ar"))
        reloadActivity()
    }


    private fun setEnglish(){
      //   Toast.makeText(activity!!,"bbbbb",Toast.LENGTH_LONG).show()
        MyApplication.languageCode = AppConstants.LANG_ENGLISH
        LocaleUtils.setLocale(Locale("en"))
        reloadActivity()
    }

    fun reloadActivity(){


        val bundle = ActivityOptionsCompat.makeCustomAnimation(
            activity!!,
            android.R.anim.fade_in, android.R.anim.fade_out
        ).toBundle()
        activity!!.startActivity(Intent(activity!!,ActivityHomeTabs::class.java), bundle)
        activity!!.finish()

    }

}

