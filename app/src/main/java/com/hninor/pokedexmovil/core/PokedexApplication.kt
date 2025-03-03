package com.hninor.pokedexmovil.core

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.hninor.pokedexmovil.core.database.AppDatabase

class PokedexApplication : Application() {

    companion object {
        lateinit var database: AppDatabase
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my_database"
        ).build()

        appContext = applicationContext
    }
}