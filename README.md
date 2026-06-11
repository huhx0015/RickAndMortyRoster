# RickAndMortyRoster

## Description

RickAndMortyRoster: A simple Android app utilizing the [Rick and Morty API](https://rickandmortyapi.com/) for displaying roster details from the Rick and Morty show.

The app fetches the character roster from the public Rick and Morty REST API and renders it with Jetpack Compose. Selecting a character opens a detail screen with a larger portrait and additional information. The codebase is intentionally lightweight and follows modern Android best practices, making it a useful reference for setting up a Compose + Hilt + Retrofit project.

### Architecture

The app follows an MVVM (Model-View-ViewModel) architecture with unidirectional data flow. Data flows in one direction: `RickAndMortyApi` → `RickAndMortyRepository` → `ViewModel` → Compose screen.

- **View** — Jetpack Compose screens (`CharacterListScreen`, `CharacterDetailScreen`) that observe state via `collectAsStateWithLifecycle`. A top-level `MainScreen` hosts the `Scaffold`, `TopAppBar`, and `NavHost`.
- **ViewModel** — `CharacterListViewModel` and `CharacterDetailViewModel` (both `@HiltViewModel`) each expose an immutable `StateFlow` of their screen's state (`CharacterListState` / `CharacterDetailState`, with `isLoading` / `isError` flags) and run loads inside `viewModelScope`.
- **Repository** — `RickAndMortyRepository` is a `@Singleton` that acts as the single source of truth. It fetches the character list once and caches it in a `StateFlow`. The detail screen reads from this in-memory cache (`getCharacter(id)`) rather than making a second network call.
- **Data layer** — Retrofit-based `RickAndMortyApi` service. DTOs and the `toRMCharacterList()` mapper live in the `api` package; the `RMCharacter` domain model lives in `model`.
- **DI** — Hilt provides the `Retrofit` instance and `RickAndMortyApi` via `NetworkModule`, and the repository via `RepositoryModule` (both `@InstallIn(SingletonComponent::class)`).
- **Navigation** — A single-activity setup (`MainActivity` under `ui/activities/`) with a dedicated `navigation/` package. Type-safe routes are defined by the `@Serializable` sealed interface `NavigationItem` (`Characters`, `CharacterDetail(characterId)`) and consumed by the `NavHost` inside `MainScreen`; the character id is passed to the detail destination and read via `backStackEntry.toRoute()`.

### Project Structure

```
app/src/main/java/com/huhx0015/rickandmortyroster/
├── RMApp.kt                                  # @HiltAndroidApp Application class
├── api/
│   ├── RickAndMortyApi.kt                    # Retrofit service interface
│   └── CharacterListResponse.kt              # DTOs + toRMCharacterList() mapper
├── data/
│   └── RickAndMortyRepository.kt             # @Singleton in-memory character cache
├── model/
│   └── RMCharacter.kt                        # Domain model
├── di/
│   ├── NetworkModule.kt                      # Hilt module providing Retrofit + API
│   └── RepositoryModule.kt                   # Hilt module providing the repository
├── navigation/
│   ├── Screen.kt                             # Screen enum
│   └── NavigationItem.kt                     # @Serializable sealed routes (Characters, CharacterDetail)
└── ui/
    ├── activities/
    │   └── MainActivity.kt                   # @AndroidEntryPoint single-activity host
    ├── screens/
    │   ├── main/
    │   │   └── MainScreen.kt                 # Scaffold + TopAppBar + NavHost composable
    │   ├── list/
    │   │   ├── CharacterListScreen.kt        # @Composable list UI
    │   │   ├── CharacterListViewModel.kt     # @HiltViewModel exposing StateFlow
    │   │   └── CharacterListState.kt         # UI state data class
    │   └── detail/
    │       ├── CharacterDetailScreen.kt      # @Composable detail UI
    │       ├── CharacterDetailViewModel.kt   # @HiltViewModel exposing StateFlow
    │       └── CharacterDetailState.kt       # UI state data class
    └── theme/
        ├── Color.kt                          # Material3 color palette
        ├── Theme.kt                          # RickMortyRosterTheme wrapper
        └── Type.kt                           # Typography definitions
```

### Tech Stack & Dependencies

| Area | Libraries |
| --- | --- |
| Language | Kotlin 2.3.x, Kotlin Coroutines |
| UI | Jetpack Compose (BOM), Material 3, Navigation Compose, Activity Compose |
| Architecture | AndroidX Lifecycle (ViewModel, LiveData, lifecycle-runtime-ktx) |
| Dependency Injection | Hilt (`dagger-hilt-android`), Hilt Navigation Compose, KSP |
| Networking | Retrofit, OkHttp (+ Logging Interceptor) |
| Serialization | Kotlinx Serialization (JSON), Moshi + Moshi Kotlin |
| Image Loading | Coil 3 (`coil-compose`, `coil-network-okhttp`) |
| Testing | JUnit 4, AndroidX Test (Espresso, JUnit ext), Compose UI Test, MockK, AndroidX Arch Core Testing |

### Build Configuration

- **`minSdk`**: 24
- **`targetSdk` / `compileSdk`**: 37
- **JVM target**: 11
- **Build system**: Gradle with Kotlin DSL and a version catalog (`gradle/libs.versions.toml`)

### API

This project consumes the public Rick and Morty REST API:

- Base URL: `https://rickandmortyapi.com/`
- Endpoint used: `GET api/character`

No API key is required.

### Building & Running

Requirements:

- Android Studio (latest stable) with the Android SDK 37 installed
- JDK 11+

Build and install on a connected device or emulator:

```bash
./gradlew assembleDebug
./gradlew installDebug
```

Or simply open the project in Android Studio and run the `app` configuration.

### Testing

```bash
./gradlew test                # JVM unit tests (JUnit + MockK)
./gradlew connectedAndroidTest # Instrumented + Compose UI tests
```

## License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

See the full license text in [LICENSE](LICENSE).
