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


class AdapterPerformance(val itemPerformance: ArrayList<ResponsePerformance>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterPerformance.VHprivacy>() {

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_performance, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {
        try{holder.tvPortfolio.text=itemPerformance[position].nameEn}catch (e:Exception){holder.tvPortfolio.text="-"}


        when (MyApplication.selectedTab) {
            AppConstants.TAB_PERFORMANCE_WTD -> {
                try{holder.tvPercentage.text=String.format("%.2f",itemPerformance[position].wtdPortfolio!!)+"%"}catch (e:Exception){holder.tvPercentage.text="-"}
                try{holder.tvBenchmark.text=String.format("%.2f", itemPerformance[position].wtdBench!!)+"%"}catch (e:Exception){holder.tvBenchmark.text="-"}
            }
            AppConstants.TAB_PERFORMANCE_YTD -> {
                try{holder.tvPercentage.text=String.format("%.2f",itemPerformance[position].ytdPortfolio!!)+"%"}catch (e:Exception){holder.tvPercentage.text="-"}
                try{holder.tvBenchmark.text=String.format("%.2f",itemPerformance[position].ytdBench!!)+"%"}catch (e:Exception){holder.tvBenchmark.text="-"}
            }
            AppConstants.TAB_PERFORMANCE_MTD -> {
                try{holder.tvPercentage.text=String.format("%.2f",itemPerformance[position].mtdPortfolio!!)+"%"}catch (e:Exception){holder.tvPercentage.text="-"}
                try{holder.tvBenchmark.text=String.format("%.2f",itemPerformance[position].mtdBench!!)+"%"}catch (e:Exception){holder.tvBenchmark.text="-"}
            }
            AppConstants.TAB_PERFORMANCE_INCEPTION -> {
                try{holder.tvPercentage.text=String.format("%.2f",itemPerformance[position].siPortfolio!!)+"%"}catch (e:Exception){holder.tvPercentage.text="-"}
                try{holder.tvBenchmark.text=String.format("%.2f",itemPerformance[position].siBench!!)+"%"}catch (e:Exception){holder.tvBenchmark.text="-"}
            }

            AppConstants.TAB_PERFORMANCE_CHART -> {
                try{holder.tvPercentage.text="-"}catch (e:Exception){}
                try{holder.tvBenchmark.text="-"}catch (e:Exception){}
            }
        }


        if(position%2==0)
            AppHelper.setBackgroundColor(holder.itemView.context,holder.itemView,R.color.white)
        else
            AppHelper.setBackgroundColor(holder.itemView.context,holder.itemView,R.color.table_odd)

    }

    override fun getItemCount(): Int {
        return itemPerformance.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvPortfolio: TextView = itemView.findViewById(com.ids.markaz.R.id.tvPortfolio) as TextView
        var tvPercentage: TextView = itemView.findViewById(com.ids.markaz.R.id.tvPercentage) as TextView
        var tvBenchmark: TextView = itemView.findViewById(com.ids.markaz.R.id.tvBenchmark) as TextView


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
