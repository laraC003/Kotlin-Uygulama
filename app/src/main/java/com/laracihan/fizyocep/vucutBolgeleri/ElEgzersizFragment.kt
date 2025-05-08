package com.laracihan.fizyocep.vucutBolgeleri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.laracihan.fizyocep.R


class ElEgzersizFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_el_egzersiz, container, false)
    }


}