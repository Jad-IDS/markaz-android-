package com.ids.markaz.controller.Adapters

import com.ids.markaz.model.ResponsePortfolioReport




import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.Holdings
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.setTint
import java.lang.Exception


import java.util.*


class AdapterReportTypes(val itemPortfolio: ArrayList<ResponsePortfolioReport>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterReportTypes.VHprivacy>() {

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_report_types, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {
        try{
            if (MyApplication.languageCode == AppConstants.LANG_ENGLISH) {
                holder.tvName.text = itemPortfolio[position].titleEn
            }else{
                holder.tvName.text = itemPortfolio[position].titleAr
            }
        }catch (e:Exception){}



        try{
            if(itemPortfolio[position].selected!!)
                holder.ivSelected.setImageResource(R.drawable.circle_select)
            else
                holder.ivSelected.setImageResource(R.drawable.circle_unselect)

        }catch (e:Exception){}




    }

    override fun getItemCount(): Int {
        return itemPortfolio.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvName: TextView = itemView.findViewById(com.ids.markaz.R.id.tvName) as TextView
        var ivSelected: ImageView = itemView.findViewById(com.ids.markaz.R.id.ivSelected) as ImageView


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
