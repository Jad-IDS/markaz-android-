package com.ids.markaz.controller.Adapters




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
import com.ids.markaz.model.ResponseHoldingPosition
import com.ids.markaz.model.ResponsePortfolioHolding
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.setTint
import java.lang.Exception


import java.util.*


class AdapterHoldings(val itemPortfolio: ArrayList<ResponseHoldingPosition>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterHoldings.VHprivacy>() {

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_holdings, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {
        if(MyApplication.languageCode == AppConstants.LANG_ENGLISH){
        try{holder.tvName.text=itemPortfolio[position].tickerEn}catch (e:Exception){}}else{
            try{holder.tvName.text=itemPortfolio[position].tickerAr}catch (e:Exception){}
        }
        try{holder.tvPercentage.text=itemPortfolio[position].changePercent.toString()+" %"}catch (e:Exception){}
        try{holder.tvPrice.text=MyApplication.selectedCurrencyName+" "+itemPortfolio[position].lastPrice.toString()}catch (e:Exception){}

        try{
        if(itemPortfolio[position].changeValue!!>0){
            holder.ivUpDown.visibility=View.VISIBLE
            holder.ivUpDown.rotationX=0f
            holder.ivUpDown.setTint(R.color.green_text)
            AppHelper.setTextColor(holder.itemView.context,holder.tvPercentage,R.color.green_text)

        }else if(itemPortfolio[position].changeValue!!<0){
            holder.ivUpDown.setTint(R.color.red)
            AppHelper.setTextColor(holder.itemView.context,holder.tvPercentage,R.color.red)
            holder.ivUpDown.rotationX=180f
            holder.ivUpDown.visibility=View.VISIBLE
        }else {
            holder.ivUpDown.visibility=View.INVISIBLE
            AppHelper.setTextColor(holder.itemView.context,holder.tvPercentage,R.color.black)
        }


        }catch (e:Exception){

        }



    }

    override fun getItemCount(): Int {
        return itemPortfolio.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvName: TextView = itemView.findViewById(com.ids.markaz.R.id.tvName) as TextView
        var tvPercentage: TextView = itemView.findViewById(com.ids.markaz.R.id.tvPercentage) as TextView
        var tvPrice: TextView = itemView.findViewById(com.ids.markaz.R.id.tvPrice) as TextView
        var ivUpDown: ImageView = itemView.findViewById(com.ids.markaz.R.id.ivUpDown) as ImageView


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
