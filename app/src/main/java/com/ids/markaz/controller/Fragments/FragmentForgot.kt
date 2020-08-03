package com.ids.markaz.controller.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ids.markaz.R
import com.ids.markaz.controller.Activities.ActivityLogin
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.utils.AppHelper
import kotlinx.android.synthetic.main.fragment_forgot_password.*

class FragmentForgot : Fragment() , RVOnItemClickListener {






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.markaz.R.layout.fragment_forgot_password, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    fun init (){

        btSubmit.setOnClickListener {


            if(etUsrnameF.text.toString().isEmpty())
                AppHelper.createDialog(activity!!,getString(R.string.check_empty_fields))
            else {

                val i = Intent(activity!!, ActivityLogin::class.java)
                // i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }

        }


    }





    override fun onItemClicked(view: View, position: Int) {

    }


}