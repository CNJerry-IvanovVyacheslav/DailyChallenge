package org.melongames.dailychallenge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Text("Complete Task")
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