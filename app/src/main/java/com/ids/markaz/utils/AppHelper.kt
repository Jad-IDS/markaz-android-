package com.ids.markaz.utils


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build

import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.auth0.android.jwt.JWT

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.ids.inpoint.utils.RetrofitClient
import com.ids.markaz.R
import com.ids.markaz.controller.Activities.ActivityHomeTabs
import com.ids.markaz.controller.Activities.ActivityLogin
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.ItemSpinner
import com.ids.markaz.model.ResponseCurrency
import com.ids.markaz.model.ResponseLogin
import com.ids.markaz.model.ResponseUserImage
import kotlinx.android.synthetic.main.activity_settings.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.*
import java.util.*

/**
 * Created by Ibrahim on 8/23/2017.
 */

class AppHelper {



    fun setbackgroundImage(context: Context, view: View, ImgUrl: String) {

        Glide.with(context)
            .load(ImgUrl)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
            .into(object : SimpleTarget<Bitmap>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                override fun onResourceReady(bitmap: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                    view.background = BitmapDrawable(context.resources, bitmap)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    super.onLoadCleared(placeholder)

                }
            })
    }

    companion object {
        var dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.UK)
        var userId: String? = null
        var selectedWelcomeType: Int? = 0
        var fragmentAvailable: Int? = null
        var dateFormatProfile = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        var dateFormat2 = SimpleDateFormat("dd-mm-yyyy")
        var dateFormatReport = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val OneDecimal = "#,##0.0"
        val TwoDecimal = "#,##0.00"
        val ThreeDecimal = "#.000"
        val OneDecimalThousandsSeparator = "#,###.0"
        val OneDecimalSeparator = "#.0"
        val NoDecimalSeparator = "#"
        val TwoDecimalThousandsSeparator = "#,###.00"
        val ThreeDecimalThousandsSeparator = "#,##0.000"
        val NoDecimalThousandsSeparator = "#,###"
        var userProfile:ResponseUserImage?=null
        fun getTypeFace(context: Context): Typeface {
            return if (Locale.getDefault().language == "ar")
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/segoeui.ttf"
                )//"fonts/NeoTech-Medium.otf"
            else
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/HelveticaNeue.ttc"
                )//"fonts/NeoTech-Medium.otf"

         //   return Typeface.DEFAULT

        }

        fun getTypeFaceLite(context: Context): Typeface {
            return if (Locale.getDefault().language == "ar")
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/segoeui.ttf"
                )//"fonts/NeoTech-Medium.otf"
            else
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/ArnoPro-Display.otf"
                )//"fonts/NeoTech-Medium.otf"

            //   return Typeface.DEFAULT

        }




        fun getTypeFaceLiteRegular(context: Context): Typeface {
            return if (Locale.getDefault().language == "ar")
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/segoeui.ttf"
                )//"fonts/NeoTech-Medium.otf"
            else
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/ArnoPro-Regular.otf"
                )//"fonts/NeoTech-Medium.otf"

            //   return Typeface.DEFAULT

        }


        fun setLocal(context: Context) {

            if (MyApplication.languageCode == AppConstants.LANG_ENGLISH) {
                LocaleUtils.setLocale(Locale("en"))
            } else if (MyApplication.languageCode == AppConstants.LANG_ARABIC) {
                LocaleUtils.setLocale(Locale("ar","LB"))
            }

        }



        fun getDeviceName(): String {

            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else {
                capitalize(manufacturer) + " " + model
            }
        }

        fun formatNumber(num: Double, format: String): String {
            val formatter = DecimalFormat(format, setInEnglish())
            return formatter.format(num)
        }
        fun setInEnglish(): DecimalFormatSymbols {
            val custom = DecimalFormatSymbols(Locale.ENGLISH)
            custom.decimalSeparator = '.'
            return custom
        }





        private fun capitalize(model: String): String {
            if (model.length == 0) {
                return ""
            }
            val first = model.get(0)
            return if (Character.isUpperCase(first)) {
                model
            } else {
                Character.toUpperCase(first) + model.substring(1)
            }
        }

        fun getCurrencyName():String {
            try {
               try{
                if(MyApplication.currencyId==0)
                    MyApplication.currencyId==MyApplication.arrayCurrencies[0].id}catch (e:java.lang.Exception){}

                val item: ResponseCurrency? =MyApplication.arrayCurrencies.find { it.id == MyApplication.currencyId }
                if (MyApplication.languageCode == AppConstants.LANG_ENGLISH) {
                    Log.wtf("currency_name","aa"+item!!.shortNameEn)
                    return item.shortNameEn!!
                }
                else {
                    Log.wtf("currency_name","bb"+item!!.shortNameAr)
                    return item.shortNameAr!!
                }
            } catch (e: java.lang.Exception){
                Log.wtf("currency_name","cc"+e.toString())
                return ""
            }
        }

        fun getAndroidVersion(): String {

            val release = Build.VERSION.RELEASE
            val sdkVersion = Build.VERSION.SDK_INT
            return "Android:$sdkVersion ($release)"
        }

        fun getVersionNumber(): Int {

            val pInfo: PackageInfo
            var version = -1
            try {
                pInfo = MyApplication.instance.packageManager
                    .getPackageInfo(MyApplication.instance.packageName, 0)
                version = pInfo.versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

            return version
        }


         fun timeIsBefore(time: String, endtime: String,format:String): Boolean {


            val sdf = SimpleDateFormat(format,Locale.UK)
            val sdf2 = SimpleDateFormat(format,Locale.UK)

            try {
                val date1 = sdf.parse(time)
                val date2 = sdf2.parse(endtime)

                return date1.before(date2)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return false
        }


        fun getTypeFaceBold(context: Context): Typeface {
            return if (Locale.getDefault().language == "ar")
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/segoeuib.ttf"
                )//fonts/NeoTech-Bold.otf

            else
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/HelveticaNeue_bold.ttf"
                )//fonts/NeoTech-Bold.otf

          //  return Typeface.DEFAULT_BOLD
        }


        @Throws(ParseException::class)
         fun getMonth(date: String): String {
            val d = SimpleDateFormat("yyyy-MM-dd'T'hh:ss:mm", Locale.ENGLISH).parse(date)
            val cal = Calendar.getInstance()
            cal.time = d
            return SimpleDateFormat("MMMM").format(cal.time)
        }

        fun AddFragment(fragmentManager: FragmentManager, selecteFragment:Int, myFragment: Fragment, myTag:String){
            fragmentAvailable= selecteFragment
            fragmentManager.beginTransaction()
                .setCustomAnimations(
                    com.ids.markaz.R.anim.enter_from_right,
                    com.ids.markaz.R.anim.exit_to_left,
                    com.ids.markaz.R.anim.enter_from_left,
                    com.ids.markaz.R.anim.exit_to_right
                )
                .add(com.ids.markaz.R.id.container, myFragment, myTag)
                .commit()
        }


        fun getDateFromTimestamp(timestamp:Long):String?{
            var date=""
            try {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val netDate = Date(timestamp*1000)
                date=sdf.format(netDate)

            }catch (exception: Exception){}
            return date



        }


         fun getDateTime(s: String): String? {
            try {
                val sdf = SimpleDateFormat("EEE, MMM dd yyyy",
                    Locale.ENGLISH)
                val netDate = Date(s.trim().toLong())
                return sdf.format(netDate)
            } catch (e: Exception) {
                return e.toString()
            }
        }


        fun extractTimeStampLong(date: String): Long {

            val ts = java.lang.Long.parseLong(date.replace("\\D+".toRegex(), ""))
            val timeStamp = java.sql.Timestamp(ts)

            return timeStamp.time
        }

        fun formatDateString(time: Date): String {
            return dateFormat2.format(time)
        }


        fun ReplaceFragment(fragmentManager: FragmentManager,selecteFragment:Int,myFragment:Fragment,myTag:String){
            fragmentAvailable= selecteFragment
            fragmentManager.beginTransaction()
/*                .setCustomAnimations(
                    com.ids.markaz.R.anim.enter_from_right,
                    com.ids.markaz.R.anim.exit_to_left,
                    com.ids.markaz.R.anim.enter_from_left,
                    com.ids.markaz.R.anim.exit_to_right
                )*/
                .replace(com.ids.markaz.R.id.container, myFragment, myTag)


                .commit()
        }





