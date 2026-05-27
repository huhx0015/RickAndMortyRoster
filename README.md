# RickAndMortyRoster

## Description

RickAndMortyRoster: A simple Android app utilizing the [Rick and Morty API](https://rickandmortyapi.com/) for displaying roster details from the Rick and Morty show.

The app fetches the character roster from the public Rick and Morty REST API and renders it with Jetpack Compose. The codebase is intentionally lightweight and follows modern Android best practices, making it a useful reference for setting up a Compose + Hilt + Retrofit project.

### Architecture

The app follows an MVVM (Model-View-ViewModel) architecture with unidirectional data flow:

- **View** — Jetpack Compose screens (`CharacterListScreen`, `CharacterDetailScreen`) that observe state via `collectAsStateWithLifecycle`.
- **ViewModel** — `CharacterListViewModel` exposes an immutable `StateFlow<CharacterListState>` and triggers data loads inside `viewModelScope`.
- **Data layer** — Retrofit-based `RickAndMortyApi` service backed by domain models (`RMCharacter`) and DTO mappers in the `api` package.
- **DI** — Hilt provides the `Retrofit` instance and `RickAndMortyApi` through `NetworkModule` (`@InstallIn(SingletonComponent::class)`).
- **Navigation** — A single-activity setup (`MainActivity`) with `NavHost` driving navigation between the list and detail destinations.

### Project Structure

```
app/src/main/java/com/huhx0015/rickandmortyroster/
├── MainActivity.kt              # Single-activity host + NavHost wiring
├── RMApp.kt                     # Hilt @HiltAndroidApp Application class
├── api/                         # Retrofit service + network response models
│   ├── RickAndMortyApi.kt
│   └── CharacterListResponse.kt
├── data/                        # Domain models
│   └── RMCharacter.kt
├── di/                          # Hilt modules
│   └── NetworkModule.kt
└── ui/
    ├── screens/
    │   ├── list/                # Character list screen + ViewModel + state
    │   └── detail/              # Character detail screen
    └── theme/                   # Compose Material3 theme, colors, typography
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
Copyright 2025 Michael Huh

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
