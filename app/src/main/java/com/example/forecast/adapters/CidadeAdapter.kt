package com.example.forecast.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.forecast.R
import com.example.forecast.db.models.Cidade

class CidadeAdapter(private var cidades: MutableList<Cidade> ) : RecyclerView.Adapter<CidadeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CidadeViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_cidade, parent, false)
        return CidadeViewHolder(v)
    }

    override fun onBindViewHolder(holder: CidadeViewHolder, position: Int) {
        holder.cidade.text = cidades[position].toString()
        //set icon
    }

    override fun getItemCount(): Int {
        return cidades.size
    }

}

class CidadeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var cidade: TextView = view.findViewById(R.id.txtCidade)
    var icon: ImageView = view.findViewById(R.id.imgClima)
}