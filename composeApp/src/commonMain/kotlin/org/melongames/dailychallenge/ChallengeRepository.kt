package org.melongames.dailychallenge

import DatabaseDriverFactory
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.melongames.dailychallenge.db.AppDatabase

class ChallengeRepository(driverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(driverFactory.createDriver())
    private val queries = database.challengeQueries

    fun getStreakFlow(): Flow<Int> {
        return queries.getChallenge()
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .map { entity ->
                entity?.streakCount?.toInt() ?: 0
            }
    }

    fun incrementStreak(currentStreak: Int) {
        val newStreak = currentStreak + 1
        queries.insertOrUpdate(
            streakCount = newStreak.toLong(),
            lastCompletionDate = 0L
        )
    }

    fun resetStreak() {
        queries.insertOrUpdate(
            streakCount = 0L,
            lastCompletionDate = 0L
        )
    }
}