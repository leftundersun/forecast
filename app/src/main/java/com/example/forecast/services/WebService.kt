package com.example.forecast.services

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.forecast.R
import com.example.forecast.db.ForecastDatabase
import com.example.forecast.db.daos.CidadeDao
import com.example.forecast.db.models.Cidade
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.*

class WebService(var activity: Activity) {
    private val OPENWEATHER_APIKEY = "9ee7392987cc442e73992f40a53cd62f"

    var client: OkHttpClient? = null
    var baseUrl = "http://api.openweathermap.org/data/2.5/weather"
    var cidadeDao: CidadeDao
    var sharedPrefs: SharedPreferences
    var codigos = mutableListOf("3470073","3462557","3466933","3463605","3466537","3456068","3448622","3452925","3454954","3461481")

    init {
        this.client = OkHttpClient()
        var db = ForecastDatabase.getDb(activity.application)
        this.cidadeDao = db.cidadeDao()
        this.sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE)
    }

    suspend fun getAll() {
        //verificar conexão
        for (codigo in codigos) {
            get(codigo)
        }
    }

    suspend fun get(codigo: String) {
        var url = "$baseUrl?id=$codigo&appid=$OPENWEATHER_APIKEY"
        Log.i("WEBSERICE URL", url)
        var req = Request.Builder()
            .url(url)
            .build()

        this.client!!.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("WEBSERVICE ERROR", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i("weather response", response.message)
                if (response.code != 200) {
                    Log.e("WEBSERVICE ERROR", response.toString())
                } else {
                    try {
                        var date = Date()
                        with(sharedPrefs.edit()){
                            putString(
                                activity.resources.getString(R.string.last_update_key),
                                date.toString()
                            )
                            commit()
                        }
                        var cidade = parseJson(JSONObject(response.body!!.string()))
                        runBlocking {
                            launch {
                                if (cidadeDao.findOne(codigo) != null) {
                                    cidadeDao.update(cidade)
                                } else {
                                    cidadeDao.add(cidade)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("WEBSERVICE ERROR", e.toString())
                    }
                }
            }
        })
    }

    fun parseJson(json: JSONObject): Cidade {
        var weather = json.getJSONObject("main")
        var coordinates = json.getJSONObject("coord")
        var wind = json.getJSONObject("wind")

        var cidade = Cidade(
            json.getString("id"),
            json.getString("name"),
            weather.getDouble("temp").toFloat(),
            weather.getDouble("temp_min").toFloat(),
            weather.getDouble("temp_max").toFloat(),
            coordinates.getDouble("lat").toFloat(),
            coordinates.getDouble("lon").toFloat(),
            wind.getDouble("speed").toFloat(),
            json.getInt("visibility"),
            weather.getInt("humidity"),
            weather.getInt("pressure")
        )
        return cidade
    }
}