package com.example.forecast.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.math.roundToInt

@Entity
data class Cidade(
    @PrimaryKey
    var codigo: String = "",
    @ColumnInfo
    var nome: String = "",
    @ColumnInfo
    var temperatura: Float = 0.0f,
    @ColumnInfo
    var minima: Float = 0.0f,
    @ColumnInfo
    var maxima: Float = 0.0f,
    @ColumnInfo
    var latitude: Float = 0.0f,
    @ColumnInfo
    var longitude: Float = 0.0f,
    @ColumnInfo
    var vento: Float = 0.0f,
    @ColumnInfo
    var visibilidade: Int = 0,
    @ColumnInfo
    var umidade: Int = 0,
    @ColumnInfo
    var pressao: Int = 0) {

    override fun toString(): String {
        var cTemp = temperatura - 273.15
        return "$nome - ${cTemp.roundToInt()}° C"
    }

}