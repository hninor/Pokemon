# Pokemon API 🔐

Aplicación moderna en Jetpack Compose con paginación incluida para mostrar un listado de pokemons con su respectivo detalle, los endpoints son proporcionados por la PokeAPI
https://pokeapi.co/api/v2/pokemon/22/


## ✨ Features

1. Listado de Pokémon (Pantalla Principal)
   Muestra una lista/grid de Pokémon con: imagen (sprite), nombre y tipos.
   Scroll infinito o paginación (offset de 20 elementos por carga).
   Barra de búsqueda para filtrar por nombre o tipo.
   Pull-to-refresh para recargar datos.

2. Detalle de Pokémon
   Al hacer clic en un Pokémon se muestra:
   Imagen principal y sprites alternativos.
   Estadísticas (HP, ataque, defensa, velocidad).
   Habilidades.
   Tipo(s) con iconos/colores representativos.

. Funcionalidades Adicionales
Guardar últimos Pokémon vistos en caché local (Room/SQLite).
Manejo offline (mostrar datos cacheados si no hay conexión).
Soporte para modo día/noche

## 🛠 Tech Stack

- **Language**: Kotlin
- **Minimum SDK**: 24
- **Target SDK**: 34
- **Architecture Components**:
    - Jetpack Compose
    - Material Design 3
    - AndroidX Core KTX
    - Lifecycle Runtime KTX
    - Activity Compose
    - Room
    - Retrofit
    - Flow
    - MVVM
    - Clean Architecture
  

## 📁 Project Structure

```
app/
├── build.gradle.kts           # App level build configuration
├── src/
    ├── main/
    │   ├── java/
    │   │   └── com/hninor/pokedexmovil/
    │   │       ├── MainActivity.kt
    │   │       └── feature/
    │   │           ├── data/    # Datasources, Room and Retrofit
    │   │           ├── domain/         # Usecases, more independent layer
    │   │           └── presentation/         # UI logic
    │   └── res/                       # Resources
└── proguard-rules.pro        # ProGuard rules
```

## 🚀 Getting Started

### Prerequisites

- Android Studio Girrafe | 2022.3.1 or newer
- JDK 17 or newer
- Android SDK with minimum API level 24

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/PasswordRegexChecker.git
```

2. Open the project in Android Studio

3. Sync Gradle files

4. Run the app on an emulator or physical device

## 🏗️ Building the Project

The project uses Gradle with Kotlin DSL for build configuration. To build the project:

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

## 🏛️ Architecture

The app follows modern Android development best practices:

- **UI Layer**: Built entirely with Jetpack Compose
- **State Management**: Uses Compose state management with `remember` and `mutableStateOf`
- **Theme**: Custom Material 3 theme with Starbucks-inspired color scheme
- **Component-Based**: Modular UI components for better reusability

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details

## 👏 Acknowledgments

- Material Design 3 guidelines
- Jetpack Compose documentation
- Starbucks design system for inspiration

## 📫 Contact

Tamer Sarioglu - [X](https://x.com/tamerthedark)
                 [LinkedIn](https://www.linkedin.com/in/tamer-sarıoğlu-119742a4/)
