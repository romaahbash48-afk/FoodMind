# FoodMind Architecture Documentation

## Overview
FoodMind is built using **Clean Architecture** principles with **MVVM** pattern, utilizing modern Android development tools and libraries.

## Technology Stack

### Core
- **Language**: Kotlin 2.0.21
- **UI Framework**: Jetpack Compose
- **Design System**: Material 3
- **Minimum SDK**: 26 (Android 8.0)
- **Target SDK**: 36

### Architecture Components
- **Dependency Injection**: Hilt 2.51
- **Navigation**: Navigation Compose 2.8.0
- **Async Operations**: Kotlin Coroutines 1.8.1
- **State Management**: StateFlow & SharedFlow
- **ViewModels**: Lifecycle ViewModel 2.8.7

## Architecture Layers

### 1. Presentation Layer (`presentation/`)
Handles UI and user interactions using Jetpack Compose.

#### Structure:
- **screens/**: Contains composable screens organized by feature
  - Each screen has its own package with:
    - `Screen.kt`: Composable UI
    - `ViewModel.kt`: State management and business logic orchestration
    - `UiState.kt`: Data class representing screen state
    - `UiEvent.kt`: Sealed class for one-time UI events
    - `Action.kt`: Sealed class for user actions

- **components/**: Reusable UI components
- **theme/**: Material 3 theme configuration (colors, typography, shapes)
- **viewmodel/**: Base ViewModel classes

#### Key Patterns:
- **Unidirectional Data Flow (UDF)**: Data flows from ViewModel to UI
- **State Hoisting**: State is managed in ViewModels
- **Single Source of Truth**: ViewModel holds the UI state

Example:
```kotlin
@Composable
fun HomeScreen(
    onNavigate: (Screen) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            // Handle one-time events
        }
    }
    
    HomeScreenContent(
        state = state,
        onAction = viewModel::onAction
    )
}
```

### 2. Domain Layer (`domain/`)
Contains business logic and defines interfaces. Independent of Android framework.

#### Structure:
- **usecase/**: Business logic encapsulated in use cases
  - `BaseUseCase.kt`: Abstract base for use cases with parameters
  - `NoParamsUseCase.kt`: Base for parameterless use cases
  
- **model/**: Domain models (business entities)
- **repository/**: Repository interfaces (implemented in data layer)

#### Use Case Pattern:
```kotlin
class GetUserDataUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: UserRepository
) : NoParamsUseCase<UserData>(dispatcher) {
    
    override suspend fun execute(): UserData {
        return repository.getUserData()
    }
}
```

### 3. Data Layer (`data/`)
Handles data operations and implements repository interfaces.

#### Structure:
- **repository/**: Repository implementations
- **source/**: Data sources
  - **local/**: Local database, SharedPreferences, etc.
  - **remote/**: API clients, network calls
- **model/**: Data Transfer Objects (DTOs)

#### Repository Pattern:
```kotlin
class UserRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {
    
    override suspend fun getUserData(): UserData {
        // Fetch from local or remote
    }
}
```

### 4. Dependency Injection (`di/`)
Hilt modules for dependency injection.

#### Modules:
- **DispatcherModule**: Provides coroutine dispatchers
  - `@DefaultDispatcher`: Dispatchers.Default
  - `@IoDispatcher`: Dispatchers.IO
  - `@MainDispatcher`: Dispatchers.Main

Additional modules can be added for:
- Network (Retrofit, OkHttp)
- Database (Room)
- DataStore
- Repositories

### 5. Navigation (`navigation/`)
Navigation setup using Navigation Compose.

#### Components:
- **Screen.kt**: Sealed class defining all app destinations
- **FoodMindNavHost.kt**: NavHost configuration

Example:
```kotlin
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Profile : Screen("profile")
    data object Settings : Screen("settings/{userId}") {
        fun createRoute(userId: String) = "settings/$userId"
    }
}
```

### 6. Utilities (`util/`)
Common utilities and helper classes.

- **UiState.kt**: Sealed class for async operation states
  - `Idle`: Initial state
  - `Loading`: Operation in progress
  - `Success<T>`: Operation completed with data
  - `Error`: Operation failed with error message

## Project Structure

```
app/src/main/java/com/example/foodmind/
├── data/
│   ├── model/           # DTOs
│   ├── repository/      # Repository implementations
│   └── source/
│       ├── local/       # Local data sources
│       └── remote/      # Remote data sources
├── domain/
│   ├── model/           # Domain models
│   ├── repository/      # Repository interfaces
│   └── usecase/         # Use cases
├── presentation/
│   ├── components/      # Reusable UI components
│   ├── screens/         # Feature screens
│   │   └── home/
│   │       ├── HomeScreen.kt
│   │       └── HomeViewModel.kt
│   ├── theme/           # Material 3 theme
│   └── viewmodel/       # Base ViewModels
├── di/                  # Dependency injection modules
├── navigation/          # Navigation setup
├── util/                # Utilities
├── FoodMindApplication.kt
└── MainActivity.kt
```

## Key Principles

### 1. Separation of Concerns
Each layer has a distinct responsibility:
- **Presentation**: UI and user interaction
- **Domain**: Business logic
- **Data**: Data management

### 2. Dependency Rule
Dependencies point inward:
- Presentation depends on Domain
- Data depends on Domain
- Domain depends on nothing (pure Kotlin)

### 3. Testability
- ViewModels can be unit tested
- Use cases can be tested independently
- Repositories can be mocked

### 4. Scalability
- Easy to add new features
- Clear separation allows parallel development
- Modular structure supports feature modules

## State Management

### ViewModel State Pattern
```kotlin
@HiltViewModel
class FeatureViewModel @Inject constructor(
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel<FeatureUiState, FeatureUiEvent>(
    initialState = FeatureUiState(),
    dispatcher = dispatcher
) {
    
    fun onAction(action: FeatureAction) {
        when (action) {
            is FeatureAction.LoadData -> loadData()
        }
    }
    
    private fun loadData() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            // Load data
            updateState { it.copy(isLoading = false, data = result) }
        }
    }
}
```

## Error Handling

### Use Case Level
```kotlin
suspend operator fun invoke(params: P): Result<R> = withContext(dispatcher) {
    try {
        Result.success(execute(params))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
```

### ViewModel Level
```kotlin
viewModelScope.launch {
    useCase(params).fold(
        onSuccess = { data ->
            updateState { it.copy(data = data) }
        },
        onFailure = { error ->
            sendEvent(UiEvent.ShowError(error.message))
        }
    )
}
```

## Testing Strategy

### Unit Tests
- **ViewModels**: Test state changes and event emissions
- **Use Cases**: Test business logic
- **Repositories**: Test data operations

### UI Tests
- **Screens**: Test composables with test tags
- **Navigation**: Test navigation flows

## Adding New Features

### Steps to add a new feature:

1. **Define Domain Layer**
   - Create domain model in `domain/model/`
   - Create repository interface in `domain/repository/`
   - Create use case in `domain/usecase/`

2. **Implement Data Layer**
   - Create DTOs in `data/model/`
   - Implement repository in `data/repository/`
   - Create data sources if needed

3. **Build Presentation Layer**
   - Create feature package in `presentation/screens/`
   - Create ViewModel with state and events
   - Create Compose screen
   - Add navigation route

4. **Wire Up Dependencies**
   - Add Hilt module if needed
   - Inject dependencies

## Best Practices

1. **Keep ViewModels Dumb**: Business logic belongs in use cases
2. **Use Sealed Classes**: For navigation, actions, events, and states
3. **Prefer Composition**: Reuse composables through composition
4. **Handle Configuration Changes**: Use ViewModel to survive config changes
5. **Use Preview**: Add `@Preview` for all composables
6. **Document Public APIs**: Add KDoc comments
7. **Follow Naming Conventions**: 
   - Screens: `FeatureScreen.kt`
   - ViewModels: `FeatureViewModel.kt`
   - Use Cases: `VerbNounUseCase.kt` (e.g., `GetUserDataUseCase`)

## Resources

- [Android Architecture Guide](https://developer.android.com/topic/architecture)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Hilt Documentation](https://dagger.dev/hilt/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Material 3](https://m3.material.io/)

## Future Enhancements

Planned additions:
- [ ] Room database integration
- [ ] Retrofit for network calls
- [ ] DataStore for preferences
- [ ] Paging 3 for lists
- [ ] WorkManager for background tasks
- [ ] Testing infrastructure
- [ ] CI/CD pipeline
