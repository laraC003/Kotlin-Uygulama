package com.laracihan.fizyocep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DoktorFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: KullaniciAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_doktor, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewKullanicilar)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = KullaniciAdapter(listOf()) { kullaniciId ->
            val action = DoktorFragmentDirections.actionDoktorFragmentToIcerikFragment(
                kullaniciId = kullaniciId,
                kullanici = true
            )
            findNavController().navigate(action)
        }

        recyclerView.adapter = adapter

        kullanicilariGetir()

        return view
    }

    private fun kullanicilariGetir() {
        val currentUserEmail = auth.currentUser?.email

        db.collection("users")
            .get()
            .addOnSuccessListener { documents ->
                val kullanicilar = documents.mapNotNull { doc ->
                    val email = doc.getString("email") ?: return@mapNotNull null
                    val kullaniciAdi = doc.getString("kullaniciAdi") ?: "İsimsiz"

                    if (currentUserEmail == email) return@mapNotNull null

                    Kullanici(doc.id, email, kullaniciAdi)
                }
                adapter.updateData(kullanicilar)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    requireContext(),
                    "Kullanıcılar getirilemedi: ${exception.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}
