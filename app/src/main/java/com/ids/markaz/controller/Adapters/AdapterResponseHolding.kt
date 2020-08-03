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
import com.ids.markaz.model.Holdings
import com.ids.markaz.model.ResponseTopHolding
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.setTint
import java.lang.Exception


import java.util.*


class AdapterResponseHolding(val itemPortfolio: ArrayList<ResponseTopHolding>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterResponseHolding.VHprivacy>() {

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_holdings, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {
        try{holder.tvName.text=itemPortfolio[position].stock}catch (e:Exception){}
        try{holder.tvPercentage.text=String.format("%.2f", itemPortfolio[position].percentage!!.toDouble())+" %"}catch (e:Exception){}
        try{holder.tvPrice.text=itemPortfolio[position].currency+" "+itemPortfolio[position].price.toString()}catch (e:Exception){}

        try{
            holder.ivUpDown.visibility=View.VISIBLE
        if(itemPortfolio[position].percentage!!>0){
            holder.ivUpDown.rotationX=0f
            holder.ivUpDown.setTint(R.color.green_text)
            AppHelper.setTextColor(holder.itemView.context,holder.tvPercentage,R.color.green_text)

        }else{
            holder.ivUpDown.setTint(R.color.red)
            AppHelper.setTextColor(holder.itemView.context,holder.tvPercentage,R.color.red)
            holder.ivUpDown.rotationX=180f
        }}catch (e:Exception){

            holder.ivUpDown.visibility=View.INVISIBLE
        }

        if(itemPortfolio.size>5) {
            if (position == 0) {
                holder.tvTopTitle.setText("Top Gainer")
                holder.tvTopTitle.visibility = View.VISIBLE
            } else if (position == 6) {
                holder.tvTopTitle.setText("Top Loosers")
                holder.tvTopTitle.visibility = View.VISIBLE
            } else
                holder.tvTopTitle.visibility = View.GONE
        }else{
            holder.tvTopTitle.visibility = View.GONE
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
        var tvTopTitle: TextView = itemView.findViewById(com.ids.markaz.R.id.tvTopTitle) as TextView



        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
