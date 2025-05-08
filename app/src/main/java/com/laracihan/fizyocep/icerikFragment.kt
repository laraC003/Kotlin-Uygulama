package com.laracihan.fizyocep

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.laracihan.fizyocep.databinding.FragmentIcerikBinding
import com.laracihan.fizyocep.databinding.FragmentMisafirBinding
import kotlin.math.E

class icerikFragment : Fragment() ,PopupMenu.OnMenuItemClickListener{

    private var _binding: FragmentIcerikBinding? = null
    private val binding get()= _binding!!
    private lateinit var egzersizListe : ArrayList<Egzersiz>
    private lateinit var popup:PopupMenu
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=Firebase.auth

        val e1 = Egzersiz(R.drawable.bas,"Baş Egzersizi")
        val e2 = Egzersiz(R.drawable.boyun,"Boyun Egzersizi")
        val e3 = Egzersiz(R.drawable.sirt,"Sırt Egzersizi")
        val e4 = Egzersiz(R.drawable.bel,"Bel Egzersizi")
        val e5 = Egzersiz(R.drawable.kol,"Kol Egzersizi")
        val e6 = Egzersiz(R.drawable.el,"El Egzersizi")
        val e7 = Egzersiz(R.drawable.bacak,"Bacak Egzersizi")
        val e8 = Egzersiz(R.drawable.ayak,"Ayak Egzersizi")

        egzersizListe = arrayListOf(e1,e2,e3,e4,e5,e6,e7,e8)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIcerikBinding.inflate(inflater,container,false)
        val view =binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = Egzersizadapter(egzersizListe) { secilenEgzersiz ->
            when (secilenEgzersiz.egzersizTuru) {
                "Baş Egzersizi" -> {
                    val action = icerikFragmentDirections.actionIcerikFragmentToBasEgzersizFragment()
                    Navigation.findNavController(view).navigate(action)
                }
                "Boyun Egzersizi" -> {
                    val action = icerikFragmentDirections.actionIcerikFragmentToBoyunEgzersizFragment()
                    Navigation.findNavController(view).navigate(action)
                }
                "Sırt Egzersizi"->{
                    val action = icerikFragmentDirections.actionIcerikFragmentToSirtEgzersizFragment()
                    Navigation.findNavController(view).navigate(action)

                }
                "Bel Egzersizi"->{
                    val action = icerikFragmentDirections.actionIcerikFragmentToBelEgzersizFragment()
                    Navigation.findNavController(view).navigate(action)
                }
                "kol Egzersizi"->{
                    val action = icerikFragmentDirections.actionIcerikFragmentToKolEgzersizFragment()
                    Navigation.findNavController(view).navigate(action)
                }
                "El Egzersizi"->{
                    val action = icerikFragmentDirections.actionIcerikFragmentToElEgzersizFragment()
                    Navigation.findNavController(view).navigate(action)

                }
                "Bacak Egzersizi"->{
                    val action = icerikFragmentDirections.actionIcerikFragmentToBacakEgzersizFragment()
                    Navigation.findNavController(view).navigate(action)
                }
                "Ayak Egzersizi"->{
                    val action = icerikFragmentDirections.actionIcerikFragmentToAyakEgzersizFragment()
                    Navigation.findNavController(view).navigate(action)
                }
                else -> {
                    Toast.makeText(requireContext(), "Tanımsız egzersiz", Toast.LENGTH_SHORT).show()

                }
            }
        }

        binding.icerikRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.icerikRecyclerView.adapter = adapter

        binding.floatingActionButton.setOnClickListener { floatingButtonTiklandi(it) }

        popup = PopupMenu(requireContext(), binding.floatingActionButton)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.my_menu, popup.menu)
        popup.setOnMenuItemClickListener(this)
    }


    fun floatingButtonTiklandi(view: View){

        popup.show()

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.cikisItem){
            auth.signOut()
            val navOptions = androidx.navigation.navOptions {
                popUpTo(R.id.icerikFragment) {
                    inclusive = true // Mevcut fragment'ı da sil
                }
            }

            val action= icerikFragmentDirections.actionIcerikFragmentToGirisFragment()


            Navigation.findNavController(requireView()).navigate(action,navOptions)
        }
        return true

    }


}