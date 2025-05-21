package com.laracihan.fizyocep

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Kullanici(val id: String, val email: String, val kullaniciAdi: String)

class KullaniciAdapter(
    private var kullanicilar: List<Kullanici>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<KullaniciAdapter.KullaniciViewHolder>() {

    inner class KullaniciViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KullaniciViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kullanici, parent, false)
        return KullaniciViewHolder(view)
    }

    override fun getItemCount(): Int = kullanicilar.size

    override fun onBindViewHolder(holder: KullaniciViewHolder, position: Int) {
        val kullanici = kullanicilar[position]
        val textView = holder.itemView.findViewById<TextView>(R.id.textViewKullaniciAdi)
        textView.text = kullanici.kullaniciAdi

        holder.itemView.setOnClickListener {
            onItemClick(kullanici.id)
        }
    }

    fun updateData(newList: List<Kullanici>) {
        kullanicilar = newList
        notifyDataSetChanged()
    }
}

