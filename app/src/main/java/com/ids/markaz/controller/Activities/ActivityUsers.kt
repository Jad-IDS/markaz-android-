package com.ids.markaz.controller.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.GridLayoutManager
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterGeneralSpinner
import com.ids.markaz.controller.Adapters.AdapterUserItems
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.ItemSpinner
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar_general.*
import kotlinx.android.synthetic.main.toolbar_general.tvToolbarTitle


class ActivityUsers : AppCompactBase(),RVOnItemClickListener{







    var AdapterUser : AdapterUserItems ?=null
    private var arrayCurrencies= java.util.ArrayList<ItemSpinner>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        init()
        setSpinnerCurrencies()


        if(MyApplication.arrayUsersClients.size>0) {
            val glm = GridLayoutManager(this, 1)
            rvUsers.layoutManager = glm
            AdapterUser = AdapterUserItems(MyApplication.arrayUsersClients, this)
            rvUsers.adapter = AdapterUser
        }else{
            rvUsers.visibility=View.GONE
        }







    }


    private fun init(){

        try{MyApplication.arrayUsersClients[0].selected = true}catch (e:Exception){}
        supportActionBar!!.hide()
        btEnter.setOnClickListener{
            if(MyApplication.currencyId==0){
                AppHelper.createDialog(this,getString(R.string.plz_choose_currency))
            }else {

                val i = Intent(this, ActivityHomeTabs::class.java)
                startActivity(i)
                finish()
            }

        }

    }


    private fun setSpinnerCurrencies(){

        arrayCurrencies.clear()
        arrayCurrencies.add(ItemSpinner(0,getString(R.string.choose_currency)))

        for (i in MyApplication.arrayCurrencies.indices)
            arrayCurrencies.add(
                if(MyApplication.languageCode == AppConstants.LANG_ENGLISH){
                ItemSpinner(
                    MyApplication.arrayCurrencies[i].id,
                    MyApplication.arrayCurrencies[i].shortNameEn)
                }else{
                    ItemSpinner(
                        MyApplication.arrayCurrencies[i].id,
                        MyApplication.arrayCurrencies[i].shortNameAr)
                }
            )



        val adapter = AdapterGeneralSpinner(this, R.layout.spinner_text_item,
            AppConstants.START,R.color.black,R.color.black,
            AppConstants.START, arrayCurrencies)
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            spCurrencies!!.adapter = adapter;
            spCurrencies!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                var item=adapter.getItem(position)
                MyApplication.currencyId=item!!.id!!
                MyApplication.selectedCurrencyName=item.name!!
            }

            override fun onNothingSelected(parent: AdapterView<*>){

            }
        }

    }

    override fun onBackPressed() {
        finish()
    }


    override fun onItemClicked(view: View, position: Int) {
        reset()
        MyApplication.arrayUsersClients[position].selected = true
        MyApplication.clientId=MyApplication.arrayUsersClients[position].id!!
        AdapterUser!!.notifyDataSetChanged()

    }

    private fun reset(){
        for(i in MyApplication.arrayUsersClients.indices)
            MyApplication.arrayUsersClients[i].selected=false
    }



    }




