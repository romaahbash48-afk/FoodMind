# FoodMind

A modern Android application demonstrating Clean Architecture principles with a multi-module structure using Kotlin, Jetpack Compose, and current best practices.

## 🏗️ Architecture Overview

FoodMind follows **Clean Architecture** principles with a clear separation of concerns across multiple modules. The architecture is designed to be:

- **Testable**: Pure business logic separated from framework dependencies
- **Maintainable**: Clear boundaries and responsibilities between modules
- **Scalable**: Easy to add new features as independent modules
- **Modern**: Uses latest Android/Kotlin technologies and patterns

## 📦 Module Structure

```
FoodMind/
├── app/                    # Android Application Module
├── core/                   # Shared Utilities Module
├── domain/                 # Business Logic Module
├── data/                   # Data Layer Module
└── feature-home/           # Home Feature Module
```

### :app - Application Module
**Type:** Android Application  
**Dependencies:** :core, :domain, :data, :feature-home

The main application module that ties everything together.

**Contents:**
- `FoodMindApplication.kt` - Hilt-enabled Application class
- `MainActivity.kt` - Single Activity with Jetpack Compose and Navigation
- `di/` - Dependency injection modules (DataModule, DispatcherModule)
- `ui/theme/` - Material 3 theme configuration

**Key Technologies:**
- Jetpack Compose for UI
- Navigation Compose for routing
- Hilt for dependency injection
- Material 3 design system

### :core - Core Utilities Module
**Type:** Android Library  
**Dependencies:** None

Shared utilities and common code used across all modules.

**Contents:**
- `util/Result.kt` - Generic result wrapper for success/error states
- `util/Constants.kt` - Application-wide constants
- `util/Extensions.kt` - Kotlin extension functions
- `di/Qualifiers.kt` - DI qualifiers for coroutine dispatchers

**Purpose:** Non-domain, non-data shared code that doesn't belong to any specific layer.

### :domain - Domain Layer Module
**Type:** Pure Kotlin Module (No Android Dependencies)  
**Dependencies:** None

The heart of Clean Architecture - contains business logic, domain models, and use cases.

**Contents:**
- `model/Food.kt` - Domain model representing food items
- `repository/FoodRepository.kt` - Repository interface contract
- `usecase/GetAllFoodsUseCase.kt` - Use case for retrieving foods

**Key Principle:** This module is **pure Kotlin** with zero Android dependencies, making it highly testable and reusable.

### :data - Data Layer Module
**Type:** Pure Kotlin Module  
**Dependencies:** :domain

Implements data access and repository patterns.

**Contents:**
- `repository/FoodRepositoryImpl.kt` - Implementation of FoodRepository
- `source/local/FoodLocalDataSource.kt` - Local data source interface
- `source/local/InMemoryFoodDataSource.kt` - In-memory implementation with sample data
- `source/remote/FoodRemoteDataSource.kt` - Remote data source interface (placeholder)

**Features:**
- In-memory data source with 5 sample food items
- Repository pattern implementation
- Ready to extend with Room database or Retrofit API calls
- Includes unit tests demonstrating repository functionality

### :feature-home - Home Feature Module
**Type:** Android Library  
**Dependencies:** :core, :domain

A feature module representing the home screen.

**Contents:**
- `HomeViewModel.kt` - ViewModel with StateFlow-based state management
- `HomeScreen.kt` - Compose UI with food list, cards, and error handling

**Features:**
- Displays list of foods with nutritional information
- Loading and error states
- Material 3 cards and components
- Fully integrated with Hilt DI

## 🛠️ Technology Stack

### Build & Configuration
- **Gradle Kotlin DSL** - Modern build configuration
- **Version Catalog** (`libs.versions.toml`) - Centralized dependency management
- **Multi-module setup** - Clear separation of concerns

### Core Libraries
- **Kotlin 1.9.20** - Modern, concise language
- **Coroutines & Flow** - Asynchronous programming
- **Jetpack Compose** - Declarative UI framework
- **Navigation Compose** - Type-safe navigation
- **Hilt** - Dependency injection
- **Material 3** - Modern design system

