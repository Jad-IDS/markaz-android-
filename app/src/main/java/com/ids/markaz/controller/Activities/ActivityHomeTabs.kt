package com.ids.markaz.controller.Activities


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler

import androidx.core.view.GravityCompat

import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.ids.markaz.R

import com.ids.markaz.controller.Base.ActivityBase
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.Fragments.*
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.ItemSpinner
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.AppHelper.Companion.fragmentAvailable
import kotlinx.android.synthetic.main.footer.*

import java.lang.Exception

class ActivityHomeTabs : AppCompactBase() {





    lateinit var fragmentManager: FragmentManager

    private var arrayClients= java.util.ArrayList<ItemSpinner>()
    private var selectedClientId=0

    private var isFirstTime=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_tabs)
        supportActionBar!!.hide()
        Handler().postDelayed({

            isFirstTime=true
            fragmentManager = supportFragmentManager

            setTabs()
            defaultFragment()
            resetTabs()
            AppHelper.setBackgroundColor(this,btTabHome,R.color.primary)
        }, 300)



    //    btTabHome.performClick()
    }




    fun finishTo(){

        finish()
    }










    private fun reloadDataHome(){
        try{ val fragment = fragmentManager.findFragmentByTag(AppConstants.HOME_FRAG) as FragmentHome?
            fragment!!.init()}catch (e:Exception){}
    }


    private fun defaultFragment(){


        fragmentAvailable= AppConstants.HOME
        val frag = FragmentHome()
        fragmentManager.beginTransaction()
            .add(R.id.container, frag, AppConstants.HOME_FRAG)
            .commit()
    }

    private fun setTabs(){
        btTabPortfolio.setOnClickListener{
            if(fragmentAvailable!=AppConstants.PORTFOLIO) {
                AppHelper.ReplaceFragment(fragmentManager,AppConstants.PORTFOLIO,FragmentPortfolio(), AppConstants.PORTFOLIO_FRAG)
                resetTabs()
                AppHelper.setBackgroundColor(this,btTabPortfolio,R.color.primary)
            }
        }



        btTabMore.setOnClickListener{
            if(fragmentAvailable!=AppConstants.MORE) {
                AppHelper.ReplaceFragment(fragmentManager,AppConstants.MORE,FragmentMore(), AppConstants.MORE_FRAG)
                resetTabs()
                AppHelper.setBackgroundColor(this,btTabMore,R.color.primary)
            }
        }


        btTabActivity.setOnClickListener{
            if(fragmentAvailable!=AppConstants.ACTIVITY_TRANSACTIONS) {
                AppHelper.ReplaceFragment(fragmentManager,AppConstants.ACTIVITY_TRANSACTIONS,FragmentActivityTransactions(), AppConstants.ACTIVITY_TRANSACTIONS_FRAG)
                resetTabs()
                AppHelper.setBackgroundColor(this,btTabActivity,R.color.primary)
            }
        }

        btTabReports.setOnClickListener{

            if(fragmentAvailable!=AppConstants.REPORTS) {
                AppHelper.ReplaceFragment(fragmentManager,AppConstants.REPORTS,FragmentReport(), AppConstants.REPORT_FRAG)
                resetTabs()
                AppHelper.setBackgroundColor(this,btTabReports,R.color.primary)
            }


        }
        btTabHome.setOnClickListener{
            if(fragmentAvailable!=AppConstants.HOME) {
                AppHelper.ReplaceFragment(fragmentManager,AppConstants.HOME,FragmentHome(), AppConstants.HOME_FRAG)
                resetTabs()
                AppHelper.setBackgroundColor(this,btTabHome,R.color.primary)
            }
        }



    }


    private fun resetTabs(){
        btTabPortfolio.setBackgroundColor(0x00000000)
        btTabActivity.setBackgroundColor(0x00000000)
        btTabHome.setBackgroundColor(0x00000000)
        btTabMore.setBackgroundColor(0x00000000)
        btTabReports.setBackgroundColor(0x00000000)
    }






}
