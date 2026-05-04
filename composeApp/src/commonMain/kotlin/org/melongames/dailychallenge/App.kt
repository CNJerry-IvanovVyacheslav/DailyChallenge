package org.melongames.dailychallenge

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    KoinContext {
        MaterialTheme {
            val viewModel: MainViewModel = koinInject()
            val state by viewModel.state.collectAsStateWithLifecycle()

            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Your streak: ${state.streak} 🔥",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.onIntent(MainIntent.CompleteChallenge) },
                    enabled = !state.isCompletedToday,
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Text(if (state.isCompletedToday) "Completed for today!" else "Complete Task")
                }

                if (state.streak > 0) {
                    TextButton(onClick = { viewModel.onIntent(MainIntent.ResetProgress) }) {
                        Text("Reset Progress")
                    }
                }
            }
        }
    }
}