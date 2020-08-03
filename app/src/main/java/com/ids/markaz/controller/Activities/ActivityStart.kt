package com.ids.markaz.controller.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.ids.controller.Activities.ActivityChooseLanguage

import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterStartPager
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.ActivityBase
import com.ids.markaz.controller.MyApplication
import kotlinx.android.synthetic.main.activity_start.*


class ActivityStart : ActivityBase(),RVOnItemClickListener {
    override fun onItemClicked(view: View, position: Int) {

    }

    private lateinit var adapter: AdapterStartPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        init()
    }


    private fun init(){
         setStartPager()
      }


    private fun setStartPager(){
        adapter = AdapterStartPager(this, arrayListOf(),this)
        vpStart.adapter = adapter
        tlImage.setupWithViewPager(vpStart)

        vpStart?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
               if(position==2) {
                   btSkip.text = getString(R.string.lest_start)
                   btSkip.setBackgroundResource(R.drawable.rounded_corner_primary)
               }else{
                   btSkip.text = getString(R.string.skip)
                   btSkip.setBackgroundResource(R.drawable.bt_skip_bg)
               }

            }

        })

    }

    fun skip(v:View){
        MyApplication.isFirstTimeHint=false
         startActivity(Intent(this, ActivityLogin::class.java))

    }



}