/*
        fun isIsLoggedIn(context: Context): Boolean {
            isLoggedIn = context.getSharedPreferences(AppConstants.SHARED_PREFS, MODE_PRIVATE)
                .getBoolean(AppConstants.IS_LOGGED_IN, false)
            return isLoggedIn
        }

        fun setIsLoggedIn(context: Context, isLoggedIn: Boolean) {
            context.getSharedPreferences(AppConstants.SHARED_PREFS, MODE_PRIVATE).edit()
                .putBoolean(AppConstants.IS_LOGGED_IN, isLoggedIn).apply()
        }
*/







        fun getTypeFaceLight(context: Context): Typeface {
            return if (Locale.getDefault().language == "ar")
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/GE SS Two Light.otf"
                )//fonts/NeoTech-Light.otf
            else if (Locale.getDefault().language == "fa")
            //return Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/NAZANINB.TTF");
                Typeface.createFromAsset(context.applicationContext.assets, "fonts/BYekan.ttf")
            else
                Typeface.createFromAsset(
                    context.applicationContext.assets,
                    "fonts/Roboto-Light.ttf"
                )//fonts/NeoTech-Light.otf
        }



/*        fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false
                    }
                }
            }
            return true
        }*/




        fun Fragment.hideKeyboard() {
            view?.let { activity?.hideKeyboard() }
        }

        fun Activity.hideKeyboard() {
            hideKeyboard()
        }

        fun formatDate(c:Context,dateString:String,oldDateFormat:String,newDateFormat:String):String?{
            var format = SimpleDateFormat(oldDateFormat, Locale.US)
            val newDate = format.parse(dateString)
            format = SimpleDateFormat(newDateFormat, Locale.US)
            val date = format.format(newDate)
            return date
        }


        fun createDialog(c: Activity, message: String) {

            val builder = AlertDialog.Builder(c)
            builder
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton(c.getString(R.string.dialog_ok)) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

        fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false
                    }
                }
            }
            return true
        }

        fun setViewBackground(url: String, view: View) {
            Glide.with(view.context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
                .into(object : SimpleTarget<Bitmap>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    override fun onResourceReady(bitmap: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                        view.background = BitmapDrawable(view.context.resources, bitmap)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        super.onLoadCleared(placeholder)

                    }
                })
        }


        fun setImage(context: Context, img: ImageView, ImgUrl: String) {
            try {
                Glide.with(context)
                    .load(ImgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .fitCenter()
                    .dontTransform()
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(img)
            } catch (e: Exception) {
            }

        }


        fun setImage(context: Context, img: ImageView, ImgUrl: String,isLocal:Boolean) {
            try {
                Glide.with(context)
                    .load(if(!isLocal) ImgUrl else File(ImgUrl))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .fitCenter()
                    .dontTransform()
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(img)
            } catch (e: Exception) {
            }

        }

        fun setTextColor(context: Context,view:TextView,color:Int) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.setTextColor(ContextCompat.getColor(context, color))
            } else {
                view.setTextColor(context.resources.getColor(color))
            }
        }

        fun setBackgroundColor(context: Context,view:View,color:Int) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.setBackgroundColor(ContextCompat.getColor(context, color))
            } else {
                view.setBackgroundColor(context.resources.getColor(color))
            }
        }




        fun share(context: Context,subject: String, text: String) {
            val intent = Intent(Intent.ACTION_SEND)

            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, text)

            context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)))
        }




        fun setRoundImage(context: Context, img: ImageView, ImgUrl: String,isLocal:Boolean) {
            Log.wtf("image_rounded",ImgUrl)
            if(isLocal) {
                Glide.with(context).load(File(ImgUrl)).asBitmap().centerCrop().dontAnimate()
                    .into(object : BitmapImageViewTarget(img) {
                        override fun setResource(resource: Bitmap) {
                            val circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            img.setImageDrawable(circularBitmapDrawable)
                        }
                    })
            }else{
                Glide.with(context).load(ImgUrl).asBitmap().centerCrop().dontAnimate()
                    .into(object : BitmapImageViewTarget(img) {
                        override fun setResource(resource: Bitmap) {
                            val circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            img.setImageDrawable(circularBitmapDrawable)
                        }
                    })
            }
        }


   /*     fun setRoundImage(context: Context, img: ImageView, ImgUrl: String,isLocal:Boolean) {
            Log.wtf("image_rounded",ImgUrl)
            if(isLocal) {
                Glide.with(context).load(File(ImgUrl)).asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate()
                    .into(object : BitmapImageViewTarget(img) {
                        override fun setResource(resource: Bitmap) {
                            val circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            img.setImageDrawable(circularBitmapDrawable)
                        }
                    })
            }else{
                Glide.with(context).load(ImgUrl).asBitmap().centerCrop().dontAnimate()
                    .into(object : BitmapImageViewTarget(img) {
                        override fun setResource(resource: Bitmap) {
                            val circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            img.setImageDrawable(circularBitmapDrawable)
                        }
                    })
            }
        }*/





        fun hideSystemUI(activity: Activity) {
            val decorView: View
            decorView = activity.window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar

                    or View.SYSTEM_UI_FLAG_IMMERSIVE)
        }




        fun getFinalPath(context: Context, isTranslatable: Boolean, isOneSize: Boolean, language: String): String {
            var drawablePath = AppConstants.DRAWABLE
            if (isTranslatable && !language.matches("en".toRegex()))
                drawablePath = "$drawablePath-$language"
            if (!isOneSize) {
                when (context.resources.displayMetrics.densityDpi) {
                    DisplayMetrics.DENSITY_MEDIUM -> drawablePath = drawablePath + "-" + AppConstants.MDPI_FOLDER
                    DisplayMetrics.DENSITY_HIGH -> drawablePath = drawablePath + "-" + AppConstants.HDPI_FOLDER
                    DisplayMetrics.DENSITY_XHIGH -> drawablePath = drawablePath + "-" + AppConstants.XHDPI_FOLDER
                    DisplayMetrics.DENSITY_XXHIGH -> drawablePath = drawablePath + "-" + AppConstants.XXHDPI_FOLDER
                    DisplayMetrics.DENSITY_XXXHIGH -> drawablePath = drawablePath + "-" + AppConstants.XXHDPI_FOLDER
                    else -> drawablePath = drawablePath + "-" + AppConstants.XHDPI_FOLDER
                }

            }
            return drawablePath
        }

   /*     fun setMargins(context: Context?, pvCode: PinView?, i: Int, i1: Int, i2: Int, i3: Int) {

        }
*/

        fun setMargins(context: Context, view: View, left: Int, top: Int, right: Int, bottom: Int) {
            if (view.layoutParams is ViewGroup.MarginLayoutParams) {
                val leftInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, left.toFloat(), context.resources
                        .displayMetrics
                ).toInt()
                val topInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, top.toFloat(), context.resources
                        .displayMetrics
                ).toInt()

                val rightInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, right.toFloat(), context.resources
                        .displayMetrics
                ).toInt()

                val bottomInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, bottom.toFloat(), context.resources
                        .displayMetrics
                ).toInt()

                val p = view.layoutParams as ViewGroup.MarginLayoutParams
                p.setMargins(leftInDp, topInDp, rightInDp, bottomInDp)
                view.requestLayout()
            }
        }



        fun LinearLayout.checkAllEmpty(): Boolean {
            for (i in 1 until childCount) {
                if (i == 8) break

                    val v = getChildAt(i) as LinearLayout
                    val edit = v.getChildAt(1) as EditText
                    if (edit.text.toString().isEmpty() || edit.text == null)
                        return true

            }
            return false
        }



        fun setPaddings(context: Context, view: View, left: Int, top: Int, right: Int, bottom: Int) {
            if (view.layoutParams is ViewGroup.MarginLayoutParams) {
                val leftInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, left.toFloat(), context.resources
                        .displayMetrics
                ).toInt()
                val topInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, top.toFloat(), context.resources
                        .displayMetrics
                ).toInt()

                val rightInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, right.toFloat(), context.resources
                        .displayMetrics
                ).toInt()

                val bottomInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, bottom.toFloat(), context.resources
                        .displayMetrics
                ).toInt()

                val p = view.layoutParams as ViewGroup.MarginLayoutParams
                p.setMargins(leftInDp, topInDp, rightInDp, bottomInDp)
                view.requestLayout()
            }
        }




        fun handleCrashes(context: Activity) {

            if (!MyApplication.isDebug)
                Thread.setDefaultUncaughtExceptionHandler(MyExceptionHandler(context))
        }

        fun formatNumberComma(number:String) :String?{
           return NumberFormat.getNumberInstance(Locale.US).format(number)

        }

        fun formatNumberCommaWithRounding(number:String) :String{

                return  NumberFormat.getNumberInstance(Locale.US).format(String.format("%.2f",number.toDouble()))

        }


        fun reLogin(context: Context){

            RetrofitClient.client?.create(RetrofitInterface::class.java)
                ?.login(
                    MyApplication.cashedUserName!!,
                    MyApplication.cashedPassword!!,
                    "vestiowebappspa",
                    "password",
                    "openid email phone profile offline_access roles clientdashboardapi portfoliodashboardapi funddashboardapi identityserverapi"

                )?.enqueue(object : Callback<ResponseLogin> {
                    override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                        try{

                            onLoginRetrieved(context,response)

                        }catch (E:Exception){
                            goToLogin(context)
                        }
                    }
                    override fun onFailure(call: Call<ResponseLogin>, throwable: Throwable) {
                        goToLogin(context)
                    }
                })



        }

        fun onLoginRetrieved(context: Context,response: Response<ResponseLogin>) {

            MyApplication.responseLogin =response.body()!!
            MyApplication.investioUserId = JWT(response.body()!!.accessToken!!).getClaim("vestiouserid").asInt()!!
            MyApplication.expDate =AppHelper.getDateFromTimestamp(JWT(response.body()!!.accessToken!!).getClaim("exp").asLong()!!)!!
            MyApplication.expDateTimestamp = JWT(response.body()!!.accessToken!!).getClaim("exp").asLong()!!
            goToHome(context)

        }

        fun goToLogin(context: Context){
            context.startActivity(Intent(context, ActivityLogin::class.java))


        }

        fun goToHome(context: Context){
            context.startActivity(Intent(context, ActivityHomeTabs::class.java))
        }



    }





    fun getScreenSize(context: Context): String {
        when (context.resources.displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_MEDIUM -> return AppConstants.MDPI
            DisplayMetrics.DENSITY_HIGH -> return AppConstants.HDPI
            DisplayMetrics.DENSITY_XHIGH -> return AppConstants.XHDPI
            DisplayMetrics.DENSITY_XXHIGH -> return AppConstants.XXHDPI
            DisplayMetrics.DENSITY_XXXHIGH -> return AppConstants.XXXHDPI
            else -> return AppConstants.XXXHDPI
        }
    }










}
