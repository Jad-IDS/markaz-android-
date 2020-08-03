package com.ids.markaz.controller.Fragments


import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ids.markaz.R
import com.ids.markaz.controller.Activities.*
import com.ids.markaz.controller.Adapters.AdapterGeneralSpinner
import com.ids.markaz.controller.Adapters.AdapterNotifications

import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.controller.MyApplication.Companion.inflated
import com.ids.markaz.model.ItemSpinner
import com.ids.markaz.model.Notifications
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.IOnBackPressed
import kotlinx.android.synthetic.main.activity_settings.*

import kotlinx.android.synthetic.main.toolbar.tvToolbarTitle
import kotlinx.android.synthetic.main.toolbar_general.*
import java.lang.Exception





class FragmentMore : Fragment() , RVOnItemClickListener , IOnBackPressed {





    override fun goBack(): Boolean {

        return inflated
    }

    override fun onItemClicked(view: View, position: Int) {

    }


    private lateinit var adapterNotifications: AdapterNotifications
    private var arrayNotifications=java.util.ArrayList<Notifications>()
    private var arrayCurrenciesSpinner= java.util.ArrayList<ItemSpinner>()
    private var selectedCurrencyId=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.markaz.R.layout.activity_settings, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setSpinnerCurrencies()
        try{
            val item: ItemSpinner? = arrayCurrenciesSpinner.find { it.id == MyApplication.currencyId }
            spCurrencies.setSelection(arrayCurrenciesSpinner.indexOf(item))}catch (e: Exception){}

    }





    private fun init(){


        tvToolbarTitle.text = getString(R.string.more)

        btBack.setOnClickListener {

        }

        inflated = true


        container.visibility = View.GONE
     //   tvToolbarTitle.text=getString(R.string.settings)





        llLogout.setOnClickListener{


            showLogoutDialog(context!!)
        }

        btAbout.setOnClickListener{


            val i = Intent(activity, ActivityAboutUs::class.java)
            startActivity(i)
        }


        btSettings.setOnClickListener{

            val i = Intent(activity, ActivitySettings::class.java)
            startActivity(i)

        }


        btContact.setOnClickListener {

            val i = Intent(activity, ActivityContactUs::class.java)
            startActivity(i)
        }









        swShowNotifications.isChecked = MyApplication.enableNotifications
        swBiometricLogin.isChecked = MyApplication.enableBiometric

        swShowNotifications.setOnCheckedChangeListener { buttonView, isChecked ->
            MyApplication.enableNotifications = isChecked
        }

        swBiometricLogin.setOnCheckedChangeListener { buttonView, isChecked ->
            MyApplication.enableBiometric = isChecked
        }

    }


    private fun showLogoutDialog(context:Context) {

        val builder = AlertDialog.Builder(context)
        builder.setMessage(this.resources.getString(R.string.logout_message))
            .setCancelable(true)
            .setNegativeButton(this.resources.getString(R.string.no)
            ) { dialog, id -> dialog.cancel() }
            .setPositiveButton(this.resources.getString(R.string.yes)) { dialog, id ->
                dialog.cancel()

                val i = Intent(activity, ActivityLogin::class.java)
                startActivity(i)

              //MyApplication.loggedOut = true

                activity!!.finish()



            }
        val alert = builder.create()
        alert.setOnShowListener { arg0 ->
            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            alert.getButton(AlertDialog.BUTTON_NEGATIVE).transformationMethod = null
            alert.getButton(AlertDialog.BUTTON_NEGATIVE).isAllCaps = false

            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            alert.getButton(AlertDialog.BUTTON_POSITIVE).transformationMethod = null
            alert.getButton(AlertDialog.BUTTON_POSITIVE).isAllCaps = false
        }
        alert.show()
    }


    private fun setSpinnerCurrencies(){

        arrayCurrenciesSpinner.clear()

        for (i in MyApplication.arrayCurrencies.indices)
            arrayCurrenciesSpinner.add(ItemSpinner(
                MyApplication.arrayCurrencies[i].id,
                MyApplication.arrayCurrencies[i].nameEn))



        val adapter = AdapterGeneralSpinner(activity!!, R.layout.spinner_text_item,AppConstants.START,R.color.secondary,R.color.black,AppConstants.START, arrayCurrenciesSpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spCurrencies!!.adapter = adapter;
        spCurrencies!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                var item=adapter.getItem(position)
                selectedCurrencyId=item!!.id!!
                MyApplication.currencyId=item.id!!


            }

            override fun onNothingSelected(parent: AdapterView<*>){

            }
        }

    }


}


