package com.example.forecast.views.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.forecast.R
import com.example.forecast.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    var sharedPrefs: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        val view = binding.root

        this.sharedPrefs = this.requireActivity().getPreferences(Context.MODE_PRIVATE)

        setOnClickListener()
        setLastUpdateText()

        return view
    }

    fun setOnClickListener() {
        binding.btnAddCidade.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFragment_to_addCidadeFragment)
        }
    }

    fun setLastUpdateText() {
        var lastUpdateLabel = resources.getString(R.string.last_update)
        var lastUpdate = sharedPrefs!!.getString(
            resources.getString(R.string.last_update_key),
            "Nunca"
        )
        binding.txtLastUpdate.text = "$lastUpdateLabel $lastUpdate"
    }
}