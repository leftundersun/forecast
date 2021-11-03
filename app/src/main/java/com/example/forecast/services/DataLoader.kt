package com.example.forecast.services

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.example.forecast.db.ForecastDatabase
import com.example.forecast.db.daos.CidadeDao
import com.example.forecast.db.models.Cidade

class DataLoader(activity: Activity) {

    val CIDADES_KEY = "CIDADES_SELECIONADAS"

    var sharedPrefs: SharedPreferences
    var cidadeDao: CidadeDao

    init {
        this.cidadeDao = ForecastDatabase.getDb(activity.application).cidadeDao()
        this.sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE)
    }

    suspend fun getSelectedCidades(): List<Cidade> {
        var codigosSelecionadas = sharedPrefs.getStringSet(CIDADES_KEY, mutableSetOf<String>())
        return getCidades(codigosSelecionadas)
    }

    suspend fun getCidades(codigos: MutableSet<String>? = null): List<Cidade>{
        var cidades = mutableListOf<Cidade>()

        if (codigos != null) {
            if (codigos!!.size > 0) {
                cidades.addAll( this.cidadeDao.findAllByCodigos(codigos) )
            }
        } else {
            cidades.addAll( this.cidadeDao.findAll() )
        }

        return cidades
    }

}