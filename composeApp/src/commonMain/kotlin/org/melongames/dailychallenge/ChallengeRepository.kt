package org.melongames.dailychallenge

import DatabaseDriverFactory
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.melongames.dailychallenge.db.AppDatabase

data class ChallengeData(
    val streak: Int,
    val lastCompletionDate: Long
)

class ChallengeRepository(driverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(driverFactory.createDriver())
    private val queries = database.challengeQueries

    fun getChallengeFlow(): Flow<ChallengeData> {
        return queries.getChallenge()
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .map { entity ->
                if (entity != null) {
                    ChallengeData(
                        streak = entity.streakCount.toInt(),
                        lastCompletionDate = entity.lastCompletionDate
                    )
                } else {
                    ChallengeData(streak = 0, lastCompletionDate = 0L)
                }
            }
    }

    fun incrementStreak(currentStreak: Int) {
        val newStreak = currentStreak + 1
        val now = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()

        queries.insertOrUpdate(
            streakCount = newStreak.toLong(),
            lastCompletionDate = now
        )
    }

    fun resetStreak() {
        queries.insertOrUpdate(
            streakCount = 0L,
            lastCompletionDate = 0L
        )
    }
}