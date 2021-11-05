package com.example.forecast.views.main

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecast.R
import com.example.forecast.db.ForecastDatabase
import com.example.forecast.db.models.Cidade
import com.example.forecast.db.repositories.CidadeRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var findAllByCodigos: LiveData<List<Cidade>>
    val lastUpdate = MutableLiveData<String>()
    private val cidadeRepository: CidadeRepository
    private val sharedPrefs: SharedPreferences
    private val mainPrefListener: SharedPreferences.OnSharedPreferenceChangeListener

    init {
        val cidadeDao = ForecastDatabase.getDb(application).cidadeDao()
        cidadeRepository = CidadeRepository(cidadeDao)

        sharedPrefs = application.getSharedPreferences(
            application.resources.getString(R.string.shared_preferences_file),
            Context.MODE_PRIVATE
        )
        lastUpdate.value = sharedPrefs.getString(application.resources.getString(R.string.last_update_key),"Nunca")!!

        cidadeRepository.codigos = sharedPrefs.getStringSet(application.resources.getString(R.string.cidades_selecionadas_key), mutableSetOf<String>())!!
        Log.i("cidadeRepository.codigos", cidadeRepository.codigos.size.toString())
        findAllByCodigos = cidadeRepository.findAllByCodigos

        mainPrefListener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            Log.i("main.prefListener", key)
            if (key == application.resources.getString(R.string.last_update_key)) {
                lastUpdate.value = sharedPrefs.getString(application.resources.getString(R.string.last_update_key),"Nunca")!!
            }
            if (key == application.resources.getString(R.string.cidades_selecionadas_key)) {
                cidadeRepository.codigos = sharedPrefs.getStringSet(application.resources.getString(R.string.cidades_selecionadas_key), mutableSetOf<String>())!!
                Log.i("cidadeRepository.codigos", cidadeRepository.codigos.size.toString())
                findAllByCodigos = cidadeRepository.findAllByCodigos
            }
        }
        sharedPrefs.registerOnSharedPreferenceChangeListener(mainPrefListener)
    }

}