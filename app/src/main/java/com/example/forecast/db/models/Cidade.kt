package com.example.forecast.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.forecast.R
import kotlin.math.roundToInt

@Entity
data class Cidade(
    @PrimaryKey
    var codigo: String = "",
    @ColumnInfo
    var nome: String = "",
    @ColumnInfo
    var clima: String = "",
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
        return "$nome - ${parseCelsius(temperatura)}° C"
    }

    fun parseCelsius(temp: Float): Int {
        return (temp - 273.15).roundToInt()
    }

    fun getImageResource(): Int {
        return when (this.clima) {
            "01d" -> R.drawable.ic_01d
            "02d" -> R.drawable.ic_02d
            "03d" -> R.drawable.ic_03d
            "04d" -> R.drawable.ic_04d
            "09d" -> R.drawable.ic_09d
            "10d" -> R.drawable.ic_10d
            "11d" -> R.drawable.ic_11d
            "13d" -> R.drawable.ic_13d
            "50d" -> R.drawable.ic_50d
            else -> R.drawable.ic_01d
        }
    }

    fun getStringResource(): Int {
        return when (this.clima) {
            "01d" -> R.string.clima_01d
            "02d" -> R.string.clima_02d
            "03d" -> R.string.clima_03d
            "04d" -> R.string.clima_04d
            "09d" -> R.string.clima_09d
            "10d" -> R.string.clima_10d
            "11d" -> R.string.clima_11d
            "13d" -> R.string.clima_13d
            "50d" -> R.string.clima_50d
            else -> R.string.clima_01d
        }
    }

}