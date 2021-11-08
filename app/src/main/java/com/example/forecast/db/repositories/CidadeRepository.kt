package com.example.forecast.db.repositories

import androidx.lifecycle.LiveData
import com.example.forecast.db.daos.CidadeDao
import com.example.forecast.db.models.Cidade

class CidadeRepository(private val cidadeDao: CidadeDao) {

    fun findOne(codigo: String): LiveData<Cidade>{
        return cidadeDao.findOne(codigo)
    }

    fun findAllByCodigos(codigos: Set<String>): LiveData<List<Cidade>> {
        return cidadeDao.findAllByCodigos(codigos)
    }

    fun findAllByCodigosNotIn(codigos: Set<String>): LiveData<List<Cidade>> {
        return cidadeDao.findAllByCodigosNotIn(codigos)
    }

    fun searchByNome(nome: String): LiveData<List<Cidade>> {
        return cidadeDao.searchByNome("%$nome%")
    }

    suspend fun add(cidade: Cidade) {
        cidadeDao.add(cidade)
    }

}