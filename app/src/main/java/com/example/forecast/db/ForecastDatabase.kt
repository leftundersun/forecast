package com.example.forecast.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.forecast.db.daos.CidadeDao
import com.example.forecast.db.models.Cidade
import java.util.concurrent.Executors

@Database(entities = [Cidade::class], version = 1, exportSchema = false)
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
                    var builder = Room.databaseBuilder(
                        context.applicationContext,
                        ForecastDatabase::class.java,
                        "forecast"
                    )
                    builder.setQueryCallback(RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
                        Log.i("QueryCallback", "SQL Query: $sqlQuery SQL Args: $bindArgs")
                    }, Executors.newSingleThreadExecutor())
                    val instance = builder.build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}
