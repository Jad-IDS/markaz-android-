package com.ids.markaz.controller.Fragments
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.*
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.*
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_acitivity_trans.*
import kotlinx.android.synthetic.main.loading_trans.*
import kotlinx.android.synthetic.main.toolbar.tvToolbarTitle
import kotlinx.android.synthetic.main.toolbar_general.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FragmentActivityTransactions : Fragment() ,RVOnItemClickListener {
    override fun onItemClicked(view: View, position: Int) {
            arrayTrans[position].expanded=!arrayTrans[position].expanded!!
            adapterActivityTrans.notifyItemChanged(position)
    }
    private var selectedTab=0
    private var filterSelectedId=0
    private var portfolioId=0

    private var arrayPortfolioSpinner= java.util.ArrayList<ItemSpinner>()
    private var arrayAccountSummary= java.util.ArrayList<ResponseAccountSummary>()

    private var arrayFilterItems= java.util.ArrayList<ItemSpinner>()

    private lateinit var adapterActivityTrans: AdapterActivityTrans

    private var arrayTrans=java.util.ArrayList<ResponseTransaction>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.markaz.R.layout.fragment_acitivity_trans, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        getAccountSummary()
        getActivityTrans()


    }


    private fun init(){

        tvToolbarTitle.text=getString(R.string.activities_transactions)



    }


    private fun setListeners(){
        btTabLastday.setOnClickListener{
            setTab(AppConstants.TAB_LAST_DAY)
        }
        btTabWtd.setOnClickListener{
            setTab(AppConstants.TAB_WTD)

        }
        btTabMtd.setOnClickListener{
            setTab(AppConstants.TAB_MTD)

        }
    }


    private fun setTab(index:Int){
        selectedTab=index
        btTabLastday.setBackgroundResource(0)
        btTabWtd.setBackgroundResource(0)
        btTabMtd.setBackgroundResource(0)


        AppHelper.setTextColor(activity!!,btTabLastday,R.color.gray_tabs)
        AppHelper.setTextColor(activity!!,btTabWtd,R.color.gray_tabs)
        AppHelper.setTextColor(activity!!,btTabMtd,R.color.gray_tabs)



        when (index) {
            AppConstants.TAB_LAST_DAY -> {
                btTabLastday.setBackgroundResource(R.drawable.background_rectangular_primary)
                AppHelper.setTextColor(activity!!,btTabLastday,R.color.white)
                getActivityTrans()
            }
            AppConstants.TAB_WTD -> {
                btTabWtd.setBackgroundResource(R.drawable.background_rectangular_primary)
                AppHelper.setTextColor(activity!!,btTabWtd,R.color.white)
                getActivityTrans()
            }
            AppConstants.TAB_MTD -> {
                btTabMtd.setBackgroundResource(R.drawable.background_rectangular_primary)
                AppHelper.setTextColor(activity!!,btTabMtd,R.color.white)
                getActivityTrans()
            }

        }

    }


    private fun setActivityTransactions(){
        adapterActivityTrans= AdapterActivityTrans(arrayTrans,this,1)
        val glm = GridLayoutManager(activity!!, 1)
        rvActivityTransactions.adapter=adapterActivityTrans
        rvActivityTransactions.layoutManager=glm
        rvActivityTransactions.isNestedScrollingEnabled=false

    }


    private fun getActivityTrans(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getTransactions(
                portfolioId,
                MyApplication.currencyId,
                date,
                //"2019-11-01",
                ""
            )?.enqueue(object : Callback<ArrayList<ResponseTransaction>> {
                override fun onResponse(call: Call<ArrayList<ResponseTransaction>>, response: Response<ArrayList<ResponseTransaction>>) {
                    try{
                        if(response.code()==401)
                            AppHelper.reLogin(activity!!)}catch (e:Exception){}
                    try{loading.visibility=View.GONE}catch (e:Exception){}
                    try{

                        arrayTrans.clear()
                        arrayTrans.addAll(response.body()!!)
                        for (i in arrayTrans.indices){

                            arrayTrans[i].arrayDetails!!.add(PortfolioDetails(getString(R.string.quantity),arrayTrans[i].quantity.toString(),""))
                            arrayTrans[i].arrayDetails!!.add(PortfolioDetails(getString(R.string.unit_price),arrayTrans[i].unitPrice.toString(),""))
                            arrayTrans[i].arrayDetails!!.add(PortfolioDetails(getString(R.string.avarage_cost),arrayTrans[i].avgCost.toString(),"-"))
                            arrayTrans[i].arrayDetails!!.add(PortfolioDetails(getString(R.string.realized_gl),arrayTrans[i].realizedPL.toString(),"-"))

                        }
                        setActivityTransactions()

                    }catch (E: java.lang.Exception){

                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseTransaction>>, throwable: Throwable) {
                    try{loading.visibility=View.GONE}catch (e:Exception){}
                }
            })

    }




/*    private fun getActivityTrans(){
        loading.visibility=View.VISIBLE
        var date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getPortfolioAccount(
                MyApplication.investioUserId
            )?.enqueue(object : Callback<java.util.ArrayList<ResponseAccountSummary>> {
                override fun onResponse(call: Call<java.util.ArrayList<ResponseAccountSummary>>, response: Response<java.util.ArrayList<ResponseAccountSummary>>) {
                    try{
                        loading.visibility=View.GONE
                        arrayTrans.clear()
                        arrayTrans.addAll(response.body()!!)
                        //testing
                        for (i in arrayTrans.indices){

                            arrayTrans[i].arrayDetails!!.add(PortfolioDetails("Holdings",arrayTrans[i].holdingPosition.toString(),arrayTrans[i].holdingPositionPercent.toString()))
                            arrayTrans[i].arrayDetails!!.add(PortfolioDetails("Cash available",arrayTrans[i].cashPosition.toString(),arrayTrans[i].cashPositionPercent.toString()))
                            arrayTrans[i].arrayDetails!!.add(PortfolioDetails("Payables/receivables","-","-"))


                        }

                        setActivityTransactions()
                    }catch (E: Exception){
                    }
                }
                override fun onFailure(call: Call<java.util.ArrayList<ResponseAccountSummary>>, throwable: Throwable) {
                }
            })

    }*/


    private fun setSpinnerPortfolio(){
        spPortfolio.visibility=View.VISIBLE
        tvToolbarTitle.visibility=View.GONE
        arrayPortfolioSpinner.clear()
        arrayPortfolioSpinner.add(ItemSpinner(0,getString(R.string.select_portfolio)))


        for (i in arrayAccountSummary.indices){
            if(MyApplication.languageCode==AppConstants.LANG_ENGLISH){
            arrayPortfolioSpinner.add(ItemSpinner(
                arrayAccountSummary[i].id,
                arrayAccountSummary[i].nameEn))
            }else{
                arrayPortfolioSpinner.add(ItemSpinner(
                    arrayAccountSummary[i].id,
                    arrayAccountSummary[i].nameAr))
            }
        }



        val adapter = AdapterGeneralSpinner(activity!!, R.layout.spinner_text_item,AppConstants.START,R.color.white,R.color.black,AppConstants.CENTER, arrayPortfolioSpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spPortfolio!!.adapter = adapter;
        spPortfolio!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                var item=adapter.getItem(position)
                portfolioId=item!!.id!!
               // MyApplication.currencyId=item.id!!
                getActivityTrans()


            }

            override fun onNothingSelected(parent: AdapterView<*>){

            }
        }

    }



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


}


