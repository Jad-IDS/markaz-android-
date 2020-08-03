package com.ids.markaz.controller.Adapters


import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener

import android.widget.TextView





internal class AdapterStartPager(private val context: Context, val arrayList: ArrayList<Int>, private val itemClickListener: RVOnItemClickListener) :
    PagerAdapter() {
    private val layoutInflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {


        val view = layoutInflater.inflate(R.layout.fragment_start_pager, container, false)
        val tvInfoTitle = view.findViewById(R.id.tvInfoTitle) as TextView
        val tvInfoDesc = view.findViewById(R.id.tvInfoDesc) as TextView
        val ivInfo = view.findViewById(R.id.ivInfo) as ImageView

        when (position) {
            0 -> {
                tvInfoTitle.text = context.getString(R.string.start_title_1)
                tvInfoDesc.text = context.getString(R.string.start_desc_1)
                ivInfo.setImageResource(R.drawable.img_01)
            }
            1 -> {
                tvInfoTitle.text = context.getString(R.string.start_title_2)
                tvInfoDesc.text = context.getString(R.string.start_desc_2)
                ivInfo.setImageResource(R.drawable.img_02)
            }
            2 -> {
                tvInfoTitle.text = context.getString(R.string.start_title_3)
                tvInfoDesc.text = context.getString(R.string.start_desc_3)
                ivInfo.setImageResource(R.drawable.img_03)
            }
        }




        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}