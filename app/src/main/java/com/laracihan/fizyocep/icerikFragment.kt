package com.laracihan.fizyocep

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.laracihan.fizyocep.databinding.FragmentIcerikBinding
import com.laracihan.fizyocep.databinding.FragmentMisafirBinding

class icerikFragment : Fragment() ,PopupMenu.OnMenuItemClickListener{

    private var _binding: FragmentIcerikBinding? = null
    private val binding get()= _binding!!
    private lateinit var egzersizListe : ArrayList<Egzersiz>
    private lateinit var popup:PopupMenu
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=Firebase.auth

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
        _binding = FragmentIcerikBinding.inflate(inflater,container,false)
        val view =binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = Egzersizadapter(egzersizListe) { secilenEgzersiz ->
            val bundle = Bundle().apply {
                putString("egzersizTuru", secilenEgzersiz.egzersizTuru)
                putInt("egzersizFoto", secilenEgzersiz.foto)
            }
            Navigation.findNavController(view).navigate(R.id.egzersizDetayFragment, bundle)
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