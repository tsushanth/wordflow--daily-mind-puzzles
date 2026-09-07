# WordFlow: Daily Mind Puzzles

A calming, daily word puzzle app for Android. Solve a fresh scrambled word every day and unwind with themed word search boards — no timers pressuring you, no ads interrupting you.

> Relax. Solve. Flow.

## Features

- **Daily Scramble** — one unscrambling puzzle per day, shared by all players, with a hint system and attempt/time tracking.
- **Word Search** — themed puzzle categories (animals, nature, food, space, and more), solved by dragging across letters.
- **Streaks & stats** — current/longest streak, total puzzles solved, and a browsable history of past results.
- **Premium** — unlocks every Word Search category, full puzzle history, unlimited hints, and additional themes via Google Play Billing (subscriptions, lifetime purchase, and one-off hint packs).
- **Material 3 design** — dynamic (Material You) theming on Android 12+ with a hand-crafted fallback palette, full dark mode support, and haptic feedback throughout.
- **Accessible** — every interactive element exposes a screen-reader-friendly content description.

## Requirements

- Android 7.0 (API 24) or higher
- Android Studio Hedgehog or newer (for building from source)
- JDK 17
- Gradle 8.2 (bundled via the Gradle wrapper)

## Build Instructions

1. Clone the repository and open it in Android Studio, or build from the command line.
2. From the project root, build a debug APK:
   ```bash
   ./gradlew assembleDebug
   ```
3. Install it on a connected device/emulator:
   ```bash
   ./gradlew installDebug
   ```
4. To run unit/instrumentation tests:
   ```bash
   ./gradlew test
   ./gradlew connectedAndroidTest
   ```

Google Play Billing requires a valid app signature and Play Console configuration to test real purchases; without it, the app still runs with billing gracefully falling back to a disconnected/offline state.

## Project Structure

```
app/src/main/java/com/factory/wordflowdailymindpuzzles/
├── data/                  # Room database, DAOs, repository, and word bank
│   └── billing/           # Google Play Billing integration, entitlement & purchase state
├── domain/                 # Word search puzzle generation engine
├── ui/
│   ├── home/                # Home screen (streaks, entry points)
│   ├── scramble/             # Daily Scramble screen + view model
│   ├── wordsearch/           # Word Search screen + view model
│   ├── stats/                 # Progress/history screen
│   ├── settings/               # Settings screen (restore purchases, legal links)
│   ├── premium/                 # Paywall, premium badges, and gate cards
│   ├── onboarding/                # First-run welcome flow
│   ├── navigation/                 # NavHost, bottom nav, and destinations
│   └── theme/                       # Material 3 color scheme, typography
├── util/                  # Date utilities, streak calculation, onboarding prefs
├── MainActivity.kt
└── WordFlowApplication.kt   # App-level DI (database, repository, billing/premium managers)
```

- **Architecture**: single-Activity Jetpack Compose app with a `NavHost`-driven screen graph. Each screen owns a `ViewModel` exposing a single `StateFlow<UiState>`.
- **Persistence**: Room (`AppDatabase`) stores daily scramble and word search results locally.
- **Monetization**: `PremiumManager` caches entitlement state locally for instant/offline UI; `BillingManager` is the source of truth via Google Play Billing.
