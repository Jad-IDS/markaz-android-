package com.ids.markaz.controller.Adapters.RVOnItemClickListener


import android.view.View

interface RVOnInnerItemClickListener {
    fun onInnerItemClicked(view: View,parentPosition:Int, position: Int)
}
