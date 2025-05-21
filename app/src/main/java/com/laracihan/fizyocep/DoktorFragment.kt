package com.laracihan.fizyocep

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class DoktorFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: KullaniciAdapter  // Kullanıcı listeleme adapterin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_doktor, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewKullanicilar)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = KullaniciAdapter(listOf()) { kullaniciId ->
            // Kullanıcıya tıklanınca içerik fragmentına yönlendirme
            val action = DoktorFragmentDirections.actionDoktorFragmentToIcerikFragment(kullaniciId)
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        kullanicilariGetir()

        return view
    }

    private fun kullanicilariGetir() {
        db.collection("users")
            .get()
            .addOnSuccessListener { documents ->
                val kullanicilar = documents.map { doc ->
                    val email = doc.getString("email") ?: "Bilinmiyor"
                    val kullaniciAdi = doc.getString("kullaniciAdi") ?: "İsimsiz"
                    Kullanici(doc.id, email, kullaniciAdi)
                }
                adapter.updateData(kullanicilar)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Kullanıcılar getirilemedi: ${exception.localizedMessage}", Toast.LENGTH_LONG).show()
            }
    }
}
