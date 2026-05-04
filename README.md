# DailyChallenge

A simple KMP pet project for exploring multiplatform development. A one-button habit tracker that locks for the rest of the day after a task is completed and resets the streak if a day is missed.

## Stack & Architecture
All business logic, DI, and database operations are located in `commonMain` (~95% of the code).

* **UI:** Compose Multiplatform
* **Architecture:** Classic MVI (ViewModel + StateFlow)
* **Database:** SQLDelight (platform SQLite drivers via `expect/actual`)
* **DI:** Koin
* **Time:** `kotlinx-datetime` (timezone-aware date calculations)

## TODO
* Compile the iOS target (requires macOS to build `iosMain`).
* Replace default Material 3 components with custom UI rendering via Canvas.
