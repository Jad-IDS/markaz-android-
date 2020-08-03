package com.ids.markaz.controller.Activities


import android.os.Bundle


import com.google.android.gms.maps.GoogleMap
import com.ids.markaz.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng



import com.google.android.gms.maps.OnMapReadyCallback

import com.ids.markaz.controller.Base.ActivityBase

import com.google.android.gms.maps.model.MarkerOptions
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_contact_us.*
import kotlinx.android.synthetic.main.toolbar_general.*


class ActivityContactUs : ActivityBase(), OnMapReadyCallback {

    private var gmap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(getString(R.string.google_maps_key))
        }
        mapViewContact.onCreate(mapViewBundle);
        init()
    }

    private fun init(){


        tvToolbarTitle.text = getString(R.string.contact_us)
        btBack.visibility = View.VISIBLE

        btBack.setOnClickListener {
            super.onBackPressed()
        }
        linearCall.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:"+tvCall.text.toString())
            startActivity(intent)
        }

        linearEmail.setOnClickListener{
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:"+tvEmail.text.toString())
            startActivity(Intent.createChooser(emailIntent, "Send feedback"))
        }



        linearChat.setOnClickListener { Toast.makeText(applicationContext,getString(R.string.comming_soon),Toast.LENGTH_LONG).show() }
        linearFeedback.setOnClickListener { Toast.makeText(applicationContext,getString(R.string.comming_soon),Toast.LENGTH_LONG).show() }


        mapViewContact.getMapAsync(this);
    }


    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var mapViewBundle = outState.getBundle(getString(R.string.google_maps_key))
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(getString(R.string.google_maps_key), mapViewBundle)
        }

        mapViewContact!!.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        mapViewContact!!.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapViewContact!!.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapViewContact!!.onStop()
    }

    override fun onPause() {
        mapViewContact!!.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapViewContact!!.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapViewContact!!.onLowMemory()
    }

   override fun onMapReady(googleMap: GoogleMap) {
        gmap = googleMap
        gmap!!.setMinZoomPreference(12f)
        val ny = LatLng(33.8658486, 35.5483189)
        gmap!!.moveCamera(CameraUpdateFactory.newLatLng(ny))
        val markerOptions = MarkerOptions()
        markerOptions.position(ny)
        gmap!!.addMarker(markerOptions)
    }

}
