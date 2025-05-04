package com.laracihan.fizyocep

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laracihan.fizyocep.databinding.EgzersizBinding
import com.laracihan.fizyocep.databinding.FragmentMisafirBinding

class Egzersizadapter(private val egzersizListe : ArrayList<Egzersiz>) : RecyclerView.Adapter<Egzersizadapter.EgzersizHolder>(){
    class EgzersizHolder(val binding: EgzersizBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EgzersizHolder {
        val binding = EgzersizBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EgzersizHolder(binding)
    }

    override fun getItemCount(): Int {
        return egzersizListe.size
    }

    override fun onBindViewHolder(holder: EgzersizHolder, position: Int) {
        holder.binding.egzersizFoto.setImageResource(egzersizListe[position].foto)
        holder.binding.egzersizTuru.text = egzersizListe[position].egzersizTuru
    }

}