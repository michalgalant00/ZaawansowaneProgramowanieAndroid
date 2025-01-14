package com.example.masterand.viewmodels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.masterand.models.ProfileWithScore
import com.example.masterand.repositories.ProfileWithScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor (
    private val profileWithScoreRepository: ProfileWithScoreRepository
) : ViewModel() {

    var profilesWithScores = mutableStateOf<List<ProfileWithScore>>(emptyList())
    var selectedNumber = mutableIntStateOf(5)
    var expanded = mutableStateOf(false)
    val availableNumbers = listOf(5, 6, 7, 8, 9, 10)

    private suspend fun getProfilesWithScores(selectedNumber: Int) {
        profilesWithScores.value =
            profileWithScoreRepository.getProfilesWithScores(selectedNumber)
                .sortedBy { it.scoreValue }
    }

    suspend fun onNumberSelected(number: Int) {
        selectedNumber.intValue = number
        expanded.value = false

        getProfilesWithScores(selectedNumber.intValue)
    }

    fun toggleDropdown() {
        expanded.value = !expanded.value
    }

    fun closeDropdown() {
        expanded.value = false
    }

    fun resetViewModel() {
        profilesWithScores = mutableStateOf<List<ProfileWithScore>>(emptyList())
        selectedNumber = mutableIntStateOf(5)
        expanded = mutableStateOf(false)
    }
}