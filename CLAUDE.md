# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

RickAndMortyRoster is a single-activity Android app that fetches the character roster from the public [Rick and Morty API](https://rickandmortyapi.com/) and renders it with Jetpack Compose. It follows MVVM with unidirectional data flow, a singleton repository cache, and Hilt for dependency injection. No API key is required.

## Build & Run

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew installDebug           # Install on a connected device/emulator
./gradlew test                   # JVM unit tests (JUnit + MockK)
./gradlew connectedAndroidTest   # Instrumented + Compose UI tests
./gradlew lint                   # Android Lint
```

Requirements: Android Studio (latest stable) with Android SDK 37, JDK 11+. Or open the project and run the `app` configuration.

## Build Configuration

- `minSdk` 24, `targetSdk` / `compileSdk` 37, JVM target 11
- AGP 9.x, Kotlin 2.3.x, KSP (not kapt) for annotation processing
- Gradle Kotlin DSL with a version catalog at `gradle/libs.versions.toml` — add/update dependency versions there, not inline in `app/build.gradle.kts`.

## Architecture

Data flows in one direction: `RickAndMortyApi` (Retrofit) → `RickAndMortyRepository` (singleton cache) → `ViewModel` (exposes `StateFlow<*State>`) → Compose screen (`collectAsStateWithLifecycle`).

- **Repository (`data/RickAndMortyRepository.kt`)** — `@Singleton`, the single source of truth. `loadCharacters()` fetches the list once and caches it in a `MutableStateFlow<List<RMCharacter>>`. The detail screen does **not** make a network call; `getCharacter(id)` reads from this in-memory cache. `CharacterListViewModel` checks `isCharacterListEmpty()` before fetching to avoid redundant loads.
- **API (`api/`)** — `RickAndMortyApi` defines `GET api/character`. `CharacterListResponse` holds the DTOs and the `toRMCharacterList()` mapper that converts them to the `RMCharacter` domain model.
- **Model (`model/RMCharacter.kt`)** — the domain model used throughout the UI layer.
- **ViewModels** — `@HiltViewModel`, each owns a private `MutableStateFlow` of its screen's immutable state data class (`CharacterListState` / `CharacterDetailState` with `isLoading` / `isError` flags). Network work runs in `viewModelScope.launch(Dispatchers.IO)` wrapped in `runCatching { }.onSuccess { }.onFailure { }`.
- **Screens (`ui/screens/`)** — each Compose screen obtains its ViewModel via `hiltViewModel()`. The detail screen triggers its load with `LaunchedEffect(characterId)`.
- **Navigation (`navigation/`)** — type-safe routes via `NavigationItem` (a `@Serializable` sealed interface: `Characters`, `CharacterDetail(characterId: Int)`). `MainScreen` hosts the `Scaffold` + `TopAppBar` + `NavHost`; the list row's click navigates to `CharacterDetail` passing the character id, retrieved on the destination via `backStackEntry.toRoute()`.
- **DI (`di/`)** — `NetworkModule` provides `Retrofit` (kotlinx.serialization converter) and the `RickAndMortyApi`; `RepositoryModule` provides the singleton repository. Both `@InstallIn(SingletonComponent::class)`.
- **Entry points** — `RMApp` (`@HiltAndroidApp`) and `MainActivity` (`@AndroidEntryPoint`, edge-to-edge, sets `RickMortyRosterTheme { MainScreen(...) }`).

## Package Structure

```
app/src/main/java/com/huhx0015/rickandmortyroster/
├── RMApp.kt                      # @HiltAndroidApp Application
├── api/
│   ├── RickAndMortyApi.kt        # Retrofit service interface
│   └── CharacterListResponse.kt  # DTOs + toRMCharacterList() mapper
├── data/
│   └── RickAndMortyRepository.kt # @Singleton in-memory character cache
├── model/
│   └── RMCharacter.kt            # Domain model
├── di/
│   ├── NetworkModule.kt          # Provides Retrofit + RickAndMortyApi
│   └── RepositoryModule.kt       # Provides RickAndMortyRepository
├── navigation/
│   ├── NavigationItem.kt         # @Serializable sealed routes
│   └── Screen.kt                 # Screen enum
└── ui/
    ├── activities/MainActivity.kt
    ├── screens/
    │   ├── main/MainScreen.kt              # Scaffold + TopAppBar + NavHost
    │   ├── list/                           # CharacterListScreen/ViewModel/State
    │   └── detail/                         # CharacterDetailScreen/ViewModel/State
    └── theme/                              # Color, Theme, Type
```

## Conventions

- New domain types go in `model/`, network DTOs + mappers in `api/`, screens under `ui/screens/<feature>/` as a `Screen` + `ViewModel` + immutable `State` triad.
- Network access goes through the repository, never directly from a ViewModel or screen.
- Use `StateFlow` + `collectAsStateWithLifecycle` for UI state; keep state classes immutable and update via `_state.update { it.copy(...) }`.
- Images load with Coil 3 (`AsyncImage` + `ImageRequest`).
- Note: the dependency catalog includes Moshi alongside kotlinx.serialization, but the active Retrofit converter is kotlinx.serialization (see `NetworkModule`).
