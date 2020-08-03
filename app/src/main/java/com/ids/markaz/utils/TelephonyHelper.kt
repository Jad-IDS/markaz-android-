
package com.ids.markaz

//import android.accounts.AccountManager;

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log



class TelephonyHelper {



    fun getMCC(context: Context): String {

        //return "415"; // Test
        //return "415"; // Default
        //      return "420"; // Zain
        //return "426"; // Bahrain
        //return "420"; // Mobily
        // return "432"; // Iran MTN, MNC


        var mcc = ""
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val networkOperator = telephonyManager.networkOperator
            if (networkOperator != null && networkOperator != "") {
                mcc = networkOperator.substring(0, 3)
                Log.d("mcc", "mcc : $mcc")
            }
        } catch (ex: Exception) {
            Log.d("getMNC", "networkOperator.substring(0, 3)")
        }

        return "420"
        //return mcc; //telephonyManager.getNetworkOperator().substring(0, 3);

    }

    // mobile network code //01
    fun getMNC(context: Context): String {

        //return "03"; // Test


        var mnc = ""
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val networkOperator = telephonyManager.networkOperator
            if (networkOperator != null && networkOperator != "") {
                mnc = networkOperator.substring(3)
                Log.d("mnc", "mnc : $mnc")
            }
        } catch (ex: Exception) {
            Log.d("getMNC", "networkOperator.substring(3)")
        }

        return "01"

    }



    fun getVersionName(context: Context): String {
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            if (pInfo != null) {
                return pInfo.versionName
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return ""
    }

    fun getVersionCode(context: Context): Int {
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            if (pInfo != null) {
                return pInfo.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return 1
    }


    fun getDeviceID(context: Context): String {

        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )

}
    fun getDeviceModel(): String {
        return android.os.Build.MODEL.replace(" ", "") //e.g device Model =  Samsung GT-I9100 -> GT-I9100
    }


    fun getPhoneName(): String? {
        try {
            val myDevice = BluetoothAdapter.getDefaultAdapter()
            return myDevice.name// Device Name in phone
        } catch (ex: Exception) {
            return null
        }

    }

    // get Device Version
    fun getDeviceVersion(): String {
        return android.os.Build.VERSION.RELEASE // e.g. myVersion := "1.6"
    }


    fun getDeviceName(): String {
        val name = getPhoneName()
        return if (name != null && name != "") {
            name
        } else {
            try {
                android.os.Build.MANUFACTURER // e.g Device name =  Samsung
            } catch (e: Exception) {
                ""
            }

        }
    }

}
