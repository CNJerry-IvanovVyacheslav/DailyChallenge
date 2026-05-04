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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
@Preview
fun App() {
    MaterialTheme {
        var streakCount by remember { mutableStateOf(0) }
        var isCompletedToday by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Твой стрик: $streakCount 🔥",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    isCompletedToday = true
                    streakCount++
                },
                enabled = !isCompletedToday,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(if (isCompletedToday) "Задача на сегодня выполнена!" else "Отметить задачу")
            }
        }
    }
}