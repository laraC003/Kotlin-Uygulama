package com.laracihan.fizyocep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.laracihan.fizyocep.databinding.FragmentIcerikBinding

class icerikFragment : Fragment(), PopupMenu.OnMenuItemClickListener {

    private var _binding: FragmentIcerikBinding? = null
    private val binding get() = _binding!!

    private lateinit var egzersizListe: ArrayList<Egzersiz>
    private lateinit var popup: PopupMenu
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()

        egzersizListe = arrayListOf(
            Egzersiz(R.drawable.omuz, "Omuz Egzersizi"),
            Egzersiz(R.drawable.boyun, "Boyun Egzersizi"),
            Egzersiz(R.drawable.sirt, "Sırt Egzersizi"),
            Egzersiz(R.drawable.bel, "Bel Egzersizi"),
            Egzersiz(R.drawable.kol, "Kol Egzersizi"),
            Egzersiz(R.drawable.el, "El Egzersizi"),
            Egzersiz(R.drawable.bacak, "Bacak Egzersizi"),
            Egzersiz(R.drawable.ayak, "Ayak Egzersizi")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIcerikBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = icerikFragmentArgs.fromBundle(requireArguments())
        val kullaniciId = args.kullaniciId ?: ""
        val kullaniciBoolean = args.kullanici

        val kullaniciIdToFetch = if (kullaniciId.isNotEmpty()) kullaniciId else auth.currentUser?.uid

        if (!kullaniciIdToFetch.isNullOrEmpty()) {
            db.collection("users").document(kullaniciIdToFetch).get()
                .addOnSuccessListener { document ->
                    val kullaniciAdi = document.getString("kullaniciAdi")
                    binding.kullaniciAdiTextView.text = kullaniciAdi ?: "Kullanıcı"
                }
                .addOnFailureListener {
                    binding.kullaniciAdiTextView.text = "Kullanıcı"
                }
        } else {
            binding.kullaniciAdiTextView.text = "Giriş yapılmadı"
        }

        val adapter = Egzersizadapter(egzersizListe) { secilenEgzersiz ->
            when (secilenEgzersiz.egzersizTuru) {
                "Omuz Egzersizi" -> Navigation.findNavController(view).navigate(
                    icerikFragmentDirections.actioIcerikFragmentToOmuzEgzersizFragment()
                )
                "Boyun Egzersizi" -> Navigation.findNavController(view).navigate(
                    icerikFragmentDirections.actionIcerikFragmentToBoyunEgzersizFragment()
                )
                "Sırt Egzersizi" -> Navigation.findNavController(view).navigate(
                    icerikFragmentDirections.actionIcerikFragmentToSirtEgzersizFragment()
                )
                "Bel Egzersizi" -> Navigation.findNavController(view).navigate(
                    icerikFragmentDirections.actionIcerikFragmentToBelEgzersizFragment()
                )
                "Kol Egzersizi" -> Navigation.findNavController(view).navigate(
                    icerikFragmentDirections.actionIcerikFragmentToKolEgzersizFragment()
                )
                "El Egzersizi" -> Navigation.findNavController(view).navigate(
                    icerikFragmentDirections.actionIcerikFragmentToElEgzersizFragment()
                )
                "Bacak Egzersizi" -> Navigation.findNavController(view).navigate(
                    icerikFragmentDirections.actionIcerikFragmentToBacakEgzersizFragment()
                )
                "Ayak Egzersizi" -> Navigation.findNavController(view).navigate(
                    icerikFragmentDirections.actionIcerikFragmentToAyakEgzersizFragment()
                )
                else -> Toast.makeText(requireContext(), "Tanımsız egzersiz", Toast.LENGTH_SHORT).show()
            }
        }

        binding.icerikRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.icerikRecyclerView.adapter = adapter

        binding.floatingActionButton.setOnClickListener { floatingButtonTiklandi(it) }

        popup = PopupMenu(requireContext(), binding.floatingActionButton)
        popup.menuInflater.inflate(R.menu.my_menu, popup.menu)
        popup.setOnMenuItemClickListener(this)
    }


    private fun floatingButtonTiklandi(view: View) {
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.cikisItem -> {
                auth.signOut()
                val navOptions = androidx.navigation.navOptions {
                    popUpTo(R.id.icerikFragment) { inclusive = true }
                }
                val action = icerikFragmentDirections.actionIcerikFragmentToGirisFragment()
                Navigation.findNavController(requireView()).navigate(action, navOptions)
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
