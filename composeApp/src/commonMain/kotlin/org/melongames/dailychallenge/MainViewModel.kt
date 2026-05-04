package org.melongames.dailychallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.daysUntil

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
            repository.getChallengeFlow().collectLatest { challengeData ->
                val today = kotlinx.datetime.Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date

                var activeStreak = challengeData.streak
                var isCompletedToday = false

                if (challengeData.lastCompletionDate != 0L) {
                    val lastDate =
                        kotlinx.datetime.Instant.fromEpochMilliseconds(challengeData.lastCompletionDate)
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date

                    val daysPassed = lastDate.daysUntil(today)

                    when {
                        daysPassed == 0 -> {
                            isCompletedToday = true
                        }

                        daysPassed > 1 -> {
                            activeStreak = 0
                            repository.resetStreak()
                        }
                    }
                }

                _state.update { currentState ->
                    currentState.copy(
                        streak = activeStreak,
                        isCompletedToday = isCompletedToday,
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