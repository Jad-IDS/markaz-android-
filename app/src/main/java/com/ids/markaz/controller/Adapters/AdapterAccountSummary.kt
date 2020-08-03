package com.ids.markaz.controller.Adapters




import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication

import com.ids.markaz.model.ResponseAccountSummary
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import java.lang.Exception


import java.util.*


class AdapterAccountSummary(val itemPortfolio: ArrayList<ResponseAccountSummary>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterAccountSummary.VHprivacy>() {

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_portfolio, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {
        try{holder.tvTitle.text=itemPortfolio[position].nameEn}catch (e:Exception){}
        if(MyApplication.selectedTab==AppConstants.TAB_CASH){
            try{holder.tvPercentage.text=String.format("%.2f", itemPortfolio[position].cashPositionPercent!!*100)+" %"}catch (e:Exception){}
            try{holder.tvValue.text=itemPortfolio[position].cashPosition.toString()}catch (e:Exception){}
        }else{
            try{holder.tvPercentage.text=String.format("%.2f", itemPortfolio[position].holdingPositionPercent!!*100)+" %"}catch (e:Exception){}
            try{holder.tvValue.text=itemPortfolio[position].holdingPosition.toString()}catch (e:Exception){}
        }

        if(position%2==0)
            AppHelper.setBackgroundColor(holder.itemView.context,holder.itemView,R.color.white)
        else
            AppHelper.setBackgroundColor(holder.itemView.context,holder.itemView,R.color.table_odd)

    }

    override fun getItemCount(): Int {
        return itemPortfolio.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvTitle: TextView = itemView.findViewById(com.ids.markaz.R.id.tvTitle) as TextView
        var tvPercentage: TextView = itemView.findViewById(com.ids.markaz.R.id.tvPercentage) as TextView
        var tvValue: TextView = itemView.findViewById(com.ids.markaz.R.id.tvValue) as TextView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
