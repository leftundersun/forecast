package com.example.forecast.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.forecast.R
import com.example.forecast.db.models.Cidade
import javax.inject.Inject

class CidadeAdapter @Inject constructor() : RecyclerView.Adapter<CidadeAdapter.CidadeViewHolder>() {

    var onItemClick: ((Cidade) -> Unit)? = null
    private var cidades = mutableListOf<Cidade>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CidadeViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_cidade, parent, false)

        return CidadeViewHolder(v)
    }

    override fun onBindViewHolder(holder: CidadeViewHolder, position: Int) {
        var cidade = cidades[position]
        holder.cidade.text = cidade.toString()
        holder.icon.setImageResource(cidade.getImageResource())
    }

    override fun getItemCount(): Int {
        return cidades.size
    }

    fun setContent(cidades: List<Cidade>) {
        this.cidades = cidades as MutableList<Cidade>
        notifyDataSetChanged()
    }

    inner class CidadeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cidade: TextView = view.findViewById(R.id.txtCidade)
        var icon: ImageView = view.findViewById(R.id.imgClima)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(cidades[adapterPosition])
            }
        }
    }

}