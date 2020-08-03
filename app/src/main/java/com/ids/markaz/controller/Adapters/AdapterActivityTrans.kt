package com.ids.markaz.controller.Adapters




import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication

import com.ids.markaz.model.ResponseAccountSummary
import com.ids.markaz.model.ResponseTransaction
import com.ids.markaz.utils.AppHelper
import java.lang.Exception


import java.util.*


class AdapterActivityTrans(val items: ArrayList<ResponseTransaction>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterActivityTrans.VHprivacy>(),RVOnItemClickListener {
    override fun onItemClicked(view: View, position: Int) {

    }

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_activity_trans, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {

        try{holder.tvName.text=items[position].shortName}catch (e:Exception){}
        try{holder.tvAction.text=items[position].typeName}catch (e:Exception){}
        try{holder.tvDate.text=items[position].transactionDate}catch (e:Exception){}

        try{ holder.tvDate.text= AppHelper.formatDate(holder.itemView.context,items[position].transactionDate!!,"yyyy-MM-dd'T'hh:mm:ss","dd/MM/yyyy")}catch (e:Exception){}


        try{holder.tvPrice.text=items[position].settlementAmount.toString()}catch (e:Exception){}

        if(items[position].arrayDetails!!.isNotEmpty()){

            var adapterDetails= AdapterPortfolioDetails(items[position].arrayDetails!!,this,1)
            val glm = GridLayoutManager(holder.itemView.context, 1)
            holder.rvSubInfo.adapter=adapterDetails
            holder.rvSubInfo.layoutManager=glm
            holder.rvSubInfo.isNestedScrollingEnabled=false
        }
        if(items[position].expanded!!) {
            holder.rvSubInfo.visibility = View.VISIBLE
         //   holder.ivExpand.rotation=90f
            holder.ivExpand.animate().rotation(90f).start();

        }
        else {
            holder.rvSubInfo.visibility = View.GONE
            //holder.ivExpand.rotationX=0f
            holder.ivExpand.animate().rotation(0f).start();
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvDate: TextView = itemView.findViewById(com.ids.markaz.R.id.tvDate) as TextView
        var tvAction: TextView = itemView.findViewById(com.ids.markaz.R.id.tvAction) as TextView
        var tvName: TextView = itemView.findViewById(com.ids.markaz.R.id.tvName) as TextView
        var tvPrice: TextView = itemView.findViewById(com.ids.markaz.R.id.tvPrice) as TextView
        var ivExpand: ImageView = itemView.findViewById(com.ids.markaz.R.id.ivExpand) as ImageView

        var rvSubInfo: RecyclerView = itemView.findViewById(com.ids.markaz.R.id.rvSubInfo) as RecyclerView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
