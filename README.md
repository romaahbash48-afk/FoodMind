# FoodMind

A modern Android app for food tracking and nutrition management, built with clean architecture principles.

## 🏗️ Architecture

FoodMind follows **Clean Architecture** with **MVVM** pattern, ensuring:
- Separation of concerns
- Testability
- Scalability
- Maintainability

For detailed architecture documentation, see [ARCHITECTURE.md](ARCHITECTURE.md).

## 🛠️ Tech Stack

### Core Technologies
- **Language**: Kotlin 2.0.21
- **UI Framework**: Jetpack Compose
- **Design System**: Material 3
- **Build Tool**: Gradle with Kotlin DSL

### Architecture Components
- **Dependency Injection**: Hilt 2.51
- **Navigation**: Navigation Compose 2.8.0
- **Async Operations**: Kotlin Coroutines 1.8.1
- **State Management**: StateFlow & SharedFlow
- **ViewModels**: Lifecycle ViewModel 2.8.7

### Minimum Requirements
- **Min SDK**: 26 (Android 8.0 Oreo)
- **Target SDK**: 36
- **Compile SDK**: 36

## 📦 Project Structure

```
app/src/main/java/com/example/foodmind/
├── data/               # Data layer
│   ├── mapper/         # DTO ↔ Domain model mappers
│   ├── model/          # Data Transfer Objects
│   ├── repository/     # Repository implementations
│   └── source/         # Data sources
│       ├── local/      # Local data (Room, DataStore)
│       └── remote/     # Remote data (Retrofit)
├── domain/             # Domain layer (business logic)
│   ├── model/          # Domain models
│   ├── repository/     # Repository interfaces
│   └── usecase/        # Use cases
├── presentation/       # Presentation layer (UI)
│   ├── components/     # Reusable UI components
│   ├── screens/        # Feature screens
│   ├── theme/          # Material 3 theme
│   └── viewmodel/      # Base ViewModels
├── di/                 # Dependency injection modules
├── navigation/         # Navigation setup
├── util/               # Utilities
├── FoodMindApplication.kt
└── MainActivity.kt
```

## ✨ Features

### Current Implementation
- ✅ Clean Architecture foundation
- ✅ Hilt dependency injection setup
- ✅ Navigation with Compose
- ✅ MVVM pattern with StateFlow
- ✅ Example screens and ViewModels
- ✅ Material 3 theming
- ✅ Dark mode support

### Planned Features
- [ ] Food item tracking
- [ ] Calorie counting
- [ ] Nutritional information
- [ ] Meal planning
- [ ] Recipe management
- [ ] Barcode scanning
- [ ] Cloud sync
- [ ] Analytics and insights

## 🚀 Getting Started

### Prerequisites
- Android Studio Jellyfish or later
- JDK 11 or later
- Android SDK 36

### Building the Project

1. **Clone the repository**
   ```bash
   git clone https://github.com/romaahbash48-afk/FoodMind.git
   cd FoodMind
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory

3. **Build the project**
   ```bash
   ./gradlew assembleDebug
   ```

4. **Run on device/emulator**
   - Connect your device or start an emulator
   - Click "Run" in Android Studio

## 📚 Documentation

- [Architecture Guide](ARCHITECTURE.md) - Detailed architecture documentation
- [Contributing Guidelines](CONTRIBUTING.md) - How to contribute (coming soon)

## 🧪 Testing

### Running Tests
```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

## 🔧 Configuration

### Version Catalog
Dependencies are managed using Gradle Version Catalogs in `gradle/libs.versions.toml`.

### Build Configuration
- `build.gradle.kts` - Root build configuration
- `app/build.gradle.kts` - App module configuration

## 📱 Screenshots

Coming soon...

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👤 Author

**romaahbash48-afk**
- GitHub: [@romaahbash48-afk](https://github.com/romaahbash48-afk)

## 🙏 Acknowledgments

- Android Jetpack team for excellent libraries
- Material Design team for comprehensive design system
- Kotlin team for the amazing language
