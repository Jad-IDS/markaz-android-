package com.ids.markaz.controller.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.R
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment


class FragmentBottomSheetNotification : BottomSheetDialogFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.markaz.R.layout.bottom_sheet_notification, container, false)


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL,com.ids.markaz.R.style.MyBottomSheetDialog)
    }


}