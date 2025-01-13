package com.example.masterand

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.masterand.data.AppContainer
import com.example.masterand.data.AppDataContainer
import com.example.masterand.viewmodels.*

class MasterAndApplication : Application() {
    private lateinit var container : AppContainer

    object AppViewModelProvider {
        val Factory = viewModelFactory {
            initializer {
                LoginViewModel(
                    masterAndApplication().container.profileRepository
                )
            }
            initializer {
                ProfileViewModel(
                    masterAndApplication().container.profileRepository
                )
            }
            initializer {
                GameViewModel(
                    masterAndApplication().container.scoreRepository
                )
            }
            initializer {
                ResultsViewModel(
                    masterAndApplication().container.profileWithScoreRepository
                )
            }
        }

        private fun CreationExtras.masterAndApplication(): MasterAndApplication =
            (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
                    MasterAndApplication)
    }

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}