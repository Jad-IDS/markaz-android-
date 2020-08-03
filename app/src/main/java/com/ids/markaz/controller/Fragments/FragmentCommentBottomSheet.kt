package com.ids.markaz.controller.Fragments


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener


class FragmentCommentBottomSheet : BottomSheetDialogFragment(), RVOnItemClickListener {
    override fun onItemClicked(view: View, position: Int) {

    }


    val CREDIT = 1
    val CASH = 2
    var OrderId=0
    var paymentMethod = CASH

    override fun onStart() {
        super.onStart()
        val dialog = dialog

        if (dialog != null) {
            val bottomSheet = dialog.findViewById(com.ids.markaz.R.id.design_bottom_sheet) as FrameLayout
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        val view = view
        view.let {

            it!!.post {
                val parent = view!!.parent as View
                val params = parent.layoutParams as CoordinatorLayout.LayoutParams
                val behavior = params.behavior
                val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
                bottomSheetBehavior!!.setPeekHeight(view.measuredHeight)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.ids.markaz.R.style.CustomBottomSheetDialogTheme)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.markaz.R.layout.fragment_comment_bottomsheet, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize(){

    }





}