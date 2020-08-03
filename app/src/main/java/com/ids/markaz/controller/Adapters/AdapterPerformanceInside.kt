package com.ids.markaz.controller.Adapters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication

import com.ids.markaz.custom.CustomTextViewMedium
import com.ids.markaz.model.*
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import java.lang.Exception


import java.util.*


class AdapterPerformanceInside(val itemPortfolio: ArrayList<ResponsePerformanceInside>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterPerformanceInside.VHprivacy>() {

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(R.layout.item_performance_inside, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {
        try{holder.tvHolding.text=itemPortfolio[position].nameEn}catch (e:Exception){}

        try{holder.tvMvKd.text=String.format("%.2f",itemPortfolio[position].performance)}catch (e:Exception){}

        if(itemPortfolio[position].performance!!<0)
            try{AppHelper.setTextColor(holder.itemView.context,holder.tvMvKd,R.color.red)}catch (e:Exception){}
        else
            try{AppHelper.setTextColor(holder.itemView.context,holder.tvMvKd,R.color.green)}catch (e:Exception){}


 /*       when (MyApplication.selectedTabInside) {
            AppConstants.TAB_TOP_HOLDING -> {

            }
            AppConstants.TAB_TOP_GAINER -> {

            }
            AppConstants.TAB_TOP_LOSERS -> {

            }

        }*/

        if(position%2==0)
            AppHelper.setBackgroundColor(holder.itemView.context,holder.itemView,R.color.white)
        else
            AppHelper.setBackgroundColor(holder.itemView.context,holder.itemView,R.color.table_odd)

    }

    override fun getItemCount(): Int {
        return itemPortfolio.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvHolding: TextView = itemView.findViewById(com.ids.markaz.R.id.tvHolding) as TextView
        var tvMvKd: TextView = itemView.findViewById(com.ids.markaz.R.id.tvMvKd) as TextView


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
