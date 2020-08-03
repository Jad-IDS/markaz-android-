package com.ids.markaz.controller.Adapters

import android.widget.TextView
import com.ids.markaz.model.Notifications
import java.lang.Exception


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.utils.AppHelper


import java.util.*


class AdapterNotifications(val arrayNotifications: ArrayList<Notifications>, private val itemClickListener: RVOnItemClickListener, from:Int) :
    RecyclerView.Adapter<AdapterNotifications.VHprivacy>() {

    var type=from
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_notification, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {
        try{holder.tvNotificationTitle.text=arrayNotifications[position].title}catch (e:Exception){}
        try{holder.tvNotificationDate.text=arrayNotifications[position].date}catch (e:Exception){}
        if(position%2==0)
            AppHelper.setBackgroundColor(holder.itemView.context,holder.itemView, R.color.white)
        else
            AppHelper.setBackgroundColor(holder.itemView.context,holder.itemView, R.color.table_odd)
    }

    override fun getItemCount(): Int {
        return arrayNotifications.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvNotificationTitle: TextView = itemView.findViewById(com.ids.markaz.R.id.tvNotificationTitle) as TextView
        var tvNotificationDate: TextView = itemView.findViewById(com.ids.markaz.R.id.tvNotificationDate) as TextView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
