package com.laracihan.fizyocep

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.laracihan.fizyocep.databinding.FragmentKaydolBinding

class kaydolFragment : Fragment() {

    private var _binding: FragmentKaydolBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore  // Firestore nesnesi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()  // Firestore'u başlat
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKaydolBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button2.setOnClickListener { KayitOl(it) }
    }

    fun KayitOl(view: View) {
        val email = binding.mailEditText2.text.toString().trim()
        val password = binding.yeniParolaEditText.text.toString().trim()
        val password2 = binding.tekrarParolaEditText.text.toString().trim()
        val kullaniciAdi = binding.isimEditText.text.toString().trim()  // Kullanıcı adı alanı

        if (password2 == password) {
            if (email.isNotEmpty() && password.isNotEmpty() && kullaniciAdi.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val kullanici = auth.currentUser
                        if (kullanici != null) {
                            val kullaniciMap = hashMapOf(
                                "email" to email,
                                "kullaniciAdi" to kullaniciAdi
                            )
                            // Firestore'a kullanıcı bilgilerini kaydet
                            db.collection("users").document(kullanici.uid)
                                .set(kullaniciMap)
                                .addOnSuccessListener {
                                    // Kayıt ve Firestore kaydı başarılı, anasayfaya git
                                    val action = kaydolFragmentDirections.actionKaydolFragmentToAnasayfaFragment()
                                    Navigation.findNavController(view).navigate(action)
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
                                }
                        }
                    } else {
                        Toast.makeText(requireContext(), task.exception?.localizedMessage ?: "Kayıt başarısız", Toast.LENGTH_LONG).show()
                    }
                }.addOnSuccessListener {
                    Toast.makeText(requireContext(), "Kaydınız başarılı", Toast.LENGTH_SHORT).show()

                    val action = kaydolFragmentDirections.actionKaydolFragmentToAnasayfaFragment()
                    Navigation.findNavController(view).navigate(action)
                }
            } else {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Şifreler eşleşmiyor", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
