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
import com.google.firebase.ktx.Firebase
import com.laracihan.fizyocep.databinding.FragmentGirisBinding

class girisFragment : Fragment() {

    private var _binding: FragmentGirisBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGirisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.girisButton.setOnClickListener {
            val email = binding.mailEditText2.text.toString().trim()
            val password = binding.sifreEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Lütfen e-posta ve şifre giriniz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val girisYapanEmail = auth.currentUser?.email
                        if (girisYapanEmail == "doktor@gmail.com") {
                            // Doktor kullanıcısı için DoktorFragment'a yönlendir
                            val action = girisFragmentDirections.actionGirisFragmentToDoktorFragment()
                            Navigation.findNavController(view).navigate(action)
                        } else {
                            // Diğer kullanıcılar için içerik fragmentına
                            val kullaniciId = auth.currentUser?.uid ?: ""
                            val action = girisFragmentDirections.actionGirisFragmentToIcerikFragment(kullaniciId, true)
                            Navigation.findNavController(view).navigate(action)
                        }
                    } else {
                        Toast.makeText(requireContext(),  "Giriş başarısız", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
