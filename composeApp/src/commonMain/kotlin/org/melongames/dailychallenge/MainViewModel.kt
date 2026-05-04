package org.melongames.dailychallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainState(
    val streak: Int = 0,
    val isCompletedToday: Boolean = false,
    val isLoading: Boolean = false
)

sealed class MainIntent {
    object CompleteChallenge : MainIntent()
    object ResetProgress : MainIntent()
}

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    fun onIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.CompleteChallenge -> completeChallenge()
            is MainIntent.ResetProgress -> reset()
        }
    }

    private fun completeChallenge() {
        viewModelScope.launch {
            _state.update { it.copy(
                streak = it.streak + 1,
                isCompletedToday = true
            ) }
        }
    }

    private fun reset() {
        _state.update { MainState() }
    }
}