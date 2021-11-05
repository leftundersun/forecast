package com.example.forecast.db.repositories

import androidx.lifecycle.LiveData
import com.example.forecast.db.daos.CidadeDao
import com.example.forecast.db.models.Cidade

class CidadeRepository(private val cidadeDao: CidadeDao) {

    var codigo = ""
    val findOne: LiveData<Cidade> = cidadeDao.findOne(codigo)

    val findAll: LiveData<List<Cidade>> = cidadeDao.findAll()

    var codigos = mutableSetOf<String>()
    val findAllByCodigos: LiveData<List<Cidade>> = cidadeDao.findAllByCodigos(codigos)
    val findAllByCodigosNotIn: LiveData<List<Cidade>> = cidadeDao.findAllByCodigosNotIn(codigos)

    suspend fun add(cidade: Cidade) {
        cidadeDao.add(cidade)
    }

    suspend fun update(cidade: Cidade) {
        cidadeDao.update(cidade)
    }

}