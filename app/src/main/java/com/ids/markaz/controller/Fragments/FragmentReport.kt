package com.ids.markaz.controller.Fragments

import android.Manifest
import android.app.DatePickerDialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterGeneralSpinner
import com.ids.markaz.controller.Adapters.AdapterReportTypes

import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.ItemSpinner
import com.ids.markaz.model.ResponseAccountSummary
import com.ids.markaz.model.ResponsePortfolioReport
import com.ids.markaz.model.ResponseUserPortfolio
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.fragment_reports.*

import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.btBack
import kotlinx.android.synthetic.main.toolbar.tvToolbarTitle
import kotlinx.android.synthetic.main.toolbar_general.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class FragmentReport : Fragment() , RVOnItemClickListener {


    private lateinit var fromdatelistener: DatePickerDialog.OnDateSetListener
    private lateinit var fromDateCalendar: Calendar
    private var selectedFromDate = ""

    private lateinit var toDateDateListener: DatePickerDialog.OnDateSetListener
    private lateinit var toDateCalendar: Calendar
    private var selectedToDate = ""

    private var selectedPortfolioId=0
    private var selectedReportType=0
    private var selectedReportnumber=0
    private var reportId=0

    private var arrayFilterItemsReport= java.util.ArrayList<ItemSpinner>()

    private var downloadManager: DownloadManager? = null
    private var currentPosition = 0
    val PERMISSION_WRITE = 3425
    var selectedUrl=""
    var startDateString="2020-05-04"
    private lateinit var adapterReports: AdapterReportTypes
    private var arrayReports= java.util.ArrayList<ResponsePortfolioReport>()
    private var arrayAccountSummary= java.util.ArrayList<ResponseAccountSummary>()
    private var portfolioId=0
    override fun onItemClicked(view: View, position: Int) {
         clearArrayReport()
         arrayReports[position].selected=!arrayReports[position].selected!!
         adapterReports.notifyDataSetChanged()
         reportId=arrayReports[position].id!!
    }
    private var arrayPortfolioSpinner= java.util.ArrayList<ItemSpinner>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.markaz.R.layout.fragment_reports, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    private fun init(){

        tvToolbarTitle.text=getString(R.string.reports)




        if(MyApplication.arrayPortfolioReports.size==0)
            getPortfolioReports()
        else
            setReportTypes()


        setDatePickers()
        setReportFilterSpinner()

        if(MyApplication.arrayUserPortfolios.size>0)
            setSpinnerPortfolio()
        else
            getUserPortfolios()
        //getAccountSummary()

        btView.setOnClickListener{prepareLinkAndDownload(selectedReportType.toString())}

       // btExcel.setOnClickListener{prepareLinkAndDownload(AppConstants.REPORT_EXCEL)}

    }

    private fun clearArrayReport(){
        for (i in arrayReports.indices)
            arrayReports[i].selected=false
    }

    private fun prepareLinkAndDownload(type:String){
        when {
            selectedFromDate.isEmpty() -> Toast.makeText(activity!!,getString(R.string.plz_chs_from_date),
                Toast.LENGTH_LONG).show()
            selectedToDate.isEmpty() -> Toast.makeText(activity!!,getString(R.string.plz_chs_to_date),
                Toast.LENGTH_LONG).show()
            reportId==0 -> Toast.makeText(activity!!,getString(R.string.plz_choose_report),
                Toast.LENGTH_LONG).show()
            else -> {
                selectedUrl=
                    RetrofitClientAuth.BASE_URL+"client/Report/GetClientReport"+  "?id=" + reportId + "&onlineUserId=" + MyApplication.investioUserId + "&portfolios=" + selectedPortfolioId + "&numbers=" + selectedReportnumber + "&clientId=0&fundIds=0&fromDate=" + selectedFromDate + "&toDate=" + selectedToDate + "&currencyId=" + MyApplication.currencyId+ "&bankAccountIds=&tradingAccountIds=&holdingIds=&isEnglish=true&isUsingClosePrice=true&costBasis=MTMNetAVG&isConsolidated=false&renderType=" + type
                Log.wtf("selected_url",selectedUrl)
                startDownload(selectedUrl)
            }
        }
    }


    private fun getUserPortfolios(){
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getUserPortfolios(MyApplication.investioUserId
            )?.enqueue(object : Callback<ArrayList<ResponseUserPortfolio>> {
                override fun onResponse(call: Call<ArrayList<ResponseUserPortfolio>>, response: Response<ArrayList<ResponseUserPortfolio>>) {
                    try{
                        MyApplication.arrayUserPortfolios.clear()
                        MyApplication.arrayUserPortfolios=response.body()!!
                        setSpinnerPortfolio()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseUserPortfolio>>, throwable: Throwable) {
                }
            })

    }


    private fun setDatePickers(){
        fromDateCalendar = Calendar.getInstance()

        fromdatelistener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub



            val startDate = AppHelper.dateFormatReport.parse(startDateString)


                fromDateCalendar.set(Calendar.YEAR, year)
                fromDateCalendar.set(Calendar.MONTH, monthOfYear)
                fromDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                tvDateFrom.text = AppHelper.dateFormatProfile.format(fromDateCalendar.time)
                selectedFromDate = AppHelper.dateFormatReport.format(fromDateCalendar.time)

            if(AppHelper.timeIsBefore(startDateString,selectedFromDate,"yyyy-MM-dd")){
                AppHelper.setTextColor(activity!!,tvDateFrom,R.color.gray_text_light)
                ivCLoseFrom.visibility=View.GONE
            }else{
                AppHelper.setTextColor(activity!!,tvDateFrom,R.color.red)
                ivCLoseFrom.visibility=View.VISIBLE
            }




        }
        tvDateFrom.setOnClickListener{
            DatePickerDialog(activity!!, fromdatelistener, fromDateCalendar.get(Calendar.YEAR), fromDateCalendar.get(
                Calendar.MONTH), fromDateCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }


        toDateCalendar = Calendar.getInstance()
        toDateDateListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            toDateCalendar.set(Calendar.YEAR, year)
            toDateCalendar.set(Calendar.MONTH, monthOfYear)
            toDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            tvDateTo.text = AppHelper.dateFormatProfile.format(toDateCalendar.time)
            selectedToDate = AppHelper.dateFormatReport.format(toDateCalendar.time)
        }
        tvDateTo.setOnClickListener{
            DatePickerDialog(activity!!, toDateDateListener, toDateCalendar.get(Calendar.YEAR), toDateCalendar.get(
                Calendar.MONTH), toDateCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }


        ivCLoseFrom.setOnClickListener{
            ivCLoseFrom.visibility=View.GONE
            AppHelper.setTextColor(activity!!,tvDateFrom,R.color.gray_text_light)
            selectedFromDate=""
            tvDateFrom.text = ""
        }
    }





    private fun setReportFilterSpinner(){
        arrayFilterItemsReport.clear()
        arrayFilterItemsReport.add(ItemSpinner(1,getString(R.string.pdf),1))
        arrayFilterItemsReport.add(ItemSpinner(2,getString(R.string.word),2))
        arrayFilterItemsReport.add(ItemSpinner(3,getString(R.string.excel),3))

        val adapter = AdapterGeneralSpinner(activity!!, R.layout.spinner_text_item,AppConstants.START,R.color.gray_text_dark,R.color.black,AppConstants.START, arrayFilterItemsReport)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spReportType!!.adapter = adapter;
        spReportType!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                var item=adapter.getItem(position)
                selectedReportType=item!!.id!!
                selectedReportnumber=item.number!!

            }

            override fun onNothingSelected(parent: AdapterView<*>){

            }
        }

    }

    private fun getPortfolioReports(){
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getPortfolioReport(
            )?.enqueue(object : Callback<ArrayList<ResponsePortfolioReport>> {
                override fun onResponse(call: Call<ArrayList<ResponsePortfolioReport>>, response: Response<ArrayList<ResponsePortfolioReport>>) {
                    try{
                        if(response.code()==401)
                            AppHelper.reLogin(activity!!)}catch (e: Exception){}
                    try{
                        MyApplication.arrayPortfolioReports.clear()
                        MyApplication.arrayPortfolioReports=response.body()!!
                        setReportTypes()
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponsePortfolioReport>>, throwable: Throwable) {
                }
            })

    }




    override
    fun onResume() {
        super.onResume()
        downloadManager = activity!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        activity!!.registerReceiver(
            onComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        activity!!.registerReceiver(
            onNotificationClick,
            IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED)
        )
    }


    override
    fun onPause() {
        super.onPause()
        activity!!.unregisterReceiver(onComplete)
        activity!!.unregisterReceiver(onNotificationClick)
    }


    override
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){


            PERMISSION_WRITE -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        startDownload(selectedUrl)
                    else
                        Toast.makeText(
                            activity!!,
                            getString(R.string.download_failed),
                            Toast.LENGTH_LONG
                        ).show()
                }
            }
        }




    }



    fun startDownload(url: String) {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!AppHelper.hasPermissions(activity!!, *permissions)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, PERMISSION_WRITE)
            }
        }

        else {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdirs()
            val urlSplit = url.trim { it <= ' ' }.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            Toast.makeText(activity!!, getString(R.string.downloading), Toast.LENGTH_LONG).show()
            //lastDownload =
            downloadManager!!.enqueue(
                DownloadManager.Request(Uri.parse(url))
                    .addRequestHeader("Authorization", "Bearer "+ MyApplication.responseLogin.accessToken.toString())
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(urlSplit[urlSplit.size - 1])
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        urlSplit[urlSplit.size - 1]
                    )
            )
        }
    }

    internal var onComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            Toast.makeText(ctxt, getString(R.string.download_complete), Toast.LENGTH_LONG).show()
        }
    }

    internal var onNotificationClick: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            Toast.makeText(ctxt, getString(R.string.downloading), Toast.LENGTH_LONG).show()
        }
    }

    fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    fun onPageSelected(position: Int) {
        currentPosition = position

    }

    fun onPageScrollStateChanged(state: Int) {

    }

    fun onPageClicked(v: View) {

    }




    private fun setReportTypes(){
        arrayReports.clear()
        arrayReports.addAll(MyApplication.arrayPortfolioReports)
        adapterReports= AdapterReportTypes(arrayReports,this,1)
        val glm = GridLayoutManager(activity!!, 1)
        rvReportTypes.adapter=adapterReports
        rvReportTypes.layoutManager=glm
        rvReportTypes.isNestedScrollingEnabled=false
    }

