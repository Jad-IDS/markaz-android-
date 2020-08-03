package com.ids.markaz.controller.Activities

import android.os.Bundle

import androidx.fragment.app.FragmentManager
import com.ids.markaz.R
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.Fragments.FragmentLogin
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.utils.AppConstants

class ActivityLogin : AppCompactBase() {
    private lateinit var fragmentManager: FragmentManager
    var fragmentAvailable= AppConstants.LOGIN
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        defaultFragment()
    }


    private fun init(){


        supportActionBar!!.hide()
        fragmentManager = supportFragmentManager

    }

     fun defaultFragment(){

        fragmentAvailable=AppConstants.LOGIN
        val login = FragmentLogin()
        fragmentManager.beginTransaction()
            .replace(com.ids.markaz.R.id.container, login, AppConstants.LOGIN_FRAG)
            .commit()
    }









}
