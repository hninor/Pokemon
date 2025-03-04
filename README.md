# Pokemon API 🔐

Aplicación moderna en Jetpack Compose, donde se cargan de a 20 pokemons para ser mostrados en lista, se puede acceder al respectivo detalle haciendo clic sobre cada uno de ellos, la paginación se realiza a medida que se hace el scroll y los endpoints que se consumen son proporcionados por la PokeAPI (https://pokeapi.co/api/v2/pokemon/22/)

### Pantalla principal
![listado_pokemons](https://github.com/user-attachments/assets/f8c17ed1-2b39-478f-8447-0b2079475a2b)

### Detalle pokemon
![detalle](https://github.com/user-attachments/assets/77e47b69-d59e-4015-a900-b314341269c3)

### Soporte modo día/noche
![modo dark](https://github.com/user-attachments/assets/5fc54bf9-c86c-4b18-b171-d2f53aacb1af)

### Paginación
![page loading](https://github.com/user-attachments/assets/be12e1bd-a870-4dcb-baaa-a31d04290e4a)

### Manejo de errores
![error cargando primeros pokemon](https://github.com/user-attachments/assets/294980a3-7d40-4a3d-8e0d-4f3e8b4b07ee)



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

3. Funcionalidades Adicionales
   Guardar últimos Pokémon vistos en caché local (Room/SQLite).
   Manejo offline (mostrar datos cacheados si no hay conexión).
   Soporte para modo día/noche

4. Inicio de sesión con Google

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
    - Flow(Hot and Cold)
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
    │   │           └── presentation/         # UI logic, Screens, Viewmodels
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
- **Repository**: Use of interface to decouple data layer from domain layer, Repository Design Pattern
- **Datasource**: Remote and Local, prioritazing cache over remote as data strategy
- **Declarative paradigm**: Use of flow to give the reactive programation environment, it is used in both UI layer and data layers


## 📫 Contact

Henry Niño -  [LinkedIn](https://www.linkedin.com/in/hninor)
