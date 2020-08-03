package com.ids.markaz.controller.Adapters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener

import com.ids.markaz.custom.CustomTextViewMedium
import com.ids.markaz.model.ItemSpinner


import java.util.*


class AdapterSpinner(val itemSpinner: ArrayList<ItemSpinner>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterSpinner.VHprivacy>() {

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_spinner, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {
        holder.tvItem.text=itemSpinner[position].name

    }

    override fun getItemCount(): Int {
        return itemSpinner.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvItem: CustomTextViewMedium = itemView.findViewById(com.ids.markaz.R.id.tvItem) as CustomTextViewMedium

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
/*            if(type==AppConstants.SPINNER_CYCLE_LENGTH)
                itemView.id= R.id.IdSpinnerCycle
            else if(type==AppConstants.SPINNER_PERIOD_LENGTH)
                itemView.id=R.id.IdSpinnerPeriod
            else if(type==AppConstants.SPINNER_KIDS_NUMBER)
                itemView.id=R.id.IdSpinnerKidsNumber*/
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
