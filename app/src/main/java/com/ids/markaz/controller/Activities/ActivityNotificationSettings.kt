package com.ids.markaz.controller.Activities

import android.os.Bundle
import android.view.View
import com.ids.markaz.R
import com.ids.markaz.controller.Base.ActivityBase
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.utils.AppHelper
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.toolbar_general.*





class ActivityNotificationSettings : AppCompactBase(){






    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_notifications)
        init()
    }

   fun init() {

       supportActionBar!!.hide()
       tvToolbarTitle.text = getString (R. string. notifications)
       btBack.visibility = View.VISIBLE

       swGeneral.isChecked = MyApplication.notificationGeneral
       swProvideInfo.isChecked = MyApplication.notificationInfo
       swNav.isChecked = MyApplication.notificationNAV
       swKYC.isChecked = MyApplication.notificationKYC



       swGeneral.setOnClickListener {
           MyApplication.notificationGeneral = swGeneral.isChecked
       }

       swKYC.setOnClickListener {
           MyApplication.notificationKYC = swKYC.isChecked
       }

       swNav.setOnClickListener {
           MyApplication.notificationNAV = swNav.isChecked
       }

       swProvideInfo.setOnClickListener {
           MyApplication.notificationInfo = swProvideInfo.isChecked
       }


       btBack.setOnClickListener {

           onBackPressed()
       }
   }
}