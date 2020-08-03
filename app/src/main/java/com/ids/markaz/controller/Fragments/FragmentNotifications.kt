package com.ids.markaz.controller.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.utils.AppConstants
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.toolbar_general.*

class FragmentNotifications : Fragment() , RVOnItemClickListener{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.markaz.R.layout.fragment_notifications, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        init()
    }



    override fun onItemClicked(view: View, position: Int) {

    }

    fun init() {



        btBack.setOnClickListener {



            var fragmentManager: FragmentManager = getFragmentManager()!!

            var fragmentAvailable= AppConstants.NOTIFICATION


            fragmentAvailable = AppConstants.NOTIFICATION
            val login = FragmentSettings()
            fragmentManager.beginTransaction()
                .add(com.ids.markaz.R.id.container, login, AppConstants.NOTIFICATION_FRAG)
                .commit()


        }



    }

}