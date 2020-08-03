package com.ids.markaz.controller.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.ItemCashInside

import com.ids.markaz.model.ResponseAccountSummary
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import java.lang.Exception


import java.util.*


class AdapterAccountCashInside(val itemPortfolio: ArrayList<ItemCashInside>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterAccountCashInside.VHprivacy>() {

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_cash_inside, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {
        try{holder.tvName.text=itemPortfolio[position].name}catch (e:Exception){}
        try{holder.tvValue.text=itemPortfolio[position].value}catch (e:Exception){}




        if(position%2==0)
            AppHelper.setBackgroundColor(holder.itemView.context,holder.itemView,R.color.white)
        else
            AppHelper.setBackgroundColor(holder.itemView.context,holder.itemView,R.color.table_odd)

    }

    override fun getItemCount(): Int {
        return itemPortfolio.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvName: TextView = itemView.findViewById(com.ids.markaz.R.id.tvName) as TextView
        var tvValue: TextView = itemView.findViewById(com.ids.markaz.R.id.tvValue) as TextView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
