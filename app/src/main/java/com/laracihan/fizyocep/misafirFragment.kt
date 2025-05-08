package com.laracihan.fizyocep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.laracihan.fizyocep.databinding.FragmentMisafirBinding

class misafirFragment : Fragment() {

    private var _binding: FragmentMisafirBinding? = null
    private val binding get() = _binding!!

    // Egzersiz listesi burada tanımlandı
    private lateinit var egzersizListe: ArrayList<Egzersiz>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMisafirBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val e1 = Egzersiz(R.drawable.bas,"Baş Egzersizi")
        val e2 = Egzersiz(R.drawable.boyun,"Boyun Egzersizi")
        val e3 = Egzersiz(R.drawable.sirt,"Sırt Egzersizi")
        val e4 = Egzersiz(R.drawable.bel,"Bel Egzersizi")
        val e5 = Egzersiz(R.drawable.kol,"Kol Egzersizi")
        val e6 = Egzersiz(R.drawable.el,"El Egzersizi")
        val e7 = Egzersiz(R.drawable.bacak,"Bacak Egzersizi")
        val e8 = Egzersiz(R.drawable.ayak,"Ayak Egzersizi")

        egzersizListe = arrayListOf(e1,e2,e3,e4,e5,e6,e7,e8)
        

        // Adapteri sadece liste ile oluştur
        val adapter = EgzersizAdapterMisafir(egzersizListe)

        binding.recylerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recylerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