### Architecture Components
- **ViewModel** - UI state management
- **StateFlow** - Reactive state holder
- **Repository Pattern** - Data abstraction
- **Use Cases** - Business logic encapsulation

### Testing
- **JUnit 4** - Unit testing framework
- **Coroutines Test** - Testing coroutines and flows

## 🚀 Getting Started

### Prerequisites
- **Android Studio** Hedgehog (2023.1.1) or later
- **JDK 17** or higher
- **Android SDK** with API 34 (Android 14)
- **Minimum SDK:** API 24 (Android 7.0)

### Opening the Project

1. **Clone the repository:**
   ```bash
   git clone https://github.com/romaahbash48-afk/FoodMind.git
   cd FoodMind
   ```

2. **Open in Android Studio:**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned FoodMind directory
   - Wait for Gradle sync to complete

3. **Build the project:**
   ```bash
   ./gradlew build
   ```

4. **Run tests:**
   ```bash
   # Run all tests
   ./gradlew test
   
   # Run specific module tests
   ./gradlew :data:test
   ```

5. **Install on device/emulator:**
   ```bash
   ./gradlew installDebug
   ```
   Or use the Run button in Android Studio.

## 📱 What's Implemented

### ✅ Working Features
- **Multi-module architecture** with proper dependency management
- **Sample data layer** with in-memory food database
- **Home screen** displaying 5 sample food items with:
  - Food name, category, and description
  - Nutritional information (calories, protein, carbs, fat)
  - Material 3 cards and styling
- **Loading and error states** in the UI
- **Dependency injection** with Hilt
- **Unit test** for repository functionality
- **Navigation** setup (ready to add more screens)
- **Theme configuration** with Material 3

### 🔜 Ready to Extend
- **Room Database:** Replace `InMemoryFoodDataSource` with Room implementation
- **Retrofit API:** Implement `FoodRemoteDataSource` for real network calls
- **More Features:** Add new feature modules (e.g., `:feature-details`, `:feature-search`)
- **Authentication:** Add user authentication module
- **Caching Strategy:** Implement proper cache-first/network-first patterns

## 🧪 Testing

The project includes a sample unit test in `:data` module:

```kotlin
// data/src/test/kotlin/io/foodmind/data/repository/FoodRepositoryImplTest.kt
class FoodRepositoryImplTest {
    @Test
    fun `getAllFoods returns list of foods`() = runTest {
        val foods = repository.getAllFoods().first()
        assertEquals(5, foods.size)
    }
}
```

**Run tests:**
```bash
./gradlew test
```

## 📋 Project Structure Best Practices

### Dependency Rules
```
:app -> :feature-*, :data, :domain, :core
:feature-* -> :domain, :core
:data -> :domain
:domain -> (no dependencies)
:core -> (no dependencies)
```

### Package Naming Convention
All code uses the `io.foodmind` base package:
- `io.foodmind` - App module
- `io.foodmind.core.*` - Core utilities
- `io.foodmind.domain.*` - Domain layer
- `io.foodmind.data.*` - Data layer
- `io.foodmind.feature.home.*` - Home feature

### Code Style
- **Comments:** Well-documented code with KDoc for public APIs
- **Naming:** Clear, descriptive names following Kotlin conventions
- **Immutability:** Prefer `val` over `var`, data classes for models
- **Null Safety:** Leverage Kotlin's null safety features

## 🔒 Security & Best Practices

- ✅ No hardcoded secrets or API keys
- ✅ All network/DB operations are placeholders
- ✅ .gitignore configured for Android projects
- ✅ ProGuard-ready configuration
- ✅ Proper error handling and state management

## 📚 Further Reading

To learn more about the technologies used:

- [Android Clean Architecture Guide](https://developer.android.com/topic/architecture)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Hilt Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)

## 🤝 Contributing

This is a base architecture skeleton. To extend:

1. Add new feature modules following the `:feature-*` pattern
2. Define domain models and use cases in `:domain`
3. Implement data sources in `:data`
4. Wire everything together in `:app`

## 📄 License

This project is a demonstration of Android architecture and is provided as-is for educational purposes.

---

**Built with ❤️ using Clean Architecture and Modern Android Development**
