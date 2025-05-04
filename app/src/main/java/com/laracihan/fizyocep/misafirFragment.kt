package com.laracihan.fizyocep

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laracihan.fizyocep.R
import com.laracihan.fizyocep.databinding.FragmentAnasayfaBinding
import com.laracihan.fizyocep.databinding.FragmentMisafirBinding


class misafirFragment : Fragment() {
    private var _binding: FragmentMisafirBinding? = null
    private val binding get()= _binding!!
    private lateinit var egzersizListe : ArrayList<Egzersiz>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val e1 = Egzersiz(R.drawable.bas,"Baş egzersizi")
        val e2 =  Egzersiz(R.drawable.boyun,"Boyun egzersizi")
        val e3 = Egzersiz(R.drawable.bas,"Baş egzersizi")
        val e4 =  Egzersiz(R.drawable.boyun,"Boyun egzersizi")

        egzersizListe = arrayListOf(e1,e2,e3,e4)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMisafirBinding.inflate(inflater,container,false)
        val view =binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = Egzersizadapter(egzersizListe)
        /*
        val layoutManager : GridLayoutManager = GridLayoutManager(requireContext(),2)
        binding.recylerView.layoutManager = layoutManager
        */
        binding.recylerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.recylerView.adapter = adapter
    }






}