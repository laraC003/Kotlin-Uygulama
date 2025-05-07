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
import com.laracihan.fizyocep.databinding.FragmentKaydolBinding

class kaydolFragment : Fragment() {

    private var _binding: FragmentKaydolBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKaydolBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button2.setOnClickListener { KayitOl(it) }
    }



    fun KayitOl(view: View){
        val email = binding.mailEditText2.text.toString()
        val password = binding.yeniParolaEditText.text.toString()
        val password2 = binding.tekrarParolaEditText.text.toString()

        if(password2 == password) {
            if (email.isNotEmpty() && password.isNotEmpty()) {

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{task->
                    if(task.isSuccessful){
                        //kullanıcı oluşturuldu
                        val action = kaydolFragmentDirections.actionKaydolFragmentToAnasayfaFragment()
                        Navigation.findNavController(view).navigate(action)
                    }
                }.addOnFailureListener{exception->
                    Toast.makeText(requireContext(),exception.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }



    }

}