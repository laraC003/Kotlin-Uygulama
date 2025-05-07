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

        // Listeye veri ekliyoruz
        val e1 = Egzersiz(R.drawable.bas, "Baş egzersizi")
        val e2 = Egzersiz(R.drawable.boyun, "Boyun egzersizi")
        val e3 = Egzersiz(R.drawable.bas, "Baş egzersizi")
        val e4 = Egzersiz(R.drawable.boyun, "Boyun egzersizi")

        egzersizListe = arrayListOf(e1, e2, e3, e4)

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
