package com.example.forecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forecast.adapters.CidadeAdapter
import com.example.forecast.databinding.FragmentAddCidadeBinding
import com.example.forecast.db.models.Cidade
import com.example.forecast.services.DataLoader
import com.example.forecast.services.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddCidadeFragment : Fragment() {

    private lateinit var binding: FragmentAddCidadeBinding;
    var adapter: CidadeAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentAddCidadeBinding.inflate(layoutInflater)

        loadCidades()

        return binding.root
    }

    fun loadCidades() {
        val context = this.requireContext()

        val dataLoader = DataLoader(this.requireActivity())
        val webService = WebService(this.requireActivity())

        runBlocking {
            val retrieveDataJob = launch {
                webService.getAll()
            }
            retrieveDataJob.join()
            var cidades = mutableListOf<Cidade>()
            val loadDataJob = launch(Dispatchers.IO) {
                cidades = dataLoader.getCidades() as MutableList
            }
            loadDataJob.join()
            adapter = CidadeAdapter(cidades)
            binding.listAddCidades.layoutManager = LinearLayoutManager(context)
            binding.listAddCidades.adapter = adapter
        }
    }

}