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
import com.ids.markaz.model.PortfolioDetails

import com.ids.markaz.model.ResponseAccountSummary



import java.util.*


class AdapterPortfolioDetails(val itemPortfolio: ArrayList<PortfolioDetails>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterPortfolioDetails.VHprivacy>() {

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_portfolio_new_sub, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {
        try{holder.tvTitleSub.text=itemPortfolio[position].name}catch (e:Exception){}
        try{holder.tvPrice.text=itemPortfolio[position].price}catch (e:Exception){}
        try{holder.tvPercentage.text=itemPortfolio[position].percentage+" %"}catch (e:Exception){}

    }

    override fun getItemCount(): Int {
        return itemPortfolio.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvTitleSub: TextView = itemView.findViewById(com.ids.markaz.R.id.tvTitleSub) as TextView
        var tvPercentage: TextView = itemView.findViewById(com.ids.markaz.R.id.tvPercentage) as TextView
        var tvPrice: TextView = itemView.findViewById(com.ids.markaz.R.id.tvPrice) as TextView



        init {
            if(type!=2)
               tvPercentage.visibility=View.GONE
            else
                tvPercentage.visibility=View.VISIBLE
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
