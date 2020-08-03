package com.ids.markaz.controller.Fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Activities.ActivityPortfolioInside
import com.ids.markaz.controller.Adapters.AdapterAccountSummary
import com.ids.markaz.controller.Adapters.AdapterPortfolio

import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.Portfolio
import com.ids.markaz.model.PortfolioDetails
import com.ids.markaz.model.ResponseAccountSummary
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.fragment_portfolios.*
import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.tvToolbarTitle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class FragmentPortfolio : Fragment() , RVOnItemClickListener {


    override fun onItemClicked(view: View, position: Int) {
        MyApplication.selectedHolding=adapterPortfolio.itemPortfolio[position]
        startActivity(Intent(activity!!, ActivityPortfolioInside::class.java)
            .putExtra(AppConstants.SELECTED_PORTFOLIO_ID,adapterPortfolio.itemPortfolio[position].id))

    }


    private lateinit var adapterPortfolio: AdapterPortfolio
    private lateinit var adapterHolding: AdapterAccountSummary
    private var arrayAccountSummary=java.util.ArrayList<ResponseAccountSummary>()
    private var arrayHolding=java.util.ArrayList<Portfolio>()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.markaz.R.layout.fragment_portfolios, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getAccountSummary()
    }

    private fun init(){
       tvToolbarTitle.text=getString(R.string.portfolio)
    }



    private fun setAccountSummary(){
        adapterPortfolio= AdapterPortfolio(arrayAccountSummary,this,1)
        val glm = GridLayoutManager(activity!!, 1)
        rvPortfolios.adapter=adapterPortfolio
        rvPortfolios.layoutManager=glm
        rvPortfolios.isNestedScrollingEnabled=false

    }







    private fun getAccountSummary(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getPortfolioAccount(
                MyApplication.investioUserId
            )?.enqueue(object : Callback<ArrayList<ResponseAccountSummary>> {
                override fun onResponse(call: Call<ArrayList<ResponseAccountSummary>>, response: Response<ArrayList<ResponseAccountSummary>>) {
                    try{
                        if(response.code()==401)
                            AppHelper.reLogin(activity!!)}catch (e:Exception){}
                    try{
                        loading.visibility=View.GONE
                        arrayAccountSummary.clear()
                        arrayAccountSummary.addAll(response.body()!!)
                        //testing
                        for (i in arrayAccountSummary.indices){
                            if(arrayAccountSummary[i].typeId==1){
                            arrayAccountSummary[i].arrayDetails!!.add(PortfolioDetails(getString(R.string.holdings),arrayAccountSummary[i].currency+" "+arrayAccountSummary[i].holdingPosition!!.toDouble(),AppHelper.formatNumber(arrayAccountSummary[i].holdingPositionPercent!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator)))
                            arrayAccountSummary[i].arrayDetails!!.add(PortfolioDetails(getString(R.string.cash_available),arrayAccountSummary[i].currency+" "+AppHelper.formatNumber(arrayAccountSummary[i].cashPosition!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator),AppHelper.formatNumber(arrayAccountSummary[i].cashPositionPercent!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator)))
                            arrayAccountSummary[i].arrayDetails!!.add(PortfolioDetails(getString(R.string.payable_receivable),arrayAccountSummary[i].currency+" "+AppHelper.formatNumber(arrayAccountSummary[i].payableReceivable!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator),"-"))
                            arrayAccountSummary[i].arrayDetails!!.add(PortfolioDetails(getString(R.string.loans),arrayAccountSummary[i].currency+" "+AppHelper.formatNumber(arrayAccountSummary[i].loans!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator),"-"))
                         }else{
                                arrayAccountSummary[i].arrayDetails!!.add(PortfolioDetails(getString( R.string.units),"",AppHelper.formatNumber(arrayAccountSummary[i].units!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator)))
                                arrayAccountSummary[i].arrayDetails!!.add(PortfolioDetails(getString(  R.string.nav),arrayAccountSummary[i].currency+" "+AppHelper.formatNumber(arrayAccountSummary[i].navValuePosition!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator),AppHelper.formatNumber(arrayAccountSummary[i].navValuePositionPercent!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator)))
                        }
                        }
                        
                        setAccountSummary()
                    }catch (E: Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseAccountSummary>>, throwable: Throwable) {
                }
            })

    }


}


