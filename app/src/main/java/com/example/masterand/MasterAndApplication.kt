package com.example.masterand

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MasterAndApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}