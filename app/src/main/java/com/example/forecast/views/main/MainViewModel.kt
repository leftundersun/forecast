package com.example.forecast.views.main

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecast.R
import com.example.forecast.db.ForecastDatabase
import com.example.forecast.db.models.Cidade
import com.example.forecast.db.repositories.CidadeRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val findAllByCodigos: LiveData<List<Cidade>>
    val lastUpdate = MutableLiveData<String>()
    private val cidadeRepository: CidadeRepository
    private val sharedPrefs: SharedPreferences
    private val prefListener: SharedPreferences.OnSharedPreferenceChangeListener

    init {
        val cidadeDao = ForecastDatabase.getDb(application).cidadeDao()
        cidadeRepository = CidadeRepository(cidadeDao)

        sharedPrefs = application.getSharedPreferences(
            application.resources.getString(R.string.shared_preferences_file),
            Context.MODE_PRIVATE
        )
        lastUpdate.value = sharedPrefs.getString(application.resources.getString(R.string.last_update_key),"Nunca")!!

        cidadeRepository.codigos = sharedPrefs.getStringSet(application.resources.getString(R.string.cidades_selecionadas_key), emptySet<String>())!!
        findAllByCodigos = cidadeRepository.findAllByCodigos

        prefListener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            if (key == application.resources.getString(R.string.last_update_key)) {
                lastUpdate.value = sharedPrefs.getString(application.resources.getString(R.string.last_update_key),"Nunca")!!
            }
        }
        sharedPrefs.registerOnSharedPreferenceChangeListener(prefListener)
    }

}