package com.ids.markaz.controller.Activities


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log

import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.ids.inpoint.utils.RetrofitClientAuth
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.AdapterGeneralSpinner
import com.ids.markaz.controller.Adapters.AdapterNotifications
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.Base.AppCompactBase
import com.ids.markaz.controller.Fragments.FragmentBottomSeetLanguage
import com.ids.markaz.controller.Fragments.FragmentBottomSheetNotification
import com.ids.markaz.controller.MyApplication

import com.ids.markaz.model.*
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper
import com.ids.markaz.utils.RetrofitInterface
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.spCurrencies
import kotlinx.android.synthetic.main.loading.*


import kotlinx.android.synthetic.main.toolbar.tvToolbarTitle
import kotlinx.android.synthetic.main.toolbar_general.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.lang.Exception


class ActivitySettings : AppCompactBase() ,RVOnItemClickListener{



    override fun onItemClicked(view: View, position: Int) {

    }

    private lateinit var adapterNotifications: AdapterNotifications
    private var arrayNotifications=java.util.ArrayList<Notifications>()
    private var arrayCurrenciesSpinner= java.util.ArrayList<ItemSpinner>()
    private var selectedCurrencyId=0
    var imageName=""
    var ImageBase64=""
    var isImagePicked=false
    var selectedImage=""
    private var arrayCurrencies= java.util.ArrayList<ItemSpinner>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_settings)
        init()





    }

    private fun init(){

        tvToolbarTitle.text = getString(R.string.settings)

        btBack.visibility = View.VISIBLE


        val s : String ? = "https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png"

        AppHelper.setRoundImage(this,ivUser,s!!,false)


        btBack.setOnClickListener {
            super.onBackPressed()
        }
        supportActionBar!!.hide()


        btProfile.setOnClickListener {
           Toast.makeText(application,getString(R.string.comming_soon),Toast.LENGTH_LONG).show()
        }

        btLanguage.setOnClickListener {
            val bottom_fragment = FragmentBottomSeetLanguage()

            bottom_fragment.show(supportFragmentManager,"fragment_change_language")
        }

        btSecurity.setOnClickListener {

            val bottom_fragment = FragmentBottomSheetNotification()

            bottom_fragment.show(supportFragmentManager,"fragment_notification")
        }

        btNotifications.setOnClickListener {

            val i = Intent(this, ActivityNotificationSettings::class.java)
            startActivity(i)

        }



        ivUser.setOnClickListener{

            val permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            if(permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    20
                )
            }else{
                changeImage()
            }
        }


        getProfileImage()

        setSpinnerCurrencies()
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 20) {

            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                changeImage()
            }else
                Toast.makeText(applicationContext,getString(R.string.plz_allow_access), Toast.LENGTH_LONG).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1010) {
            if (data == null) {
                Toast.makeText(applicationContext, getString(R.string.unable_pick), Toast.LENGTH_LONG).show()
                return
            }
            val selectedImageUri = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

            val cursor = this.getContentResolver().query(selectedImageUri!!, filePathColumn, null, null, null)

            if (cursor != null) {

                try {
                    cursor.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    selectedImage = cursor.getString(columnIndex)
                    imageName = File(selectedImage).name



                    ImageBase64 = resizeBase64Image(encoder(selectedImage))
                    isImagePicked = true


                    try {
                        AppHelper.setImage(this, ivUser, selectedImage, true)
                    } catch (e2: Exception) {
                    }
                    updateProfileImage()

                    cursor.close()
                }catch (e:Exception){
                    Toast.makeText(applicationContext, getString(R.string.unable_to_load), Toast.LENGTH_LONG).show()}
            } else {
                Toast.makeText(applicationContext, getString(R.string.unable_to_load), Toast.LENGTH_LONG).show()
            }
        }
    }



    private fun setSpinnerCurrencies(){

        arrayCurrencies.clear()

        for (i in MyApplication.arrayCurrencies.indices)
            arrayCurrencies.add(
                if(MyApplication.languageCode == AppConstants.LANG_ENGLISH){
                    ItemSpinner(
                        MyApplication.arrayCurrencies[i].id,
                        MyApplication.arrayCurrencies[i].shortNameEn)
                }else{
                    ItemSpinner(
                        MyApplication.arrayCurrencies[i].id,
                        MyApplication.arrayCurrencies[i].shortNameAr)
                }
            )



        val adapter = AdapterGeneralSpinner(this, R.layout.spinner_text_item,
            AppConstants.START,R.color.black,R.color.black,
            AppConstants.START, arrayCurrencies)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spCurrencies!!.adapter = adapter;
        spCurrencies!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                var item=adapter.getItem(position)
                MyApplication.currencyId=item!!.id!!
                MyApplication.selectedCurrencyName=item.name!!
            }

            override fun onNothingSelected(parent: AdapterView<*>){

            }
        }


        val item: ResponseCurrency? =MyApplication.arrayCurrencies.find { it.id == MyApplication.currencyId }
        spCurrencies.setSelection(MyApplication.arrayCurrencies.indexOf(item))

    }




    private fun changeImage(){
        val galleryIntent = Intent()
        galleryIntent.type = "image/*"
        galleryIntent.action = Intent.ACTION_PICK
        val chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.choose_image))
        startActivityForResult(chooserIntent, 1010)
    }



    private fun getProfileImage(){

        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.getProfileImage(
               1


            )?.enqueue(object : Callback<ResponseUserImage> {
                override fun onResponse(call: Call<ResponseUserImage>, response: Response<ResponseUserImage>) {
                    try{
                        onUserProfileRetreived(response)
                    }catch (E: java.lang.Exception){
                    }
                }
                override fun onFailure(call: Call<ResponseUserImage>, throwable: Throwable) {
                }
            })

    }


    private fun updateProfileImage(){
      loading.visibility = View.VISIBLE

        if(ImageBase64.isEmpty()){
            try{ImageBase64=encoderURL()
                val tsLong = System.currentTimeMillis() / 1000
                val ts = tsLong.toString()
                imageName="img"+ts.toString()+".jpg"

            }catch (e: Exception){
                Log.wtf("imageException1",e.toString())
            }

        }
        Log.wtf("imagedecoded",ImageBase64);
        RetrofitClientAuth.client?.create(RetrofitInterface::class.java)
            ?.updateProfileImage(
                ResponseUserImage(userId = "1",image = ImageBase64.trim())

            )?.enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    try{
                       loading.visibility=View.GONE
                    }catch (E: java.lang.Exception){
                        Log.wtf("exception",E.toString())
                    }
                }
                override fun onFailure(call: Call<Boolean>, throwable: Throwable) {
                    loading.visibility=View.GONE
                }
            })

    }



    private fun onUserProfileRetreived(response: Response<ResponseUserImage>){
        loading.visibility=View.GONE
        AppHelper.userProfile=response.body()
        setInfo()


    }

    fun encoder(filePath: String): String{

        val bm = BitmapFactory.decodeFile(filePath)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
        val b = baos.toByteArray()
        val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
        return encodedImage
    }


    fun encoderURL(): String{

        val drawable = ivUser.drawable as RoundedBitmapDrawable
        val bm = drawable.bitmap
        val baos = ByteArrayOutputStream()
        bm!!.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
        val b = baos.toByteArray()
        val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
        return encodedImage
    }


    fun resizeBase64Image(base64image: String): String {
        val encodeByte = Base64.decode(base64image.toByteArray(), Base64.DEFAULT)
        val options = BitmapFactory.Options()
        options.inPurgeable = true
        var image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size, options)


        if (image.height <= 400 && image.width <= 400) {
            return base64image
        }
        image = Bitmap.createScaledBitmap(image, 150, 150, false)

        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)

        val b = baos.toByteArray()
        System.gc()
        return Base64.encodeToString(b, Base64.NO_WRAP)

    }



    @Throws(FileNotFoundException::class)
    fun decodeUri(c: Context, uri: Uri, requiredSize: Int): Bitmap? {
        val o = BitmapFactory.Options()
        o.inJustDecodeBounds = true
        BitmapFactory.decodeStream(c.contentResolver.openInputStream(uri), null, o)

        var width_tmp = o.outWidth
        var height_tmp = o.outHeight
        var scale = 1

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break
            width_tmp /= 2
            height_tmp /= 2
            scale *= 2
        }

        val o2 = BitmapFactory.Options()
        o2.inSampleSize = scale
        return BitmapFactory.decodeStream(c.contentResolver.openInputStream(uri), null, o2)
    }


    private fun setInfo(){
        val userProfile = AppHelper.userProfile
        imageName= userProfile!!.image.toString()
        var decodedFile :Bitmap?=null
        if(!imageName.isEmpty()) {

            try {

                val decodedString = Base64.decode(imageName, Base64.DEFAULT)
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                ivUser.setImageBitmap(decodedByte)

        /*          try {
                      Glide.with(this).load(decodedByte).asBitmap().centerCrop().dontAnimate()
                          .into(object : BitmapImageViewTarget(ivUser) {
                              override fun setResource(resource: Bitmap) {
                                  val circularBitmapDrawable =
                                      RoundedBitmapDrawableFactory.create(resources, resource)
                                  circularBitmapDrawable.isCircular = true
                                  ivUser.setImageDrawable(circularBitmapDrawable)
                              }
                          })
        } catch (e2: Exception) {
        }
*/


            } catch (e: Exception) {


            }

        }






    }

    private fun bitmapToByte(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

}
