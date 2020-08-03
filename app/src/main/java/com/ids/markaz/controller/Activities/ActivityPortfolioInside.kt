package com.ids.markaz.controller.Activities


import android.content.Intent
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterAllInvestment
import com.ids.markaz.controller.Adapters.AdapterHoldings
import com.ids.markaz.controller.Adapters.AdapterPortfolioDetails
import com.ids.markaz.controller.Adapters.AdapterPortfolioPosition
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
import kotlinx.android.synthetic.main.home_tab_performance.*


import kotlinx.android.synthetic.main.loading_trans.*

import kotlinx.android.synthetic.main.portfolio_tab_holdings.*
import kotlinx.android.synthetic.main.portfolio_tab_overview.*
import kotlinx.android.synthetic.main.sub_tabs.*
import kotlinx.android.synthetic.main.sub_tabs.tvTabAsset
import kotlinx.android.synthetic.main.sub_tabs.tvTabCurrency
import kotlinx.android.synthetic.main.sub_tabs.tvTabGeographical
import kotlinx.android.synthetic.main.sub_tabs.tvTabSector


import kotlinx.android.synthetic.main.toolbar.tvToolbarTitle
import kotlinx.android.synthetic.main.toolbar_general.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ActivityPortfolioInside : AppCompactBase() ,RVOnItemClickListener{
    override fun onItemClicked(view: View, position: Int) {
        if(view.id==R.id.linearHoldings){
            MyApplication.selectedHoldingPosition=adapterHolding.itemPortfolio[position]
            MyApplication.selectedTop=ResponseTopHolding()
            startActivity(Intent(this,ActivityPortfolioDetails::class.java)
                .putExtra(AppConstants.PORFOLIO_NAME,arrayHoldings[position].tickerEn))
        }

    }




    private var portfolioId=0
    private var arrayHoldings=java.util.ArrayList<ResponseHoldingPosition>()
    private var arrayTotals=java.util.ArrayList<ResponsePortfolioHolding>()
    lateinit var adapterInvestment: AdapterAllInvestment
    lateinit var adapterHolding: AdapterHoldings
    var arrayAllInvestment=java.util.ArrayList<ResponseAllInvestment>()

    var arrayAccountPerformance=java.util.ArrayList<ResponseAccountPerformance>()
    private var selectedPerformanceType=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_new_inside)
        init()

    }

    private fun init(){
        portfolioId=MyApplication.selectedHolding.id!!
        selectedTabInside=AppConstants.TAB_PORTFOLIO_OVERVIEW
        supportActionBar!!.hide()
        tvToolbarTitle.text=MyApplication.selectedHolding.nameEn
        btBack.visibility=View.VISIBLE
        setListeners()
        setInvestment()
        setTotalCashInfo()
        getHolding()
        getHoldingPositionBench()
        if(MyApplication.selectedHolding.typeId==1)
           getCashPortfolioDetails()
        else
            getFundDetails()
        getInvestmentAsset(false)
        getAccountPerformance()
        MyApplication.selectedSubTab =AppConstants.TAB_ASSET


        btBack.setOnClickListener{
            super.onBackPressed()
        }

    }


    private fun setListeners(){
       btTabOverview.setOnClickListener{
            setTab(AppConstants.TAB_PORTFOLIO_OVERVIEW)
        }
        btTabPerformance.setOnClickListener{
            setTab(AppConstants.TAB_PORTFOLIO_PERFORMANCE)

        }
        btTabHolding.setOnClickListener{
            setTab(AppConstants.TAB_PORTFOLIO_HOLDINGS)

        }


        tvTabAsset.setOnClickListener{

            MyApplication.selectedSubTab =AppConstants.TAB_ASSET
            setSubTab(AppConstants.TAB_ASSET)
            getInvestmentAsset(true)

        }
        tvTabGeographical.setOnClickListener{
            MyApplication.selectedSubTab =AppConstants.TAB_GEOGRAPHICAL
            setSubTab(AppConstants.TAB_GEOGRAPHICAL)
            getInvestmentCountry()
        }
        tvTabSector.setOnClickListener{
            MyApplication.selectedSubTab =AppConstants.TAB_SECTOR
            setSubTab(AppConstants.TAB_SECTOR)
            getInvestmentSector()
        }
        tvTabCurrency.setOnClickListener{
            MyApplication.selectedSubTab =AppConstants.TAB_CURRENCY
            setSubTab(AppConstants.TAB_CURRENCY)
            getInvestmentCurrency()
        }
        setPerformance()

        btYs.setOnClickListener{
            selectedPerformanceType=AppConstants.PERFORMANCE_TYPE_YS
            setPerformance()
            setLineChartData()
            setbardata()
        }

        btYtd.setOnClickListener{
            selectedPerformanceType=AppConstants.PERFORMANCE_TYPE_YTD
            setPerformance()
            setLineChartData()
            setbardata()
        }

        btMtd.setOnClickListener{
            selectedPerformanceType=AppConstants.PERFORMANCE_TYPE_MTD
            setPerformance()
            setLineChartData()
            setbardata()
        }

        btTopHolding.setOnClickListener{
            startActivity(Intent(this,ActivityTopHolding::class.java))
        }

    }

    private fun getHolding(){
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getHoldingPosition(
                portfolioId,
                MyApplication.currencyId,
                date,
                ""
            )?.enqueue(object : Callback<ArrayList<ResponseHoldingPosition>> {
                override fun onResponse(call: Call<ArrayList<ResponseHoldingPosition>>, response: Response<ArrayList<ResponseHoldingPosition>>) {
                    try{
                        arrayHoldings.clear()
                        arrayHoldings.addAll(response.body()!!)
                         setHoldings()


                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseHoldingPosition>>, throwable: Throwable) {
                }
            })

    }


    private fun getCashPortfolioDetails(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getCashDetails(
                portfolioId,
                MyApplication.currencyId,
                MyApplication.investioUserId
            )?.enqueue(object : Callback<ResponsePortfolioCash> {
                override fun onResponse(call: Call<ResponsePortfolioCash>, response: Response<ResponsePortfolioCash>) {
                    try{
                        loading.visibility=View.GONE
                        setCashDetails(response.body()!!)
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ResponsePortfolioCash>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }


    private fun getFundDetails(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getFundsDetails(
                portfolioId
            )?.enqueue(object : Callback<ResponseFundDetails> {
                override fun onResponse(call: Call<ResponseFundDetails>, response: Response<ResponseFundDetails>) {
                    try{
                        loading.visibility=View.GONE
                        setFundsDetails(response.body()!!)
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ResponseFundDetails>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }


    private fun getHoldingPositionBench(){
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getHoldingPositionBench(
                portfolioId,
                MyApplication.currencyId,
                date,
                ""
            )?.enqueue(object : Callback<ArrayList<ResponsePortfolioHolding>> {
                override fun onResponse(call: Call<ArrayList<ResponsePortfolioHolding>>, response: Response<ArrayList<ResponsePortfolioHolding>>) {
                    try{
                        arrayTotals.clear()
                        arrayTotals.addAll(response.body()!!)
                        setTotals()


                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponsePortfolioHolding>>, throwable: Throwable) {
                }
            })

    }

    private fun setTotalCashInfo(){
        try{tvCashPosition.text=AppHelper.getCurrencyName()+" "+AppHelper.formatNumber(MyApplication.selectedHolding.cashPosition!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator)}catch (e:Exception){}
        //try{holder.tvPrice.text=AppHelper.formatNumber(itemPortfolio[position].cashPosition!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator)}catch (e:Exception){}


    }


    private fun setCashDetails(responsePortfolioCash: ResponsePortfolioCash){
        var arrayDetails= arrayListOf<PortfolioDetails>()
        var array= arrayListOf<PortfolioDetails>()
       // if(MyApplication.selectedHolding.typeId==1){
            array.add(PortfolioDetails(getString(R.string.cash_ava),MyApplication.selectedHolding.currency+" "+responsePortfolioCash.balanceCashAFT!!.toDouble(),""))
            array.add(PortfolioDetails(getString(R.string.deposits),MyApplication.selectedHolding.currency+" "+responsePortfolioCash.balanceDeposits!!.toDouble(),""))
            array.add(PortfolioDetails(getString(R.string.blocked),MyApplication.selectedHolding.currency+" "+responsePortfolioCash.balanceBlocked!!.toDouble(),""))

            array.add(PortfolioDetails(getString(R.string.cash_div_due),MyApplication.selectedHolding.currency+" "+responsePortfolioCash.receivableDividends!!.toDouble(),""))
            array.add(PortfolioDetails(getString(R.string.recievable_interest_due),MyApplication.selectedHolding.currency+" "+responsePortfolioCash.receivableInterestAccrued!!.toDouble(),""))
            array.add(PortfolioDetails(getString(R.string.other_receivable),MyApplication.selectedHolding.currency+" "+responsePortfolioCash.receivableOthers!!.toDouble(),""))
            array.add(PortfolioDetails(getString(R.string.fees),MyApplication.selectedHolding.currency+" "+responsePortfolioCash.payableFees!!.toDouble(),""))
            array.add(PortfolioDetails(getString(R.string.payable_interest),MyApplication.selectedHolding.currency+" "+responsePortfolioCash.payableInterest!!.toDouble(),""))
            array.add(PortfolioDetails(getString(R.string.payable_others),MyApplication.selectedHolding.currency+" "+responsePortfolioCash.payableOthers!!.toDouble(),""))

            arrayDetails.addAll(array)
      //  }else{
          //  arrayDetails.addAll(MyApplication.selectedHolding.arrayDetails!!)
       // }



        var adapterTotalCash= AdapterPortfolioDetails(arrayDetails,this,1)
        val glm = GridLayoutManager(this, 1)
        rvPortfolioInfo.adapter=adapterTotalCash
        rvPortfolioInfo.layoutManager=glm
    }


    private fun setFundsDetails(responseFundDetails: ResponseFundDetails){
        var arrayDetails= arrayListOf<PortfolioDetails>()
        var array= arrayListOf<PortfolioDetails>()
        // if(MyApplication.selectedHolding.typeId==1){
        array.add(PortfolioDetails(getString(R.string.units),MyApplication.selectedHolding.currency+" "+responseFundDetails.numberOfUnits!!.toDouble(),""))
        array.add(PortfolioDetails(getString(R.string.avarage_cost),MyApplication.selectedHolding.currency+" "+responseFundDetails.avgCost!!.toDouble(),""))
        array.add(PortfolioDetails(getString(R.string.total_cost),MyApplication.selectedHolding.currency+" "+responseFundDetails.totalCost!!.toDouble(),""))

        array.add(PortfolioDetails(getString(R.string.nav),MyApplication.selectedHolding.currency+" "+responseFundDetails.navPerUnit!!.toDouble(),""))
        array.add(PortfolioDetails(getString(R.string.total_value),MyApplication.selectedHolding.currency+" "+responseFundDetails.totalValue!!.toDouble(),""))
        array.add(PortfolioDetails(getString(R.string.unrealize_pl),MyApplication.selectedHolding.currency+" "+responseFundDetails.unrealizedPL!!.toDouble(),""))
        array.add(PortfolioDetails(getString(R.string.per_ytd),MyApplication.selectedHolding.currency+" "+responseFundDetails.ytdPerfomancePerc!!.toDouble(),""))

        arrayDetails.addAll(array)
        //  }else{
        //  arrayDetails.addAll(MyApplication.selectedHolding.arrayDetails!!)
        // }



        var adapterTotalCash= AdapterPortfolioDetails(arrayDetails,this,1)
        val glm = GridLayoutManager(this, 1)
        rvPortfolioInfo.adapter=adapterTotalCash
        rvPortfolioInfo.layoutManager=glm
    }

    private fun setInvestment(){

        adapterInvestment= AdapterAllInvestment(arrayAllInvestment,this,selectedTab)
        val glm = GridLayoutManager(this, 1)
        rvAllocation.adapter=adapterInvestment
        rvAllocation.layoutManager=glm
        rvAllocation.isNestedScrollingEnabled=false
    }


    private fun setTab(index:Int){
        selectedTab=index
        btTabOverview.setBackgroundResource(0)
        btTabPerformance.setBackgroundResource(0)
        btTabHolding.setBackgroundResource(0)


        AppHelper.setTextColor(this,btTabOverview,R.color.gray_tabs)
        AppHelper.setTextColor(this,btTabPerformance,R.color.gray_tabs)
        AppHelper.setTextColor(this,btTabHolding,R.color.gray_tabs)

        tab_portfolio_overview.visibility=View.GONE
        tabPerformance.visibility=View.GONE
        tab_portfolio_holdings.visibility=View.GONE


        when (index) {
            AppConstants.TAB_PORTFOLIO_OVERVIEW -> {
                btTabOverview.setBackgroundResource(R.drawable.background_rectangular_primary)
                AppHelper.setTextColor(this,btTabOverview,R.color.white)
                tab_portfolio_overview.visibility=View.VISIBLE
            }
            AppConstants.TAB_PORTFOLIO_PERFORMANCE -> {
                btTabPerformance.setBackgroundResource(R.drawable.background_rectangular_primary)
                AppHelper.setTextColor(this,btTabPerformance,R.color.white)
                tabPerformance.visibility=View.VISIBLE
                setLineChartData()
                setbardata()
            }
            AppConstants.TAB_PORTFOLIO_HOLDINGS -> {
                btTabHolding.setBackgroundResource(R.drawable.background_rectangular_primary)
                AppHelper.setTextColor(this,btTabHolding,R.color.white)
                tab_portfolio_holdings.visibility=View.VISIBLE
            }

        }

    }


    private fun setHoldings(){
/*        arrayHoldings=ArrayList<Holdings>()
        arrayHoldings.add(Holdings("KIB","KWD 5,069,090","4.09%",true))
        arrayHoldings.add(Holdings("Gulf Bank","KWD 569,090","3.09%",false))
        arrayHoldings.add(Holdings("Salheia RE","KWD 4,069,090","2.09%",true))
        arrayHoldings.add(Holdings("Security name","KWD 3,069,090","5.09%",false))*/
        adapterHolding= AdapterHoldings(arrayHoldings,this,1)
        val glm = GridLayoutManager(this, 1)
        rvPortfolioHoldings.adapter=adapterHolding
        rvPortfolioHoldings.layoutManager=glm


    }


    private fun setTotals(){

        var totalPositive=0.0
        var totalNegative=0.0
        var toTalHolding=0.0



        for (i in arrayTotals.indices){
            toTalHolding+=arrayTotals[i].totalholdingsValue!!
            if(arrayTotals[i].changePercent!! > 0)
                totalPositive += arrayTotals[i].changePercent!!
            else     if(arrayTotals[i].changePercent!! < 0)
                totalNegative += arrayTotals[i].changePercent!!

        }

        try{tvTotalHolding.text=AppHelper.getCurrencyName()+" "+AppHelper.formatNumber(arrayTotals[0].totalholdingsValue!!.toDouble(),AppHelper.ThreeDecimalThousandsSeparator)}catch (e:Exception){}


        try{tvIncreasePercentage.text= arrayTotals[0].changePercent.toString()}catch (e:Exception){}
        try{tvDecreasePercentage.text= arrayTotals[0].indexChangePercent.toString()}catch (e:Exception){}
        try{tvBoursaName.text=arrayTotals[0].mainIndexNameEn}catch (e:Exception){}


    }



    private fun getInvestmentAsset(showLoading:Boolean){
        if(showLoading)
           loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getInvestmentPortfolioByAsset(
                portfolioId,
                MyApplication.currencyId,
                date
            )?.enqueue(object : Callback<java.util.ArrayList<ResponseAllInvestment>> {
                override fun onResponse(call: Call<java.util.ArrayList<ResponseAllInvestment>>, response: Response<java.util.ArrayList<ResponseAllInvestment>>) {
                    try{
                        if(showLoading)
                           loading.visibility=View.GONE
                        arrayAllInvestment.clear()
                        arrayAllInvestment.addAll(response.body()!!)
                        adapterInvestment.notifyDataSetChanged()

                        setPieChart(AppConstants.TAB_ASSET)
                        setData()


                    }catch (E: java.lang.Exception){
                        Log.wtf("exception",E.toString())
                    }
                }
                override fun onFailure(call: Call<java.util.ArrayList<ResponseAllInvestment>>, throwable: Throwable) {
                   if(showLoading)
                    loading.visibility=View.GONE
                }
            })

    }


    private fun getInvestmentCountry(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getInvestmentPortfolioByCountry(
                portfolioId,
                MyApplication.currencyId,
                date
            )?.enqueue(object : Callback<java.util.ArrayList<ResponseAllInvestment>> {
                override fun onResponse(call: Call<java.util.ArrayList<ResponseAllInvestment>>, response: Response<java.util.ArrayList<ResponseAllInvestment>>) {
                    try{
                        loading.visibility=View.GONE
                        arrayAllInvestment.clear()
                        arrayAllInvestment.addAll(response.body()!!)
                        adapterInvestment.notifyDataSetChanged()

                        setPieChart(AppConstants.TAB_GEOGRAPHICAL)
                        setData()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<java.util.ArrayList<ResponseAllInvestment>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }


    private fun getInvestmentSector(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getInvestmentsPortfolioBySector(
                portfolioId,
                MyApplication.currencyId,
                date
            )?.enqueue(object : Callback<java.util.ArrayList<ResponseAllInvestment>> {
                override fun onResponse(call: Call<java.util.ArrayList<ResponseAllInvestment>>, response: Response<java.util.ArrayList<ResponseAllInvestment>>) {
                    try{
                        loading.visibility=View.GONE
                        arrayAllInvestment.clear()
                        arrayAllInvestment.addAll(response.body()!!)
                        adapterInvestment.notifyDataSetChanged()

                        setPieChart(AppConstants.TAB_SECTOR)
                        setData()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<java.util.ArrayList<ResponseAllInvestment>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }


    private fun getInvestmentCurrency(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getInvestmentPortfolioByCurrency(
                portfolioId,
                MyApplication.currencyId,
                date
            )?.enqueue(object : Callback<java.util.ArrayList<ResponseAllInvestment>> {
                override fun onResponse(call: Call<java.util.ArrayList<ResponseAllInvestment>>, response: Response<java.util.ArrayList<ResponseAllInvestment>>) {
                    try{
                        loading.visibility=View.GONE
                        arrayAllInvestment.clear()
                        arrayAllInvestment.addAll(response.body()!!)
                        adapterInvestment.notifyDataSetChanged()

                        setPieChart(AppConstants.TAB_CURRENCY)
                        setData()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<java.util.ArrayList<ResponseAllInvestment>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }



    private fun setbardata() {
        if(arrayAccountPerformance.size>0) {
            tvNoDataBarChart.visibility = View.GONE
            val start = 1f
            val values = java.util.ArrayList<BarEntry>()
            barChart.removeAllViews()

            var xAxisLabel = arrayListOf<String>()
            for (i in 0 until arrayAccountPerformance.size) {
                if (selectedPerformanceType == AppConstants.PERFORMANCE_TYPE_YS)
                    values.add(
                        BarEntry(
                            i.toFloat(),
                            arrayAccountPerformance[i].siReturn!!.toFloat()
                        )
                    )
                else if (selectedPerformanceType == AppConstants.PERFORMANCE_TYPE_MTD)
                    values.add(
                        BarEntry(
                            i.toFloat(),
                            arrayAccountPerformance[i].mtdReturn!!.toFloat()
                        )
                    )
                else if (selectedPerformanceType == AppConstants.PERFORMANCE_TYPE_YTD)
                    values.add(
                        BarEntry(
                            i.toFloat(),
                            arrayAccountPerformance[i].ytdReturn!!.toFloat()
                        )
                    )


                xAxisLabel.add(AppHelper.getMonth(arrayAccountPerformance[i].date!!))
            }

            val set1: BarDataSet


            set1 = BarDataSet(values, "The year 2017")

            set1.setDrawIcons(false)

/*
            val startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light)
            val startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light)
            val startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light)
            val startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light)
            val startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light)
            val endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark)
            val endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple)
            val endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark)
            val endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark)
            val endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark)

            val gradientFills = ArrayList<Fill>()
            gradientFills.add(Fill(startColor1, endColor1))
            gradientFills.add(Fill(startColor2, endColor2))
            gradientFills.add(Fill(startColor3, endColor3))
            gradientFills.add(Fill(startColor4, endColor4))
            gradientFills.add(Fill(startColor5, endColor5))

            set1.setFills(gradientFills)
*/

            val dataSets = java.util.ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
            data.setValueTextSize(10f)

            data.barWidth = 0.9f
            barChart.description.text = ""
            barChart.setNoDataText("No data available")
            barChart.setData(data)
            barChart.invalidate()
        }else{
            tvNoDataBarChart.visibility=View.VISIBLE
        }
    }

    private fun setData() {
        val entries = java.util.ArrayList<PieEntry>()


        for (i in 0 until arrayAllInvestment.size) {
            entries.add(PieEntry(arrayAllInvestment[i].totalPercentage!!.toFloat(),arrayAllInvestment[i].typeEn))

        }

        val dataSet = PieDataSet(entries, "")
        dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE;
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE;
        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 0f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors

        val colors = java.util.ArrayList<Int>()
        val myChartsColos = intArrayOf(
            Color.rgb(128,0,0),
            Color.rgb(128,128,128),
            Color.rgb(0,128,0),
            Color.rgb(128,0,0),
            Color.rgb(255,0,0),
            Color.rgb (255,127,80),
            Color.rgb(250,128,114),
            Color.rgb(189,183,107),
            Color.rgb(154,205,50),
            Color.rgb(50,205,50),
            Color.rgb	(47,79,79),
            Color.rgb(0,128,128),
            Color.rgb(0,128,128),
            Color.rgb(65,105,225),
            Color.rgb(199,21,133),
            Color.rgb(139,69,19),
            Color.rgb(112,128,144),
            Color.rgb(105,105,105),
            Color.rgb(30,144,255)
        )

        for (c in myChartsColos)
            colors.add(c)

  /*      val colors = java.util.ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())*/

        dataSet.colors = colors
        //dataSet.selectionShift = 0f

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLACK)



        try{
            for (i in 0 until arrayAllInvestment.size) {
                if(i < arrayAllInvestment.size)
                    arrayAllInvestment[i].color=dataSet.colors[i]
                else
                    arrayAllInvestment[i].color=dataSet.colors[0]
            }
        }catch (e: Exception){
            Log.wtf("exception",e.toString())
        }


        pieChart.data = data

        // undo all highlights
        pieChart.highlightValues(null)

        pieChart.invalidate()
    }

    private fun setPieChart(type:Int){
        pieChart.setUsePercentValues(true)

        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(25f, 10f, 25f, 5f)
        pieChart.dragDecelerationFrictionCoef = 0.95f
        //  chart.centerText = generateCenterSpannableText()
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.holeRadius = 60f
        pieChart.transparentCircleRadius = 21f
        pieChart.setDrawCenterText(true)


/*        when (type) {
            AppConstants.TAB_ASSET -> chart.centerText=getString(R.string.asset_class)
            AppConstants.TAB_GEOGRAPHICAL -> chart.centerText=getString(R.string.geographical)
            AppConstants.TAB_SECTOR -> chart.centerText=getString(R.string.sector)
            AppConstants.TAB_CURRENCY -> chart.centerText=getString(R.string.currency)

            //chart.setOnChartValueSelectedListener(this)
        }*/
/*        when (type) {
            AppConstants.TAB_HOME_ALLOCATION -> chart.centerText=""
         }*/

        pieChart.rotationAngle = 0f
        pieChart.isRotationEnabled = true
        pieChart.isHighlightPerTapEnabled = true

        //chart.setOnChartValueSelectedListener(this)
        pieChart.animateY(1400, Easing.EaseInOutQuad)
        val l = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.legend.isEnabled=true

    }


    private fun setSubTab(index: Int){
        AppHelper.setTextColor(this,tvTabAsset,R.color.gray_text_light)
        AppHelper.setTextColor(this,tvTabGeographical,R.color.gray_text_light)
        AppHelper.setTextColor(this,tvTabSector,R.color.gray_text_light)
        AppHelper.setTextColor(this,tvTabCurrency,R.color.gray_text_light)
        when (index) {
            AppConstants.TAB_ASSET -> { AppHelper.setTextColor(this,tvTabAsset,R.color.text_blue_normal)}
            AppConstants.TAB_GEOGRAPHICAL -> { AppHelper.setTextColor(this,tvTabGeographical,R.color.text_blue_normal)}
            AppConstants.TAB_SECTOR -> {AppHelper.setTextColor(this,tvTabSector,R.color.text_blue_normal)}
            AppConstants.TAB_CURRENCY -> {AppHelper.setTextColor(this,tvTabCurrency,R.color.text_blue_normal)}
        }
    }

    private fun setPerformance(){
        tvYearToDatePercentage.setTypeface(AppHelper.getTypeFace(this), Typeface.NORMAL)
        tvYtdTitle.setTypeface(AppHelper.getTypeFace(this), Typeface.NORMAL)

        tvMonthToDatePercentage.setTypeface(AppHelper.getTypeFace(this), Typeface.NORMAL)
        tvMtdTitle.setTypeface(AppHelper.getTypeFace(this), Typeface.NORMAL)

        tvYesterdayPercentage.setTypeface(AppHelper.getTypeFace(this), Typeface.NORMAL)
        tvYsTitle.setTypeface(AppHelper.getTypeFace(this), Typeface.NORMAL)

        if(selectedPerformanceType==AppConstants.PERFORMANCE_TYPE_YS){
            tvYesterdayPercentage.setTypeface(AppHelper.getTypeFaceBold(this), Typeface.BOLD)
            tvYsTitle.setTypeface(AppHelper.getTypeFaceBold(this), Typeface.BOLD)
        }else if(selectedPerformanceType==AppConstants.PERFORMANCE_TYPE_MTD){
            tvMonthToDatePercentage.setTypeface(AppHelper.getTypeFaceBold(this), Typeface.BOLD)
            tvMtdTitle.setTypeface(AppHelper.getTypeFaceBold(this), Typeface.BOLD)
        }else if(selectedPerformanceType==AppConstants.PERFORMANCE_TYPE_YTD){
            tvYearToDatePercentage.setTypeface(AppHelper.getTypeFaceBold(this), Typeface.BOLD)
            tvYtdTitle.setTypeface(AppHelper.getTypeFaceBold(this), Typeface.BOLD)
        }
    }

    private fun setLineChartData() {

        if(arrayAccountPerformance.size>0){
            tvNoDataLineChart.visibility=View.GONE
        lineChart.removeAllViews()
        val values = java.util.ArrayList<Entry>()
        var xAxisLabel = arrayListOf<String>()
        for (i in 0 until arrayAccountPerformance.size) {
            if(selectedPerformanceType==AppConstants.PERFORMANCE_TYPE_YS)
                values.add(Entry(i.toFloat(),arrayAccountPerformance[i].siReturn!!.toFloat()))
            else if(selectedPerformanceType==AppConstants.PERFORMANCE_TYPE_MTD)
                values.add(Entry(i.toFloat(),arrayAccountPerformance[i].mtdReturn!!.toFloat()))
            else  if(selectedPerformanceType==AppConstants.PERFORMANCE_TYPE_YTD)
                values.add(Entry(i.toFloat(),arrayAccountPerformance[i].ytdReturn!!.toFloat()))


            xAxisLabel.add(  AppHelper.getMonth(arrayAccountPerformance[i].date!!))
        }

        val set1: LineDataSet



        set1 = LineDataSet(values, "")
        set1.setDrawIcons(false)
        set1.color = Color.BLACK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            set1.setCircleColor(this.getColor(R.color.gold_text))
        }else
            set1.setCircleColor(this.resources.getColor(R.color.gold_text))
        set1.lineWidth = 1f
        set1.circleRadius = 6f
        set1.setDrawCircleHole(false)
        set1.formLineWidth = 1f
        set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        set1.formSize = 15f
        set1.valueTextSize = 9f
        set1.setDrawFilled(false)
        set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> lineChart.axisLeft.axisMinimum }

        if (Utils.getSDKInt() >= 18) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.rectangular_light_red)
            set1.fillDrawable = drawable
        } else {
            set1.fillColor = Color.BLACK
        }



        set1.mode=LineDataSet.Mode.CUBIC_BEZIER
        val dataSets = java.util.ArrayList<ILineDataSet>()
        dataSets.add(set1)
        val data = LineData(dataSets)

        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel);


        lineChart.axisLeft.setDrawLabels(true)
        lineChart.axisRight.setDrawLabels(false)
        lineChart.description.text=""
        lineChart.setNoDataText("No data available")
        lineChart.data = data
        lineChart.invalidate()}else{
            tvNoDataLineChart.visibility=View.VISIBLE
        }
    }


    private fun getAccountPerformance(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getAccountPerformance(
                MyApplication.clientId,
                MyApplication.currencyId,
                "2020-04-18"
                //  date
            )?.enqueue(object : Callback<java.util.ArrayList<ResponseAccountPerformance>> {
                override fun onResponse(call: Call<java.util.ArrayList<ResponseAccountPerformance>>, response: Response<java.util.ArrayList<ResponseAccountPerformance>>) {
                    try{
                        loading.visibility=View.GONE
                        arrayAccountPerformance.clear()
                        arrayAccountPerformance.addAll(response.body()!!)
                        setLineChartData()
                        setbardata()

                    }catch (E: java.lang.Exception){
                        Log.wtf("exception",E.toString())
                    }
                }
                override fun onFailure(call: Call<java.util.ArrayList<ResponseAccountPerformance>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }
}
