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
git clone https://github.com/hninor/Pokemon.git
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
- **State Management**: Uses Compose state management with StateFlows
- **Theme**: Custom Material 3 theme
- **Component-Based**: Modular UI components for better reusability
- **Datasource**: Remote and Local, prioritazing cache over remote as data strategy
- **Declarative paradigm**: Use of flow to give the reactive programation environment, it is used in both UI layer and data layers


## 📫 Contact

Henry Niño -  [LinkedIn](https://www.linkedin.com/in/hninor)
