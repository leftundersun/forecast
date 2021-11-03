package com.example.forecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.forecast.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        val view = binding.root

        binding.btnAddCidade.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_addCidadeFragment)
        }

        return view
    }
}