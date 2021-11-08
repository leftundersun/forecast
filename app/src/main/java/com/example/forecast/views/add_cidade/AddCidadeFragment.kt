package com.example.forecast.views.add_cidade

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
    private lateinit var viewModel: AddCidadeViewModel
    private lateinit var adapter: CidadeAdapter
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        this.binding = FragmentAddCidadeBinding.inflate(layoutInflater)
        this.sharedPrefs = this.requireActivity().getSharedPreferences(
            resources.getString(R.string.shared_preferences_file),
            Context.MODE_PRIVATE
        )

        this.setListAdapter()

        this.viewModel = ViewModelProvider(this).get(AddCidadeViewModel::class.java)
        this.setCidadesObserver()
        this.setSearchListener()

        this.loadCidades()

        return this.binding.root
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

    fun setCidadesObserver() {
        this.viewModel.findAllByCodigosNotIn.observe(viewLifecycleOwner, { cidades ->
            if (cidades.isNotEmpty()) {
                this.showList()
            } else {
                this.showEmptyMessage()
            }
            this.adapter.setContent(cidades)
        })
    }

    fun setListAdapter() {
        this.adapter = CidadeAdapter()
        this.binding.listAddCidades.layoutManager = LinearLayoutManager(context)
        this.binding.listAddCidades.adapter = adapter
        this.adapter.onItemClick = { cidade ->
            var codigos: MutableSet<String> = this.sharedPrefs.getStringSet(resources.getString(R.string.cidades_selecionadas_key), mutableSetOf<String>())!!
            codigos.add(cidade.codigo)
            with(this.sharedPrefs.edit()){
                putStringSet(
                    resources.getString(R.string.cidades_selecionadas_key),
                    codigos
                )
                apply()
            }
            Navigation.findNavController(this.binding.root).navigate(R.id.action_addCidadeFragment_to_mainFragment)
        }
    }

    fun setSearchListener() {
        this.binding.txtSearchCidades.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(search: String?): Boolean {
                if (!search.isNullOrEmpty()) {
                    viewModel.searchByNome(search).observe( viewLifecycleOwner, { cidades ->
                        if (cidades.isNotEmpty()) {
                            showList()
                        } else {
                            showEmptyMessage()
                        }
                        adapter.setContent(cidades)
                    })
                    return true
                } else {
                    return false
                }
            }
        })
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