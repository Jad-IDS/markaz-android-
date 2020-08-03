package com.ids.markaz.controller.Activities



import android.os.Bundle

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterHoldings
import com.ids.markaz.controller.Adapters.AdapterPortfolioDetails
import com.ids.markaz.controller.Adapters.AdapterPortfolioPosition
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.PortfolioDetails
import com.ids.markaz.model.ResponseHoldingPosition
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import kotlinx.android.synthetic.main.activity_portfolio_details.*

import kotlinx.android.synthetic.main.activity_portfolio_new_inside.*
import kotlinx.android.synthetic.main.toolbar_general.*
import java.lang.Exception


class ActivityPortfolioDetails : AppCompactBase() ,RVOnItemClickListener{
    override fun onItemClicked(view: View, position: Int) {
    }



    private lateinit var adapterHolding: AdapterPortfolioPosition
    private var portfolioId=0
    private var arrayHolding=java.util.ArrayList<ResponseHoldingPosition>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_details)
        init()

    }

    private fun init(){
        supportActionBar!!.hide()
        try{tvToolbarTitle.text=intent.getStringExtra(AppConstants.PORFOLIO_NAME)}catch (e:Exception){}
        btBack.visibility=View.VISIBLE
        setPortfolioDetails()
        btBack.setOnClickListener{
            super.onBackPressed()
        }

    }




    private fun setPortfolioDetails(){


        var arrayInfo=ArrayList<PortfolioDetails>()

        if(MyApplication.selectedTop.stock!=null){
        arrayInfo.add(PortfolioDetails(getString(R.string.stock),MyApplication.selectedTop.stock,""))
        arrayInfo.add(PortfolioDetails(getString(R.string.unit_cost),MyApplication.selectedTop.currency!!+" "+AppHelper.formatNumber(MyApplication.selectedTop.cost!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator),""))
        arrayInfo.add(PortfolioDetails(getString(R.string.total_cost),MyApplication.selectedTop.currency!!+" "+AppHelper.formatNumber(MyApplication.selectedTop.totalCost!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator),""))
        arrayInfo.add(PortfolioDetails(getString(R.string.close_price),MyApplication.selectedTop.currency!!+" "+AppHelper.formatNumber(MyApplication.selectedTop.price!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator),""))
        arrayInfo.add(PortfolioDetails(getString(R.string.total_value),MyApplication.selectedTop.currency!!+" "+AppHelper.formatNumber(MyApplication.selectedTop.totalValue!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator),""))
        arrayInfo.add(PortfolioDetails(getString(R.string.unrealize_pl),MyApplication.selectedTop.currency!!+" "+AppHelper.formatNumber(MyApplication.selectedTop.unrealized!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator),""))
        arrayInfo.add(PortfolioDetails(getString(R.string.unrealizepl_per),AppHelper.formatNumber(MyApplication.selectedTop.percentage!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator)+" %",""))
        }else{
            arrayInfo.add(PortfolioDetails(getString(R.string.qty_aft),MyApplication.selectedHoldingPosition.quantity.toString(),""))
            arrayInfo.add(PortfolioDetails(getString(R.string.unit_cost),AppHelper.getCurrencyName()+" "+AppHelper.formatNumber(MyApplication.selectedHoldingPosition.avgCost!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator),""))
            arrayInfo.add(PortfolioDetails(getString(R.string.total_cost),AppHelper.getCurrencyName()+" "+AppHelper.formatNumber(MyApplication.selectedHoldingPosition.totalCost!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator),""))
            arrayInfo.add(PortfolioDetails(getString(R.string.close_price),AppHelper.getCurrencyName()+" "+" "+AppHelper.formatNumber(MyApplication.selectedHoldingPosition.lastPrice!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator),""))
            arrayInfo.add(PortfolioDetails(getString(R.string.total_value),AppHelper.getCurrencyName()+" "+" "+AppHelper.formatNumber(MyApplication.selectedHoldingPosition.totalCurrentValue!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator),""))
            arrayInfo.add(PortfolioDetails(getString(R.string.unrealize_pl),AppHelper.getCurrencyName()+" "+" "+AppHelper.formatNumber(MyApplication.selectedHoldingPosition.unrealizedValue!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator),""))
            arrayInfo.add(PortfolioDetails(getString(R.string.unrealizepl_per),AppHelper.formatNumber(MyApplication.selectedHoldingPosition.unrealizedPercent!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator)+" %",""))
            arrayInfo.add(PortfolioDetails(getString(R.string.realize_pl_ytd),AppHelper.getCurrencyName()+" "+AppHelper.formatNumber(MyApplication.selectedHoldingPosition.realizedPL!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator)+" %",""))
            arrayInfo.add(PortfolioDetails(getString(R.string.cash_divended),AppHelper.getCurrencyName()+" "+AppHelper.formatNumber(MyApplication.selectedHoldingPosition.divRecieved!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator)+" %",""))

        }

        var adapterDetails= AdapterPortfolioDetails(arrayInfo,this,1)
        val glm = GridLayoutManager(this, 1)
        rvPortfolioDetails.adapter=adapterDetails
        rvPortfolioDetails.layoutManager=glm
        rvPortfolioDetails.isNestedScrollingEnabled=false
    }







}