/*
    private fun getAccountSummary(){
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getPortfolioAccount(
                MyApplication.investioUserId
            )?.enqueue(object : Callback<java.util.ArrayList<ResponseAccountSummary>> {
                override fun onResponse(call: Call<java.util.ArrayList<ResponseAccountSummary>>, response: Response<java.util.ArrayList<ResponseAccountSummary>>) {
                    try{
                        if(response.code()==401)
                            AppHelper.reLogin(activity!!)}catch (e:Exception){}
                    try{

                        arrayAccountSummary.clear()
                        arrayAccountSummary.addAll(response.body()!!)
                        setSpinnerPortfolio()
                        //testing

                    }catch (E: Exception){
                    }
                }
                override fun onFailure(call: Call<java.util.ArrayList<ResponseAccountSummary>>, throwable: Throwable) {
                }
            })

    }
*/



    private fun setSpinnerPortfolio(){
        spPortfolio.visibility=View.VISIBLE
        tvToolbarTitle.visibility=View.GONE
        arrayPortfolioSpinner.clear()
        arrayPortfolioSpinner.add(ItemSpinner(0,getString(R.string.select_portfolio)))

        for (i in MyApplication.arrayUserPortfolios.indices)
            arrayPortfolioSpinner.add(ItemSpinner(
                MyApplication.arrayUserPortfolios[i].id,
                MyApplication.arrayUserPortfolios[i].nameEn))



        val adapter = AdapterGeneralSpinner(activity!!, R.layout.spinner_text_item,AppConstants.START,R.color.white,R.color.black,AppConstants.START, arrayPortfolioSpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spPortfolio!!.adapter = adapter;
        spPortfolio!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                var item=adapter.getItem(position)
                selectedPortfolioId=item!!.id!!




            }

            override fun onNothingSelected(parent: AdapterView<*>){

            }
        }

    }

}


