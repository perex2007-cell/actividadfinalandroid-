# Implementation Walkthrough - Task Manager Application

I have completely implemented the Task Manager application following the requested clean architecture layers, MVVM design pattern, and custom persist-then-delete sync flow.

## Changes Made

### 1. Build and Dependency Configuration
- Updated `libs.versions.toml`, root `build.gradle.kts`, and `app/build.gradle.kts` to safely add Hilt (`2.60.1`), Firebase BoM (`34.19.0`), Room (`2.6.1`), and Navigation Compose.
- Synchronized the build system to ensure built-in Kotlin annotation support is active.

### 2. Domain Layer
- Created pure domain data models `Task` and `TaskDraft`.
- Created interfaces `AuthRepository`, `TaskRepository`, and `DraftRepository`.
- Built full list of single-responsibility Use Cases for authentication, remote task operations, and local draft management.

### 3. Data Layer
- Configured local Room persistence with `TaskDraftEntity`, `TaskDraftDao`, and `AppDatabase`.
- Created `TaskMapper` to abstract data transformations.
- Implemented repositories using Firebase Auth, Firestore Database, and Room.

### 4. UI & Navigation Layer
- Created MVI-inspired UI state containers and ViewModels for all user journeys.
- Built interactive screens using Jetpack Compose and Material 3 (`LoginScreen`, `RegisterScreen`, `TaskListScreen`, `TaskFormScreen`, `DraftListScreen`).
- Wired everything inside `AppNavigation` to ensure route protection for authenticated users.

## Firebase Integration Guide

To complete the setup and enable cloud operations, follow these precise steps:

### Paso 1: Crear el proyecto en Firebase Console
1. Entra a [Firebase Console](https://console.firebase.google.com).
2. Haz clic en **Agregar proyecto** y ponle un nombre (por ejemplo, `TaskManagerApp`).
3. Puedes activar o desactivar Google Analytics y presionar **Crear proyecto**.

### Paso 2: Registrar la App Android
1. Dentro del panel de tu proyecto de Firebase, haz clic en el ícono de **Android** para agregar la app.
2. Introduce el **Nombre del paquete** exacto de tu aplicación: `com.example.actividadfinalandroid`.
3. Haz clic en **Registrar app**.

### Paso 3: Descargar y Agregar `google-services.json`
1. Descarga el archivo `google-services.json` proporcionado por el asistente de Firebase.
2. Copia y pega el archivo en el directorio de tu módulo `app`:
   `C:/actividadandroidfinal/app/google-services.json`

### Paso 4: Habilitar los Servicios en Firebase Console
1. **Authentication**: Ve a *Authentication* -> *Sign-in method* -> Activa el proveedor de **Correo electrónico/Contraseña**.
2. **Cloud Firestore**: Ve a *Firestore Database* -> Haz clic en *Crear base de datos* -> Inicia en **Modo de prueba** (para desarrollo) y selecciona la ubicación del servidor.

Una vez que coloques el archivo `google-services.json` en la carpeta `app/`, el plugin se activará automáticamente y la aplicación compilará por completo con soporte completo para sincronización en la nube y persistencia en Room.
