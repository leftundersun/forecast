package com.example.forecast.views.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forecast.R
import com.example.forecast.adapters.CidadeAdapter
import com.example.forecast.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CidadeAdapter
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        this.binding = FragmentMainBinding.inflate(layoutInflater)
        this.sharedPrefs = this.requireActivity().getSharedPreferences(
            resources.getString(R.string.shared_preferences_file),
            Context.MODE_PRIVATE
        )

        this.setListAdapter()

        this.viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        this.setCidadesObserver()
        this.setLastUpdateObserver()
        this.setOnClickListener()

        return binding.root
    }

    fun setListAdapter() {
        this.adapter = CidadeAdapter()
        this.binding.listHomepage.layoutManager = LinearLayoutManager(context)
        this.binding.listHomepage.adapter = this.adapter
        this.adapter.onItemClick = { cidade ->
            Log.i("adapter.onItemClick", cidade.codigo)
            var codigo = cidade.codigo
            with(this.sharedPrefs.edit()){
                putString(
                    resources.getString(R.string.cidade_to_show_key),
                    codigo
                )
                apply()
            }
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFragment_to_detailsFragment)
        }
    }

    fun setCidadesObserver() {
        this.viewModel.findAllByCodigos.observe(viewLifecycleOwner, { cidades ->
            Log.i("findAllByCodigos.observe", cidades.size.toString())
            if (cidades.isNotEmpty()) {
                this.showList()
            } else {
                this.showEmptyMessage()
            }
            this.adapter.setContent(cidades)
        })
    }

    fun setLastUpdateObserver() {
        this.viewModel.lastUpdate.observe(viewLifecycleOwner, { lastUpdate ->
            this.binding.txtLastUpdate.text = resources.getString(R.string.last_update_interpolation, lastUpdate)
        })
    }

    fun setOnClickListener() {
        this.binding.btnAddCidade.setOnClickListener {
            Navigation.findNavController(this.binding.root).navigate(R.id.action_mainFragment_to_addCidadeFragment)
        }
    }

    fun showList() {
        this.binding!!.listHomepage.visibility = View.VISIBLE
        this.binding!!.txtNoCities.visibility = View.GONE
    }

    fun showEmptyMessage() {
        this.binding!!.txtNoCities.visibility = View.VISIBLE
        this.binding!!.listHomepage.visibility = View.GONE
    }
}