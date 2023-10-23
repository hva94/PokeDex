package com.hvasoft.pokedex

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PokemonApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("hva_test", "Application context: $applicationContext")
    }
}