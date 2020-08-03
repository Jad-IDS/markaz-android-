package com.ids.markaz.controller.Activities


import android.os.Bundle
import android.text.Html
import android.view.View
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Base.ActivityBase
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.ResponseHtmlContent
import com.ids.markaz.model.ResponseUpdateDevice
import com.ids.markaz.model.ResponseUserPortfolio
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.toolbar_general.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ActivityAboutUs : AppCompactBase(){






    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        init()
        getHtmlContent()
    }

    fun init() {

        supportActionBar!!.hide()
        tvToolbarTitle.text = getString (R.string.about)
        btBack.visibility = View.VISIBLE

        btBack.setOnClickListener {

            onBackPressed()
        }
    }

    private fun getHtmlContent(){
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getHtmlContent(MyApplication.investioUserId
            )?.enqueue(object : Callback<ArrayList<ResponseHtmlContent>> {
                override fun onResponse(call: Call<ArrayList<ResponseHtmlContent>>, response: Response<ArrayList<ResponseHtmlContent>>) {
                    try{
                        MyApplication.arrayHTMLContent.clear()
                        MyApplication.arrayHTMLContent.addAll(response.body()!!)
                        for (i in MyApplication.arrayHTMLContent!!.indices){
                            if(MyApplication.arrayHTMLContent[i].name.toString().toLowerCase()=="about us"){
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                    tvAbout.text = Html.fromHtml(MyApplication.arrayHTMLContent[i].htmlText,Html.FROM_HTML_MODE_LEGACY);
                                } else {
                                    tvAbout.text = Html.fromHtml(MyApplication.arrayHTMLContent[i].htmlText);
                                }
                            }
                        }
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ArrayList<ResponseHtmlContent>>, throwable: Throwable) {
                }
            })

    }
}