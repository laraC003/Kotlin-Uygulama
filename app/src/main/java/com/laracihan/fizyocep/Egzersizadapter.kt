package com.laracihan.fizyocep

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laracihan.fizyocep.databinding.EgzersizBinding
import com.laracihan.fizyocep.databinding.FragmentMisafirBinding

class Egzersizadapter(
    private val egzersizListe: ArrayList<Egzersiz>,
    private val onItemClick: (Egzersiz) -> Unit
) : RecyclerView.Adapter<Egzersizadapter.EgzersizHolder>() {

    class EgzersizHolder(val binding: EgzersizBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EgzersizHolder {
        val binding = EgzersizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EgzersizHolder(binding)
    }

    override fun getItemCount(): Int = egzersizListe.size

    override fun onBindViewHolder(holder: EgzersizHolder, position: Int) {
        val egzersiz = egzersizListe[position]
        holder.binding.egzersizFoto.setImageResource(egzersiz.foto)
        holder.binding.egzersizTuru.text = egzersiz.egzersizTuru

        holder.itemView.setOnClickListener {
            onItemClick(egzersiz)
        }
    }
}
