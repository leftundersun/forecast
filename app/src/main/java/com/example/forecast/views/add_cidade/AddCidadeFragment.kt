package com.example.forecast.views.add_cidade

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
import com.example.forecast.databinding.FragmentAddCidadeBinding
import com.example.forecast.services.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddCidadeFragment : Fragment() {

    private lateinit var binding: FragmentAddCidadeBinding
    var sharedPrefs: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentAddCidadeBinding.inflate(layoutInflater)
        sharedPrefs = this.requireActivity().getSharedPreferences(
            resources.getString(R.string.shared_preferences_file),
            Context.MODE_PRIVATE
        )
        var viewModel = ViewModelProvider(this).get(AddCidadeViewModel::class.java)

        var adapter = CidadeAdapter()
        binding.listAddCidades.layoutManager = LinearLayoutManager(context)
        binding.listAddCidades.adapter = adapter
        adapter.onItemClick = { cidade ->
            Log.i("adapter.onItemClick", cidade.codigo)
            var codigos: MutableSet<String> = sharedPrefs!!.getStringSet(resources.getString(R.string.cidades_selecionadas_key), mutableSetOf<String>())!!
            Log.i("adapter.onItemClick", codigos.size.toString())
            codigos.add(cidade.codigo)
            Log.i("adapter.onItemClick", codigos.size.toString())
            with(sharedPrefs!!.edit()){
                putStringSet(
                    resources.getString(R.string.cidades_selecionadas_key),
                    codigos
                )
                apply()
            }
            Navigation.findNavController(binding.root).navigate(R.id.action_addCidadeFragment_to_mainFragment)
        }

        viewModel.findAllByCodigosNotIn.observe(viewLifecycleOwner, { cidades ->
            Log.i("findAllByCodigosNotIn.observe", cidades.size.toString())
            if (cidades.isNotEmpty()) {
                showList()
            } else {
                showEmptyMessage()
            }
            adapter.setContent(cidades)
        })

        loadCidades()

        return binding.root
    }

    fun loadCidades(): Boolean {
        val webService = WebService(this.requireActivity())
        runBlocking {
            launch(Dispatchers.IO) {
                webService.getAll()
            }
        }
        return true
    }

    fun showList() {
        this.binding!!.listAddCidades.visibility = View.VISIBLE
        this.binding!!.txtNoCitiesToAdd.visibility = View.GONE
    }

    fun showEmptyMessage() {
        this.binding!!.txtNoCitiesToAdd.visibility = View.VISIBLE
        this.binding!!.listAddCidades.visibility = View.GONE
    }

}