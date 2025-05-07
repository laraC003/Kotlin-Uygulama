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
import com.laracihan.fizyocep.R
import com.laracihan.fizyocep.databinding.FragmentGirisBinding
import com.laracihan.fizyocep.databinding.FragmentMisafirBinding

class girisFragment : Fragment() {


    private var _binding: FragmentGirisBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGirisBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.girisButton.setOnClickListener{giris(it)}


        val guncelKullanici=auth.currentUser

        if(guncelKullanici != null){
            val kullanici = true
            val action = girisFragmentDirections.actionGirisFragmentToIcerikFragment(kullanici)
            Navigation.findNavController(view).navigate(action)
        }

    }



    fun giris(view: View){

        val email=binding.mailEditText2.text.toString()
        val password =binding.sifreEditText.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val kullanici = true
                val action = girisFragmentDirections.actionGirisFragmentToIcerikFragment(kullanici)
                Navigation.findNavController(view).navigate(action)

            }.addOnFailureListener{exception->
                Toast.makeText(requireContext(),exception.localizedMessage,Toast.LENGTH_LONG).show()

            }
        }



    }
}