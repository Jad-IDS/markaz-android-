package com.ids.markaz.controller.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener



class FragmentSample : Fragment() , RVOnItemClickListener {
    override fun onItemClicked(view: View, position: Int) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.markaz.R.layout.fragment_home_new, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }







}


