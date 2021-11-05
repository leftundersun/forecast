package com.example.forecast.views.main

import android.os.Bundle
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)

        var viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        var adapter = CidadeAdapter()
        binding.listHomepage.layoutManager = LinearLayoutManager(context)
        binding.listHomepage.adapter = adapter

        viewModel.findAllByCodigos.observe(viewLifecycleOwner, { cidades ->
            if (cidades.isNotEmpty()) {
                showList()
            } else {
                showEmptyMessage()
            }
            adapter.setContent(cidades)
        })

        viewModel.lastUpdate.observe(viewLifecycleOwner, { lastUpdate ->
            var lastUpdateLabel = resources.getString(R.string.last_update)
            binding.txtLastUpdate.text = "$lastUpdateLabel $lastUpdate"
        })

        setOnClickListener()

        return binding.root
    }

    fun setOnClickListener() {
        binding.btnAddCidade.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFragment_to_addCidadeFragment)
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