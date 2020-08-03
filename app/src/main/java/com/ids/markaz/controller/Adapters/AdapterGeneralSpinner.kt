package com.ids.markaz.controller.Adapters


import android.content.Context
import android.graphics.Color
import android.graphics.Path
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import com.ids.markaz.R
import com.ids.markaz.model.ItemSpinner
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper

class AdapterGeneralSpinner(
    context: Context, textViewResourceId: Int,
    private val selectedSpinnerPosition:Int,
    private val selectedTextColor:Int,
    private val dropDownTextColor:Int,
    private val dropDownTextPosition:Int,
    private val values: ArrayList<ItemSpinner>
) : ArrayAdapter<ItemSpinner>(context, textViewResourceId, values) {

    override fun getCount(): Int {
        return values.size
    }

    override fun getItem(position: Int): ItemSpinner? {
        return values[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
       // label.setTextColor(Color.BLACK)
        AppHelper.setTextColor(context,label,selectedTextColor)
        label.text = values[position].name
        label.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.resources.getDimension(R.dimen.font))
        when (selectedSpinnerPosition) {
            AppConstants.START -> label.gravity=Gravity.START
            AppConstants.CENTER -> label.gravity=Gravity.CENTER
            AppConstants.END -> label.gravity=Gravity.END
            else -> label.gravity=Gravity.START
        }
        return label
    }

    override fun getDropDownView(
        position: Int, convertView: View?,
        parent: ViewGroup
    ): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        AppHelper.setTextColor(context,label, dropDownTextColor)
        label.text = values[position].name
        label.textDirection=View.LAYOUT_DIRECTION_LOCALE
        when (dropDownTextPosition) {
            AppConstants.START -> {label.gravity=Gravity.CENTER_VERTICAL
            label.textAlignment=View.TEXT_ALIGNMENT_VIEW_START

            }
            AppConstants.CENTER -> label.gravity=Gravity.CENTER
            AppConstants.END -> label.gravity=Gravity.END
            else -> label.gravity=Gravity.START
        }
        return label
    }
}