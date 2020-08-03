package com.ids.markaz.controller.Fragments


import android.content.Intent
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Handler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
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
import com.ids.markaz.controller.Activities.ActivityTopHolding
import com.ids.markaz.controller.Adapters.AdapterAllInvestment
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.controller.MyApplication.Companion.selectedSubTab
import com.ids.markaz.controller.MyApplication.Companion.selectedTab
import com.ids.markaz.model.*
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import com.mikhaellopez.circularprogressbar.CircularProgressBar


import kotlinx.android.synthetic.main.fragment_home_new.*
import kotlinx.android.synthetic.main.home_tab_allocation.*

import kotlinx.android.synthetic.main.home_tab_overview.*
import kotlinx.android.synthetic.main.home_tab_overview.tvCashValue
import kotlinx.android.synthetic.main.home_tab_performance.*
import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.portfolio_tab_overview.*
import kotlinx.android.synthetic.main.sub_tabs.tvTabAsset
import kotlinx.android.synthetic.main.sub_tabs.tvTabCurrency
import kotlinx.android.synthetic.main.sub_tabs.tvTabGeographical
import kotlinx.android.synthetic.main.sub_tabs.tvTabSector
import kotlinx.android.synthetic.main.toolbar_home_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class FragmentHome : Fragment() ,RVOnItemClickListener {

    lateinit var adapterInvestment:AdapterAllInvestment
    var arrayAllInvestment=java.util.ArrayList<ResponseAllInvestment>()

    var arrayAccountPerformance=java.util.ArrayList<ResponseAccountPerformance>()
    private var selectedPerformanceType=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.markaz.R.layout.fragment_home_new, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    override fun onResume() {

        super.onResume()
    }


    fun init(){
        selectedTab=AppConstants.TAB_HOME_OVERVIEW
        setListeners()
        setInvestment()
        getInvestmentAsset()

        getAccountPerformance()
        selectedSubTab=AppConstants.TAB_ASSET
        getInvesmentInfo()
    }


    private fun setListeners(){
        btTabOverview.setOnClickListener{
            setTab(AppConstants.TAB_HOME_OVERVIEW)
        }
        btTabAllocation.setOnClickListener{
            setTab(AppConstants.TAB_HOME_ALLOCATION)

        }
        btTabPerformance.setOnClickListener{
            setTab(AppConstants.TAB_HOME_PERFORMANCE)

        }



        tvTabAsset.setOnClickListener{

            selectedSubTab=AppConstants.TAB_ASSET
            setSubTab(AppConstants.TAB_ASSET)
            getInvestmentAsset()

        }
        tvTabGeographical.setOnClickListener{
            selectedSubTab=AppConstants.TAB_GEOGRAPHICAL
            setSubTab(AppConstants.TAB_GEOGRAPHICAL)
            getInvestmentCountry()
        }
        tvTabSector.setOnClickListener{
            selectedSubTab=AppConstants.TAB_SECTOR
            setSubTab(AppConstants.TAB_SECTOR)
            getInvestmentSector()
        }
        tvTabCurrency.setOnClickListener{
            selectedSubTab=AppConstants.TAB_CURRENCY
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
            startActivity(Intent(activity!!,ActivityTopHolding::class.java))
        }
    }

    private fun setPerformance(){
        tvYearToDatePercentage.setTypeface(AppHelper.getTypeFace(activity!!), Typeface.NORMAL)
        tvYtdTitle.setTypeface(AppHelper.getTypeFace(activity!!), Typeface.NORMAL)

        tvMonthToDatePercentage.setTypeface(AppHelper.getTypeFace(activity!!), Typeface.NORMAL)
        tvMtdTitle.setTypeface(AppHelper.getTypeFace(activity!!), Typeface.NORMAL)

        tvYesterdayPercentage.setTypeface(AppHelper.getTypeFace(activity!!), Typeface.NORMAL)
        tvYsTitle.setTypeface(AppHelper.getTypeFace(activity!!), Typeface.NORMAL)

        if(selectedPerformanceType==AppConstants.PERFORMANCE_TYPE_YS){
            tvYesterdayPercentage.setTypeface(AppHelper.getTypeFaceBold(activity!!), Typeface.BOLD)
            tvYsTitle.setTypeface(AppHelper.getTypeFaceBold(activity!!), Typeface.BOLD)
        }else if(selectedPerformanceType==AppConstants.PERFORMANCE_TYPE_MTD){
            tvMonthToDatePercentage.setTypeface(AppHelper.getTypeFaceBold(activity!!), Typeface.BOLD)
            tvMtdTitle.setTypeface(AppHelper.getTypeFaceBold(activity!!), Typeface.BOLD)
        }else if(selectedPerformanceType==AppConstants.PERFORMANCE_TYPE_YTD){
            tvYearToDatePercentage.setTypeface(AppHelper.getTypeFaceBold(activity!!), Typeface.BOLD)
            tvYtdTitle.setTypeface(AppHelper.getTypeFaceBold(activity!!), Typeface.BOLD)
        }
    }


    override fun onItemClicked(view: View, position: Int) {


    }

    private fun setSubTab(index: Int){
        AppHelper.setTextColor(activity!!,tvTabAsset,R.color.gray_text_light)
        AppHelper.setTextColor(activity!!,tvTabGeographical,R.color.gray_text_light)
        AppHelper.setTextColor(activity!!,tvTabSector,R.color.gray_text_light)
        AppHelper.setTextColor(activity!!,tvTabCurrency,R.color.gray_text_light)
        when (index) {
            AppConstants.TAB_ASSET -> { AppHelper.setTextColor(activity!!,tvTabAsset,R.color.text_blue_normal)}
            AppConstants.TAB_GEOGRAPHICAL -> { AppHelper.setTextColor(activity!!,tvTabGeographical,R.color.text_blue_normal)}
            AppConstants.TAB_SECTOR -> {AppHelper.setTextColor(activity!!,tvTabSector,R.color.text_blue_normal)}
            AppConstants.TAB_CURRENCY -> {AppHelper.setTextColor(activity!!,tvTabCurrency,R.color.text_blue_normal)}
       }
    }



    private fun setTab(index:Int){
        selectedTab=index
        btTabAllocation.setBackgroundResource(0)
        btTabOverview.setBackgroundResource(0)
        btTabPerformance.setBackgroundResource(0)


        AppHelper.setTextColor(activity!!,btTabAllocation,R.color.tab_text_colo)
        AppHelper.setTextColor(activity!!,btTabOverview,R.color.tab_text_colo)
        AppHelper.setTextColor(activity!!,btTabPerformance,R.color.tab_text_colo)

        tab_allocation.visibility=View.GONE
        tabOverview.visibility=View.GONE
        tabPerformance.visibility=View.GONE


        when (index) {
            AppConstants.TAB_HOME_PERFORMANCE -> {
                btTabPerformance.setBackgroundResource(R.drawable.background_rectangular_primary)
                AppHelper.setTextColor(activity!!,btTabPerformance,R.color.white)
                tabPerformance.visibility=View.VISIBLE
                setLineChartData()
                setbardata()
            }
            AppConstants.TAB_HOME_ALLOCATION -> {
                btTabAllocation.setBackgroundResource(R.drawable.background_rectangular_primary)
                AppHelper.setTextColor(activity!!,btTabAllocation,R.color.white)
                tab_allocation.visibility=View.VISIBLE

            }
            AppConstants.TAB_HOME_OVERVIEW -> {
                btTabOverview.setBackgroundResource(R.drawable.background_rectangular_primary)
                AppHelper.setTextColor(activity!!,btTabOverview,R.color.white)
                tabOverview.visibility=View.VISIBLE
            }

        }

    }





    private fun setInvestment(){

        adapterInvestment= AdapterAllInvestment(arrayAllInvestment,this,selectedTab)
        val glm = GridLayoutManager(activity, 1)
        rvAllocation.adapter=adapterInvestment
        rvAllocation.layoutManager=glm
        rvAllocation.isNestedScrollingEnabled=false
    }




    private fun setPieChart(type:Int){
        pieChart.setUsePercentValues(true)

        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(25f, 10f, 25f, 5f)
        pieChart.dragDecelerationFrictionCoef = 0.95f
      //  pieChart.centerText = generateCenterSpannableText()
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.holeRadius = 60f
        pieChart.transparentCircleRadius = 21f
        pieChart.setDrawCenterText(true)


/*        when (type) {
            AppConstants.TAB_ASSET -> pieChart.centerText=getString(R.string.asset_class)
            AppConstants.TAB_GEOGRAPHICAL -> pieChart.centerText=getString(R.string.geographical)
            AppConstants.TAB_SECTOR -> pieChart.centerText=getString(R.string.sector)
            AppConstants.TAB_CURRENCY -> pieChart.centerText=getString(R.string.currency)

            //pieChart.setOnChartValueSelectedListener(this)
        }*/
/*        when (type) {
            AppConstants.TAB_HOME_ALLOCATION -> pieChart.centerText=""
         }*/

        pieChart.rotationAngle = 0f
        pieChart.isRotationEnabled = true
        pieChart.isHighlightPerTapEnabled = true

        //pieChart.setOnChartValueSelectedListener(this)
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




    private fun setData() {
        val entries = ArrayList<PieEntry>()


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

        val colors = ArrayList<Int>()
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

/*        for (c in ColorTemplate.VORDIPLOM_COLORS)
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
        }catch (e:Exception){
            Log.wtf("exception",e.toString())
        }


        pieChart.data = data

        // undo all highlights
        pieChart.highlightValues(null)

        pieChart.invalidate()
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
            )?.enqueue(object : Callback<ArrayList<ResponseAccountPerformance>> {
                override fun onResponse(call: Call<ArrayList<ResponseAccountPerformance>>, response: Response<ArrayList<ResponseAccountPerformance>>) {

                    try{
                        if(response.code()==401)
                            AppHelper.reLogin(activity!!)}catch (e:Exception){}
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
                override fun onFailure(call: Call<ArrayList<ResponseAccountPerformance>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }



    private fun getInvestmentAsset(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getInvestmentByAsset(
                MyApplication.clientId,
                MyApplication.currencyId,
                date
            )?.enqueue(object : Callback<ArrayList<ResponseAllInvestment>> {
                override fun onResponse(call: Call<ArrayList<ResponseAllInvestment>>, response: Response<ArrayList<ResponseAllInvestment>>) {
                    try{
                        if(response.code()==401)
                            AppHelper.reLogin(activity!!)}catch (e:Exception){}

                    try{
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
                override fun onFailure(call: Call<ArrayList<ResponseAllInvestment>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }


    private fun getInvestmentCountry(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getInvestmentByCountry(
                MyApplication.clientId,
                MyApplication.currencyId,
                date
            )?.enqueue(object : Callback<ArrayList<ResponseAllInvestment>> {
                override fun onResponse(call: Call<ArrayList<ResponseAllInvestment>>, response: Response<ArrayList<ResponseAllInvestment>>) {
                    try{
                        if(response.code()==401)
                            AppHelper.reLogin(activity!!)}catch (e:Exception){}
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
                override fun onFailure(call: Call<ArrayList<ResponseAllInvestment>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }


    private fun getInvestmentSector(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getInvestmentsBySector(
                MyApplication.clientId,
                MyApplication.currencyId,
                date
            )?.enqueue(object : Callback<ArrayList<ResponseAllInvestment>> {
                override fun onResponse(call: Call<ArrayList<ResponseAllInvestment>>, response: Response<ArrayList<ResponseAllInvestment>>) {
                    try{
                        if(response.code()==401)
                            AppHelper.reLogin(activity!!)}catch (e:Exception){}
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
                override fun onFailure(call: Call<ArrayList<ResponseAllInvestment>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }


    private fun getInvestmentCurrency(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getInvestmentByCurrency(
                MyApplication.clientId,
                MyApplication.currencyId,
                date
            )?.enqueue(object : Callback<ArrayList<ResponseAllInvestment>> {
                override fun onResponse(call: Call<ArrayList<ResponseAllInvestment>>, response: Response<ArrayList<ResponseAllInvestment>>) {
                    try{
                        if(response.code()==401)
                            AppHelper.reLogin(activity!!)}catch (e:Exception){}
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
                override fun onFailure(call: Call<ArrayList<ResponseAllInvestment>>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }




    private fun setLineChartData() {


        if(arrayAccountPerformance.size>0){
            tvNoDataLineChart.visibility=View.GONE
        lineChart.removeAllViews()
        val values = ArrayList<Entry>()
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
            set1.setCircleColor(activity!!.getColor(R.color.gold_text))
        }else
            set1.setCircleColor(activity!!.resources.getColor(R.color.gold_text))
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
                val drawable = ContextCompat.getDrawable(activity!!, R.drawable.rectangular_light_red)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }



        set1.mode=LineDataSet.Mode.CUBIC_BEZIER
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1)
            val data = LineData(dataSets)

        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel);


        lineChart.axisLeft.setDrawLabels(true)
        lineChart.axisRight.setDrawLabels(false)
        lineChart.description.text=""
        lineChart.setNoDataText("No data available")
            lineChart.data = data
       // lineChart.description.isEnabled=false

        lineChart.invalidate()




        }else{
            tvNoDataLineChart.visibility=View.VISIBLE
        }
    }







    private fun setbardata() {

        if(arrayAccountPerformance.size>0) {
            tvNoDataBarChart.visibility=View.GONE
            val start = 1f
            val values = ArrayList<BarEntry>()
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
            val startColor1 = ContextCompat.getColor(activity!!, android.R.color.holo_orange_light)
            val startColor2 = ContextCompat.getColor(activity!!, android.R.color.holo_blue_light)
            val startColor3 = ContextCompat.getColor(activity!!, android.R.color.holo_orange_light)
            val startColor4 = ContextCompat.getColor(activity!!, android.R.color.holo_green_light)
            val startColor5 = ContextCompat.getColor(activity!!, android.R.color.holo_red_light)
            val endColor1 = ContextCompat.getColor(activity!!, android.R.color.holo_blue_dark)
            val endColor2 = ContextCompat.getColor(activity!!, android.R.color.holo_purple)
            val endColor3 = ContextCompat.getColor(activity!!, android.R.color.holo_green_dark)
            val endColor4 = ContextCompat.getColor(activity!!, android.R.color.holo_red_dark)
            val endColor5 = ContextCompat.getColor(activity!!, android.R.color.holo_orange_dark)

            val gradientFills = ArrayList<Fill>()
            gradientFills.add(Fill(startColor1, endColor1))
            gradientFills.add(Fill(startColor2, endColor2))
            gradientFills.add(Fill(startColor3, endColor3))
            gradientFills.add(Fill(startColor4, endColor4))
            gradientFills.add(Fill(startColor5, endColor5))

            set1.setFills(gradientFills)
*/

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
            data.setValueTextSize(10f)

            data.barWidth = 0.9f
            barChart.description.text = ""
            barChart.setNoDataText("No data available")
            barChart.setData(data)
            barChart.invalidate()

            //  barChart.description.isEnabled=false

        }else{
            tvNoDataBarChart.visibility=View.VISIBLE
        }

    }


    private fun getInvesmentInfo(){
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())

        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getInvestmentInfo(
                MyApplication.clientId,
                MyApplication.currencyId,
                //"2020-04-18"
                date



            )?.enqueue(object : Callback<ResponseInvestmentInfo> {
                override fun onResponse(call: Call<ResponseInvestmentInfo>, response: Response<ResponseInvestmentInfo>) {
                    try{
                        if(response.code()==401)
                            AppHelper.reLogin(activity!!)}catch (e:Exception){}
                    try{
                        if(response.isSuccessful)
                           setInfo(response.body())
                        else{
                            try{
                                setEmptyInfo()
                            }catch (e:Exception){}
                        }
                    }catch (E: java.lang.Exception){
                       try{setEmptyInfo()}catch (e:Exception){
                           Log.wtf("exception_empty",e.toString())
                       }
                    }
                }
                override fun onFailure(call: Call<ResponseInvestmentInfo>, throwable: Throwable) {
                }
            })

    }

    private fun setEmptyInfo(){
        var clientName=""
        var last_login=""
        try {last_login=MyApplication.expDate}catch (e:Exception){}


        try{tvAccountLastLogin.text=clientName+" | Last login: "+last_login }catch (e:Exception){}
        try{tvDateCenter.text="As of "+last_login}catch (e:Exception){}

        try{tvPayablesValue.text=AppHelper.getCurrencyName()+" 0"}catch (e:Exception){}
        try{tvReceivablesValue.text=AppHelper.getCurrencyName()+" 0"}catch (e:Exception){}


        try{tvTotalAssets.text=AppHelper.getCurrencyName()+" 0"}catch (e:Exception){}
        try{tvTotalLiability.text=AppHelper.getCurrencyName()+" 0"}catch (e:Exception){}


        try{tvTotalPortfolioValue.text=AppHelper.getCurrencyName()+" 0"}catch (e:Exception){ }

        try{tvCashValue.text=AppHelper.getCurrencyName()+" 0"}catch (e:Exception){try{tvCashValue.text="-"}catch (e:Exception){}}
        try{tvHolingValue.text=AppHelper.getCurrencyName()+" 0"}catch (e:Exception){try{tvHolingValue.text="-"}catch (e:Exception){}}

    }

    private fun setInfo(response: ResponseInvestmentInfo?) {

        var clientName=""
        var last_login=""
        try {last_login=MyApplication.expDate}catch (e:Exception){}

        try{
        val item: ResponseUser? = MyApplication.arrayUsersClients.find { it.id == MyApplication.clientId }
        var index=MyApplication.arrayUsersClients.indexOf(item)

        try{clientName=MyApplication.arrayUsersClients[index].nameEn!!}catch (e:Exception){
            clientName=MyApplication.arrayUsersClients[0].nameEn!!
        }}catch (e:Exception){}
        try{tvAccountLastLogin.text=clientName+" | Last login: "+last_login }catch (e:Exception){}
        try{tvDateCenter.text="As of "+last_login}catch (e:Exception){}

        try{tvPayablesValue.text=AppHelper.getCurrencyName()+" "+response!!.payable.toString() }catch (e:Exception){}
        try{tvReceivablesValue.text=AppHelper.getCurrencyName()+" "+response!!.receivable.toString() }catch (e:Exception){}


        try{tvTotalAssets.text=AppHelper.getCurrencyName()+" "+response!!.assetValue.toString() }catch (e:Exception){}
        try{tvTotalLiability.text=AppHelper.getCurrencyName()+" "+response!!.liabilities.toString()}catch (e:Exception){}

        try{setProgress(pbHolding,55f)}catch (e:Exception){}
        try{setProgress(pbCash,45f)}catch (e:Exception){}


        try{tvTotalPortfolioValue.text=AppHelper.getCurrencyName()}catch (e:Exception){
            try{tvTotalPortfolioValue.text=AppHelper.getCurrencyName()+" 0"}catch (e:Exception){}
        }



        try{tvCashValue.text=response!!.cashIndex.toString()}catch (e:Exception){tvCashValue.text="-"}
        try{tvHolingValue.text=response!!.holdingIndex.toString()}catch (e:Exception){tvHolingValue.text="-"}

        try{tvYesterdayPercentage.text=response!!.benchmark.toString()+" %"}catch (e:Exception){tvYesterdayPercentage.text="- %"}
        try{tvYearToDatePercentage.text=response!!.ytdReturn.toString()+" %"}catch (e:Exception){tvYearToDatePercentage.text="- %"}
        try{tvMonthToDatePercentage.text=response!!.mtdReturn.toString()+" %"}catch (e:Exception){tvMonthToDatePercentage.text="- %" }



    }


    private fun setProgress(myProgress: CircularProgressBar,progressValue:Float){
        myProgress.apply {
            progress = progressValue
            setProgressWithAnimation(progressValue, 1000) // =1s
            progressMax = 100f
            progressBarColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity!!.getColor(R.color.green_text)
            }else
                activity!!.resources.getColor(R.color.green_text)
            progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM
            backgroundProgressBarColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity!!.getColor(R.color.gray_light)
            }else
                activity!!.resources.getColor(R.color.gray_light)
            backgroundProgressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM
            progressBarWidth = 2f // in DP
            backgroundProgressBarWidth = 1f // in DP
            roundBorder = true
            startAngle = 180f
            progressDirection = CircularProgressBar.ProgressDirection.TO_LEFT
        }
    }




}


