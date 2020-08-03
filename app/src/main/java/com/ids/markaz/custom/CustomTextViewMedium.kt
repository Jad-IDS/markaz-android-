package com.ids.markaz.custom


import android.content.Context

import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

import com.ids.markaz.utils.AppHelper


class CustomTextViewMedium : AppCompatTextView {
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()

    }

    private fun init() {
        if (!isInEditMode)
            typeface = AppHelper.getTypeFace(context)

    }

}
