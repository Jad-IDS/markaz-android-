package com.ids.markaz.controller.Fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.utils.AppConstants
import com.ids.markaz.R
import kotlinx.android.synthetic.main.activity_contact_us.*

import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.toolbar_general.*

class FragmentContactUs : Fragment() , RVOnItemClickListener , OnMapReadyCallback {


    internal var llContactUs: LinearLayout?=null
    internal var mMapView: MapView?=null
    private var googleMap: GoogleMap? = null
    private var longitude: Double = 0.toDouble()
    private var latitude: Double = 0.toDouble()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(com.ids.markaz.R.layout.activity_contact_us, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        init()

        mapViewContact!!.onCreate(savedInstanceState)

        mapViewContact!!.onResume()

        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mapViewContact!!.getMapAsync { mMap ->
            googleMap = mMap

            if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                try {
                    llContactUs!!.postDelayed({


                        // For showing a move to my location button
                        if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            Log.wtf("in", "if")

                            return@postDelayed
                        }

                        Log.wtf("out", "if")

                        googleMap!!.isMyLocationEnabled = true

                        latitude = 33.864731959274
                        longitude = 35.528454780579
                        // For dropping a marker at a point on the Map
                        val sydney = LatLng(latitude, longitude)
                        googleMap!!.addMarker(MarkerOptions().position(sydney).title(resources.getString(R.string.app_name)).snippet(resources.getString(R.string.contact_us)))

                        // For zooming automatically to the location of the marker
                        val cameraPosition = CameraPosition.Builder().target(sydney).zoom(15f).build()
                        googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                    }, 4000)
                } catch (e: Exception) {
                    e.printStackTrace()
                }


            } else {

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.wtf("in", "if")
                    return@getMapAsync
                }

                Log.wtf("out", "if")

                googleMap!!.isMyLocationEnabled = true

                latitude = 33.864731959274
                longitude = 35.528454780579
                // For dropping a marker at a point on the Map
                val sydney = LatLng(latitude, longitude)
                googleMap!!.addMarker(MarkerOptions().position(sydney).title(resources.getString(R.string.app_name)).snippet(resources.getString(R.string.contact_us)))

                // For zooming automatically to the location of the marker
                val cameraPosition = CameraPosition.Builder().target(sydney).zoom(15f).build()
                googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }


            googleMap!!.setOnMapClickListener { latLng ->

                Log.wtf("Clicked", "Google map")
                val gmmIntentUri = Uri.parse("geo:$latitude,$longitude")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                if (mapIntent.resolveActivity(activity!!.packageManager) != null) {
                    startActivity(mapIntent)
                }
            }
        }

        try {

            // (activity as DrawerLocker).setDrawerEnabled(false)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {



        this.googleMap = googleMap


        val here = LatLng(33.889427, 35.505741)
        googleMap.addMarker(MarkerOptions().position(here).title("Beirut"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(here))


    }


    override fun onItemClicked(view: View, position: Int) {

    }




  fun init() {


      mMapView = mapViewContact


      btBack.setOnClickListener {

          var fragmentManager: FragmentManager = getFragmentManager()!!
          var fragmentAvailable= AppConstants.MORE
          val login = FragmentMore()
          fragmentManager.beginTransaction()
              .add(com.ids.markaz.R.id.container, login, AppConstants.MORE_FRAG)
              .commit()
      }


  }
}