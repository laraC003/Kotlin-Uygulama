package com.laracihan.fizyocep.vucutBolgeleri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.laracihan.fizyocep.EgzersizTakip
import com.laracihan.fizyocep.EgzersizTakipAdapter
import com.laracihan.fizyocep.R
import com.laracihan.fizyocep.databinding.FragmentBasEgzersizBinding

class BasEgzersizFragment : Fragment() {

    private var _binding: FragmentBasEgzersizBinding? = null
    private val binding get() = _binding!!

    private lateinit var egzersizTakipListe: ArrayList<EgzersizTakip>
    private lateinit var auth: FirebaseAuth
    private lateinit var egzersizTakipAdapter: EgzersizTakipAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        val e1 = EgzersizTakip(R.drawable.boyunmenu, "Başını Sağa Ey", false)
        val e2 = EgzersizTakip(R.drawable.boyunmenu, "Başını Sola Ey", false)
        val e3 = EgzersizTakip(R.drawable.boyunmenu, "Başını Yukarı Kaldır", false)
        val e4 = EgzersizTakip(R.drawable.boyunmenu, "Başını Aşağı Eğ", false)

        egzersizTakipListe = arrayListOf(e1, e2, e3, e4)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasEgzersizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.egzersizTakipRecyclerView.layoutManager = GridLayoutManager(requireContext(), 1)

        egzersizTakipAdapter = EgzersizTakipAdapter(egzersizTakipListe) { egzersiz, isChecked ->
            // Checkbox değişiminde yapılacaklar (isteğe bağlı)
        }

        binding.egzersizTakipRecyclerView.adapter = egzersizTakipAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
