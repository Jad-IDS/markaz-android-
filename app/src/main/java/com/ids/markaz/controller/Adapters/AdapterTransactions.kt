package com.ids.markaz.controller.Adapters





import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener

import com.ids.markaz.custom.CustomTextViewMedium
import com.ids.markaz.model.Asset
import com.ids.markaz.model.ItemSpinner
import com.ids.markaz.model.ResponseTransaction

import com.ids.markaz.model.Transactions
import com.ids.markaz.utils.AppHelper
import java.lang.Exception


import java.util.*


class AdapterTransactions(val itemPortfolio: ArrayList<ResponseTransaction>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterTransactions.VHprivacy>() {

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_transactions, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {
        try{ holder.tvDate.text=AppHelper.formatDate(holder.itemView.context,itemPortfolio[position].transactionDate!!,"yyyy-MM-dd'T'hh:mm:ss","MM/dd/yyyy")}catch (e:Exception){}

        try{holder.tvProduct.text=itemPortfolio[position].productNo}catch (e:Exception){holder.tvProduct.text="-"}
        try{holder.tvType.text=itemPortfolio[position].transactionTypeEn}catch (e:Exception){holder.tvType.text="-"}
        try{holder.tvSecurity.text=itemPortfolio[position].securityShortName}catch (e:Exception){}
        try{holder.tvQty.text=itemPortfolio[position].quantity.toString()}catch (e:Exception){}
        try{holder.tvUnitPrice.text=itemPortfolio[position].unitPrice.toString()}catch (e:Exception){}
        if(position%2==0)
            AppHelper.setBackgroundColor(holder.itemView.context,holder.itemView,R.color.white)
        else
            AppHelper.setBackgroundColor(holder.itemView.context,holder.itemView,R.color.table_odd)

    }

    override fun getItemCount(): Int {
        return itemPortfolio.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvDate: TextView = itemView.findViewById(com.ids.markaz.R.id.tvDate) as TextView
        var tvProduct: TextView = itemView.findViewById(com.ids.markaz.R.id.tvProduct) as TextView
        var tvType: TextView = itemView.findViewById(com.ids.markaz.R.id.tvType) as TextView

        var tvSecurity: TextView = itemView.findViewById(com.ids.markaz.R.id.tvSecurity) as TextView
        var tvQty: TextView = itemView.findViewById(com.ids.markaz.R.id.tvQty) as TextView
        var tvUnitPrice: TextView = itemView.findViewById(com.ids.markaz.R.id.tvUnitPrice) as TextView
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
