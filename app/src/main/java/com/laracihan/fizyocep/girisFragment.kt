package com.laracihan.fizyocep

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.laracihan.fizyocep.R
import com.laracihan.fizyocep.databinding.FragmentGirisBinding
import com.laracihan.fizyocep.databinding.FragmentMisafirBinding

class girisFragment : Fragment() {


    private var _binding: FragmentGirisBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGirisBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.girisButton.setOnClickListener{giris(it)}

    }

    fun giris(view: View){
        val action = girisFragmentDirections.actionGirisFragmentToIcerikFragment()
        Navigation.findNavController(view).navigate(action)
    }
}