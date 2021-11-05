package com.example.forecast.views.add_cidade

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.forecast.db.ForecastDatabase
import com.example.forecast.db.daos.CidadeDao
import com.example.forecast.db.models.Cidade
import com.example.forecast.db.repositories.CidadeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCidadeViewModel(application: Application) : AndroidViewModel(application) {

    val findAll: LiveData<List<Cidade>>
    private val cidadeRepository: CidadeRepository

    init {
        val cidadeDao = ForecastDatabase.getDb(application).cidadeDao()
        cidadeRepository = CidadeRepository(cidadeDao)
        findAll = cidadeRepository.findAll
    }

}