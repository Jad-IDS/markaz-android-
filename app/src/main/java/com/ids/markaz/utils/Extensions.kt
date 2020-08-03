package com.ids.markaz.utils

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat


/**
 * Removes the listener of a checkbox temporarily to restore the chosen choice without calling the on Text change
 */
fun CompoundButton.setCustomChecked(value: Boolean, listener: CompoundButton.OnCheckedChangeListener) {
    setOnCheckedChangeListener(null)
    isChecked = value
    setOnCheckedChangeListener(listener)
}




/**
 * Calculate the occurrences of a certain string
 */
fun String.occurrencesOf(sub: String): Int {
    var count = 0
    var last = 0
    while (last != -1) {
        last = this.indexOf(sub, last)
        if (last != -1) {
            count++
            last += sub.length
        }
    }
    return count
}

/**
 * Set the first character as upper case
 */
fun String.upperCaseFirstLetter(): String {
    return this.substring(0, 1).toUpperCase().plus(this.substring(1))
}

/**
 * Checks if String is numeric
 */
fun String.isNumeric(): Boolean {
    return this.matches("\\d+".toRegex())
}

/**
 * Used for simpler logging
 */
fun Any.wtf(message: String) {
    Log.wtf(this::class.java.simpleName, message)
}

/**
 * Used for Images Loading
 */


/**
 * Used for Showing toasts
 */
fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}


@SuppressLint("RestrictedApi")
fun AppCompatCheckBox.setCheckBoxColor(uncheckedColor: Int, checkedColor: Int) {
    val colorStateList = ColorStateList(
        arrayOf(
            intArrayOf(-R.attr.state_checked), // unchecked
            intArrayOf(R.attr.state_checked)  // checked
        ),
        intArrayOf(uncheckedColor, checkedColor)
    )
    this.supportButtonTintList = colorStateList
}

@SuppressLint("RestrictedApi")
fun AppCompatRadioButton.setRadioButtonColor(uncheckedColor: Int, checkedColor: Int) {
    val colorStateList = ColorStateList(
        arrayOf(
            intArrayOf(-R.attr.state_checked), // unchecked
            intArrayOf(R.attr.state_checked)  // checked
        ),
        intArrayOf(uncheckedColor, checkedColor)
    )
    this.supportButtonTintList = colorStateList
}



/**
 * Used for Showing and Hiding views
 */
fun View.showView() {
    visibility = View.VISIBLE
}

fun View.hideView() {
    visibility = View.GONE
}

/**
 * Used for Showing and Hiding keyboard
 */
fun View.showKeyboard(show: Boolean) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (show) {
        if (requestFocus()) imm.showSoftInput(this, 0)
    } else {
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, colorRes)))
}