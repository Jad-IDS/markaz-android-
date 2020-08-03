package com.ids.markaz.controller.Adapters




import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication

import com.ids.markaz.model.ResponseAccountSummary
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import java.lang.Exception


import java.util.*


class AdapterPortfolio(val itemPortfolio: ArrayList<ResponseAccountSummary>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterPortfolio.VHprivacy>(),RVOnItemClickListener {
    override fun onItemClicked(view: View, position: Int) {

    }

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_portfolio_new, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {

        if (MyApplication.languageCode==AppConstants.LANG_ENGLISH){
           try{holder.tvPortfolioName.text=itemPortfolio[position].nameEn}catch (e:Exception){}
        }else{
            try{holder.tvPortfolioName.text=itemPortfolio[position].nameAr}catch (e:Exception){}
        }
        try{holder.tvPrice.text=AppHelper.getCurrencyName()+" "+AppHelper.formatNumber(itemPortfolio[position].cashPosition!!.toDouble(),AppHelper.TwoDecimalThousandsSeparator)}catch (e:Exception){
            holder.tvPrice.text=AppHelper.getCurrencyName()+" 0"
        }

        if(itemPortfolio[position].arrayDetails!!.isNotEmpty()){

           var adapterDetails= AdapterPortfolioDetails(itemPortfolio[position].arrayDetails!!,this,2)
            val glm = GridLayoutManager(holder.itemView.context, 1)
            holder.rvPortfolioSub.adapter=adapterDetails
            holder.rvPortfolioSub.layoutManager=glm
            holder.rvPortfolioSub.isNestedScrollingEnabled=false
        }

    }

    override fun getItemCount(): Int {
        return itemPortfolio.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvPortfolioName: TextView = itemView.findViewById(com.ids.markaz.R.id.tvPortfolioName) as TextView
        var tvStatus: TextView = itemView.findViewById(com.ids.markaz.R.id.tvStatus) as TextView
        var tvPrice: TextView = itemView.findViewById(com.ids.markaz.R.id.tvPrice) as TextView
        var rvPortfolioSub: RecyclerView = itemView.findViewById(com.ids.markaz.R.id.rvPortfolioSub) as RecyclerView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
