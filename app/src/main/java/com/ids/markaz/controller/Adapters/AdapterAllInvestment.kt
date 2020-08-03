package com.ids.markaz.controller.Adapters


import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication

import com.ids.markaz.model.Asset
import com.ids.markaz.model.ResponseAllInvestment
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import java.lang.Exception


import java.util.*


class AdapterAllInvestment(val itemAsset: ArrayList<ResponseAllInvestment>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterAllInvestment.VHprivacy>() {

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_portfolio_new_sub, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {

        when (MyApplication.selectedSubTab) {
            AppConstants.TAB_ASSET ->{
                if (MyApplication.languageCode == AppConstants.LANG_ENGLISH) {
                    try{holder.tvTitle.text = itemAsset[position].typeEn}catch (e:Exception){}
                }else{
                    try{holder.tvTitle.text = itemAsset[position].typeAr}catch (e:Exception){}
                }
            }
            AppConstants.TAB_GEOGRAPHICAL ->{
                   if (MyApplication.languageCode == AppConstants.LANG_ENGLISH) {
                try{holder.tvTitle.text = itemAsset[position].countryEn}catch (e:Exception){}}
                   else{
                        try{holder.tvTitle.text = itemAsset[position].countryAr}catch (e:Exception){}
                    }
            }
            AppConstants.TAB_SECTOR -> {
                if (MyApplication.languageCode == AppConstants.LANG_ENGLISH) {
                try{holder.tvTitle.text = itemAsset[position].sectorEn}catch (e:Exception){}}else{
                    try{holder.tvTitle.text = itemAsset[position].sectorAr}catch (e:Exception){}
                }

            }
            AppConstants.TAB_CURRENCY -> {
                if (MyApplication.languageCode == AppConstants.LANG_ENGLISH) {
                try{holder.tvTitle.text = itemAsset[position].currencyEn}catch (e:Exception){}}else{
                    try{holder.tvTitle.text = itemAsset[position].currencyAr}catch (e:Exception){}
                }
            }
        }

        try{holder.tvPercentage.text=String.format("%.2f", itemAsset[position].totalPercentage!!*100)+" %"}catch (e:Exception){}
        try{holder.tvTitle.setTextColor(itemAsset[position].color!!)}catch (e:Exception){}
       // try{AppHelper.setTextColor(holder.itemView.context,holder.tvPercentage,itemAsset[position].color!!)}catch (e:Exception){}
        try{holder.tvPrice.text=AppHelper.getCurrencyName()+" "+AppHelper.formatNumber(itemAsset[position].value!!.toDouble()!!,AppHelper.ThreeDecimalThousandsSeparator)}catch (e:Exception){}


    }

    override fun getItemCount(): Int {
        return itemAsset.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvTitle: TextView = itemView.findViewById(com.ids.markaz.R.id.tvTitleSub) as TextView
        var tvPercentage: TextView = itemView.findViewById(com.ids.markaz.R.id.tvPercentage) as TextView
        var tvPrice: TextView = itemView.findViewById(com.ids.markaz.R.id.tvPrice) as TextView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
