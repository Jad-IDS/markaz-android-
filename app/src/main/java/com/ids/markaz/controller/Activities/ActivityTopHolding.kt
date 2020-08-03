package com.ids.markaz.controller.Activities


import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterHoldings
import com.ids.markaz.controller.Adapters.AdapterPortfolioDetails
import com.ids.markaz.controller.Adapters.AdapterPortfolioPosition
import com.ids.markaz.controller.Adapters.AdapterResponseHolding
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.controller.MyApplication.Companion.selectedTab
import com.ids.markaz.controller.MyApplication.Companion.selectedTabInside
import com.ids.markaz.model.*
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.activity_portfolio_new_inside.*
import kotlinx.android.synthetic.main.activity_top_holding.*
import kotlinx.android.synthetic.main.home_tab_performance.*
import kotlinx.android.synthetic.main.loading_trans.*

import kotlinx.android.synthetic.main.portfolio_tab_holdings.*
import kotlinx.android.synthetic.main.portfolio_tab_overview.*


import kotlinx.android.synthetic.main.toolbar.tvToolbarTitle
import kotlinx.android.synthetic.main.toolbar_general.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class ActivityTopHolding : AppCompactBase() ,RVOnItemClickListener{
    override fun onItemClicked(view: View, position: Int) {
        if(view.id==R.id.linearHoldings){
            MyApplication.selectedHoldingPosition=ResponseHoldingPosition()
            MyApplication.selectedTop=adapterHolding.itemPortfolio[position]
            startActivity(Intent(this,ActivityPortfolioDetails::class.java)
                .putExtra(AppConstants.PORFOLIO_NAME,arrayHoldings[position].stock))
        }

    }




    private var portfolioId=0
    private var arrayHoldings=java.util.ArrayList<ResponseTopHolding>()
    private lateinit var adapterHolding: AdapterResponseHolding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_holding)
        init()

    }

    private fun init(){
        selectedTabInside=AppConstants.TAB_PORTFOLIO_OVERVIEW
        supportActionBar!!.hide()
        tvToolbarTitle.text=getString(R.string.portfolio)
        btBack.visibility=View.VISIBLE
        getTopHolding()



        btBack.setOnClickListener{
            super.onBackPressed()
        }

    }

    private fun getTopHolding(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getTopHolding(
                MyApplication.clientId,
                MyApplication.currencyId,

                //"2020-04-18",
                  date,
                1
            )?.enqueue(object : Callback<ArrayList<ResponseTopHolding>> {
                override fun onResponse(call: Call<ArrayList<ResponseTopHolding>>, response: Response<ArrayList<ResponseTopHolding>>) {
                    try{
                        loading.visibility=View.GONE
                        arrayHoldings.clear()
                        arrayHoldings.addAll(response.body()!!)
                        setHoldings()

                    }catch (E: java.lang.Exception){
                        Log.wtf("exception",E.toString())
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseTopHolding>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }


    private fun setHoldings(){
        if (arrayHoldings.size>5){
            arrayHoldings.add(0,ResponseTopHolding())
            arrayHoldings.add(6,ResponseTopHolding())
        }

        adapterHolding= AdapterResponseHolding(arrayHoldings,this,1)
        val glm = GridLayoutManager(this, 1)
        rvTopHolding.adapter=adapterHolding
        rvTopHolding.layoutManager=glm
    }


}
