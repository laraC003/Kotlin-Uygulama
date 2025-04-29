package com.laracihan.fizyocep

import android.graphics.Path.Direction
import android.os.Bundle
import android.text.Layout.Directions
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.laracihan.fizyocep.databinding.FragmentAnasayfaBinding

class anasayfaFragment : Fragment() {
    private var _binding:FragmentAnasayfaBinding? = null
    private val binding get()= _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnasayfaBinding.inflate(inflater,container,false)
        val view =binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.girisButonu.setOnClickListener {
            giris(it)
        }

        binding.kaydolButonu.setOnClickListener {
            kaydol(it)
        }
        binding.misafirButonu.setOnClickListener {
            misafir(it)
        }



    }



    fun giris(view: View) {
        val action = anasayfaFragmentDirections.actionAnasayfaFragmentToGirisFragment()
        Navigation.findNavController(view).navigate(action)
    }

    fun kaydol(view: View) {
        val action = anasayfaFragmentDirections.actionAnasayfaFragmentToKaydolFragment()
        Navigation.findNavController(view).navigate(action)
    }


    fun misafir(view: View) {
        val action = anasayfaFragmentDirections.actionAnasayfaFragmentToMisafirFragment()
        Navigation.findNavController(view).navigate(action)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }
}