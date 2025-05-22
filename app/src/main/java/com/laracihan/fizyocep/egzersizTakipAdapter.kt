package com.laracihan.fizyocep

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laracihan.fizyocep.databinding.EgzersiztakipBinding

class EgzersizTakipAdapter(
    private val egzersizTakipListe: List<EgzersizTakip>,
    private val onCheckedChange: (EgzersizTakip, Boolean) -> Unit
) : RecyclerView.Adapter<EgzersizTakipAdapter.EgzersizViewHolder>() {

    class EgzersizViewHolder(val binding: EgzersiztakipBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EgzersizViewHolder {
        val binding = EgzersiztakipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EgzersizViewHolder(binding)
    }

    override fun getItemCount(): Int = egzersizTakipListe.size

    override fun onBindViewHolder(holder: EgzersizViewHolder, position: Int) {
        val egzersizTakip = egzersizTakipListe[position]
        holder.binding.egzersizAdi.text = egzersizTakip.egzersizAdi
        holder.binding.gif.setImageResource(egzersizTakip.gif)


        holder.binding.checkBox.setOnCheckedChangeListener(null)
        holder.binding.checkBox.isChecked = egzersizTakip.isChecked


        holder.binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            egzersizTakip.isChecked = isChecked
            onCheckedChange(egzersizTakip, isChecked)
        }
    }
}
