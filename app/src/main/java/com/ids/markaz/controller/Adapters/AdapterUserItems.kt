package com.ids.markaz.controller.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.model.ResponseUser
import java.util.ArrayList

class AdapterUserItems(val userItems: ArrayList<ResponseUser> = ArrayList(), private val itemClickListener: RVOnItemClickListener) :
    RecyclerView.Adapter<AdapterUserItems.VHprivacy>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.user_item, parent, false))
    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {




        if(userItems.get(position).selected!!)
            holder.ivSelected.setImageResource(R.drawable.circle_select)
        else
            holder.ivSelected.setImageResource(R.drawable.circle_unselect)



        holder.text.text = userItems.get(position).nameEn!!

    }

    override fun getItemCount(): Int {
        return userItems.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        var ivSelected : ImageView = itemView.findViewById(R.id.ivSelection)
        var text : TextView = itemView.findViewById(R.id.tvClientName)



        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }
}
