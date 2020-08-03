package com.ids.markaz.controller.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.ids.markaz.R
import com.ids.markaz.controller.Base.ActivityBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import kotlinx.android.synthetic.main.activity_choose_app.*

class ActivityChooseApp : ActivityBase() {

    private var selectedApp=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_app)
        init()
    }


    private fun init(){
         selectedApp=AppConstants.MARKAZ
         linearMarkazRobot.setOnClickListener{next()}
         linearMarkazWealth.setOnClickListener{next()}





    }

    private fun next(){
        if(selectedApp==AppConstants.MARKAZ){
            //if(MyApplication.isFirstTimeHint) {
            startActivity(Intent(this, ActivityStart::class.java))
            finish()
            /*         }else{
                         startActivity(Intent(this, ActivityLogin::class.java))
                         finish()
                     }*/
        }else{
            startActivity(Intent(this, ActivityAgvisorWebview::class.java))
            finish()


        }
    }
    private fun test(){

    }


}
