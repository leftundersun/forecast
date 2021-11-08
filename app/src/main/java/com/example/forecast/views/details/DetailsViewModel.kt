package com.example.forecast.views.details

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.forecast.R
import com.example.forecast.db.ForecastDatabase
import com.example.forecast.db.models.Cidade
import com.example.forecast.db.repositories.CidadeRepository

class DetailsViewModel(application: Application): AndroidViewModel(application) {

    var cidade: LiveData<Cidade>
    private val cidadeRepository: CidadeRepository
    private val sharedPrefs: SharedPreferences

    init {
        val cidadeDao = ForecastDatabase.getDb(application).cidadeDao()
        this.cidadeRepository = CidadeRepository(cidadeDao)

        this.sharedPrefs = application.getSharedPreferences(
            application.resources.getString(R.string.shared_preferences_file),
            Context.MODE_PRIVATE
        )

        var codigo = sharedPrefs.getString(application.resources.getString(R.string.cidade_to_show_key), "")!!
        this.cidade = cidadeRepository.findOne(codigo)
    }
}