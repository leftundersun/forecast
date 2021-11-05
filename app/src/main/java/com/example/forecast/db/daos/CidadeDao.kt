package com.example.forecast.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.forecast.db.models.Cidade

@Dao
interface CidadeDao {

    @Query("SELECT * FROM cidade WHERE codigo = :codigo LIMIT 1")
    fun findOne(codigo: String): LiveData<Cidade>

    @Query("SELECT * FROM cidade")
    fun findAll(): LiveData<List<Cidade>>

    @Query("SELECT * FROM cidade WHERE codigo IN (:codigos)")
    fun findAllByCodigos(codigos: Set<String>): LiveData<List<Cidade>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun add(cidade: Cidade)

    @Update
    suspend fun update(cidade: Cidade)

    @Delete
    suspend fun delete(cidade: Cidade)

}