package com.example.forecast.views.details

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.forecast.R
import com.example.forecast.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        this.sharedPrefs = this.requireActivity().getSharedPreferences(
            resources.getString(R.string.shared_preferences_file),
            Context.MODE_PRIVATE
        )

        this.viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        this.setCidadeObserver()

        return binding.root
    }

    fun setCidadeObserver() {
        this.viewModel.cidade.observe(viewLifecycleOwner, { cidade ->
            
            this.binding.txtInfoCidade.text = cidade.nome
            this.binding.txtInfoTemperatura.text = resources.getString(R.string.temperatura_interpolation, cidade.parseCelsius(cidade.temperatura))

            this.binding.imgInfoClima.setImageResource(cidade.getImageResource())
            this.binding.txtInfoWeather.text = resources.getString(cidade.getStringResource())

            this.binding.txtInfoMinima.text = resources.getString(R.string.minima_interpolation, cidade.parseCelsius(cidade.minima))
            this.binding.txtInfoMaxima.text = resources.getString(R.string.maxima_interpolation, cidade.parseCelsius(cidade.maxima))
            this.binding.txtInfoLatitude.text = resources.getString(R.string.latitude_interpolation, cidade.latitude)
            this.binding.txtInfoLongitude.text = resources.getString(R.string.longitude_interpolation, cidade.longitude)

            this.binding.txtInfoWind.text = resources.getString(R.string.vento_interpolation, cidade.vento)
            this.binding.txtInfoVisibility.text = resources.getString(R.string.visibilidade_interpolation, (cidade.visibilidade.toFloat() / 1000))
            this.binding.txtInfoUmidade.text = resources.getString(R.string.umidade_interpolation, cidade.umidade)
            this.binding.txtInfoPressao.text = resources.getString(R.string.pressao_interpolation, cidade.pressao)

        })
    }
}