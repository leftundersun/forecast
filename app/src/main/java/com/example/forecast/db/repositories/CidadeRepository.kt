package com.example.forecast.db.repositories

import androidx.lifecycle.LiveData
import com.example.forecast.db.daos.CidadeDao
import com.example.forecast.db.models.Cidade

class CidadeRepository(private val cidadeDao: CidadeDao) {

    val findAll: LiveData<List<Cidade>> = cidadeDao.findAll()

    suspend fun add(cidade: Cidade) {
        cidadeDao.add(cidade)
    }

}