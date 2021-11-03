package com.example.forecast.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.forecast.db.daos.CidadeDao
import com.example.forecast.db.models.Cidade

@Database(entities = [Cidade::class], version = 1)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun cidadeDao(): CidadeDao

    companion object {
        @Volatile
        private var INSTANCE: ForecastDatabase? = null

        fun getDb(context: Context): ForecastDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            } else {
                synchronized(this){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ForecastDatabase::class.java,
                        "forecast"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}
