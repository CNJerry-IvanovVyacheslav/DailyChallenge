package org.melongames.dailychallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainState(
    val streak: Int = 0,
    val isCompletedToday: Boolean = false,
    val isLoading: Boolean = true
)

sealed class MainIntent {
    object CompleteChallenge : MainIntent()
    object ResetProgress : MainIntent()
}

class MainViewModel(
    private val repository: ChallengeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        observeDatabase()
    }

    private fun observeDatabase() {
        viewModelScope.launch {
            repository.getStreakFlow().collectLatest { savedStreak ->
                _state.update { currentState ->
                    currentState.copy(
                        streak = savedStreak,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.CompleteChallenge -> {
                repository.incrementStreak(_state.value.streak)
            }

            is MainIntent.ResetProgress -> {
                repository.resetStreak()
            }
        }
    }
}