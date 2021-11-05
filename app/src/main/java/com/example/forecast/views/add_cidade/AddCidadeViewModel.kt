package com.example.forecast.views.add_cidade

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.forecast.R
import com.example.forecast.db.ForecastDatabase
import com.example.forecast.db.models.Cidade
import com.example.forecast.db.repositories.CidadeRepository

class AddCidadeViewModel(application: Application) : AndroidViewModel(application) {

    var findAllByCodigosNotIn: LiveData<List<Cidade>>
    private val cidadeRepository: CidadeRepository
    private val sharedPrefs: SharedPreferences
    private val addCidadePrefListener: SharedPreferences.OnSharedPreferenceChangeListener

    init {
        val cidadeDao = ForecastDatabase.getDb(application).cidadeDao()
        cidadeRepository = CidadeRepository(cidadeDao)

        sharedPrefs = application.getSharedPreferences(
            application.resources.getString(R.string.shared_preferences_file),
            Context.MODE_PRIVATE
        )
        cidadeRepository.codigos = sharedPrefs.getStringSet(application.resources.getString(R.string.cidades_selecionadas_key), mutableSetOf<String>())!!
        Log.i("cidadeRepository.codigos", cidadeRepository.codigos.size.toString())
        findAllByCodigosNotIn = cidadeRepository.findAllByCodigosNotIn

        addCidadePrefListener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            Log.i("cidadeRepository.codigos", cidadeRepository.codigos.size.toString())
            if (key == application.resources.getString(R.string.cidades_selecionadas_key)) {
                cidadeRepository.codigos = sharedPrefs.getStringSet(application.resources.getString(R.string.cidades_selecionadas_key), mutableSetOf<String>())!!
                findAllByCodigosNotIn = cidadeRepository.findAllByCodigosNotIn
            }
        }
        sharedPrefs.registerOnSharedPreferenceChangeListener(addCidadePrefListener)
    }

}