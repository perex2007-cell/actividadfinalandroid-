# Implementation Plan - Task Manager with Firebase and Room

This plan outlines the development of an Android application using MVVM architecture, Firebase (Authentication & Firestore), and Room (Local Drafts) based on the provided guide and workshop requirements.

## User Review Required

> [!IMPORTANT]
> **Firebase Configuration**: The `google-services.json` file is currently missing from the `app` module. While I will implement all the necessary code, the application will not be able to connect to Firebase until this file is added and the project is registered in the Firebase Console.

> [!WARNING]
> **Dependency Sync**: Significant changes will be made to the Gradle configuration. A full project sync will be required after applying these changes.

## Proposed Changes

### 1. Project Configuration (Gradle & Dependencies)

Update `libs.versions.toml` and build files to include:
- Firebase BoM, Auth, and Firestore.
- Room (Runtime, KSP).
- Hilt (Dependency Injection).
- Navigation Compose.
- Lifecycle & Coroutines extensions.

#### [MODIFY] [libs.versions.toml](file:///C:/actividadandroidfinal/gradle/libs.versions.toml)
#### [MODIFY] [build.gradle.kts (Root)](file:///C:/actividadandroidfinal/build.gradle.kts)
#### [MODIFY] [build.gradle.kts (App)](file:///C:/actividadandroidfinal/app/build.gradle.kts)

---

### 2. Domain Layer (Models & Repository Interfaces)

Define the core business logic and data structures.

#### [NEW] `Task.kt`, `TaskDraft.kt` (Models)
#### [NEW] `AuthRepository.kt`, `TaskRepository.kt`, `DraftRepository.kt` (Interfaces)
#### [NEW] Use Cases for Auth, Tasks, and Drafts.

---

### 3. Data Layer (Implementations & Persistence)

Implement the data sources and persistence logic.

#### [NEW] `TaskDraftEntity.kt`, `TaskDraftDao.kt`, `AppDatabase.kt` (Room)
#### [NEW] `AuthRepositoryImpl.kt`, `TaskRepositoryImpl.kt`, `DraftRepositoryImpl.kt`
#### [NEW] `TaskMapper.kt` (Data transformation)

---

### 4. Dependency Injection (Hilt)

Configure Hilt for providing instances of repositories, database, and Firebase services.

#### [NEW] `TaskManagerApplication.kt`
#### [NEW] `FirebaseModule.kt`, `DatabaseModule.kt`, `RepositoryModule.kt`

---

### 5. UI Layer (Compose & ViewModels)

Implement the user interface and state management.

#### [NEW] Navigation graph (`AppNavigation.kt`)
#### [NEW] Screens: `LoginScreen`, `RegisterScreen`, `TaskListScreen`, `TaskFormScreen`, `DraftListScreen`.
#### [NEW] ViewModels for each screen using `StateFlow`.

---

### 6. Main Entry Point

#### [MODIFY] [MainActivity.kt](file:///C:/actividadandroidfinal/app/src/main/java/com/example/actividadfinalandroid/MainActivity.kt)
Integrate Hilt and Navigation.

## Verification Plan

### Automated Tests
- I will perform a `gradle build` to ensure all new components and dependencies are correctly integrated.
- I will use `analyze_file` on key components to check for potential issues.

### Manual Verification
1. **Build Success**: Ensure the app compiles and runs.
2. **Firebase Check**: (Once `google-services.json` is provided) Verify login, registration, and Firestore CRUD.
3. **Room Check**: Verify that drafts are saved and retrieved correctly even without internet.
4. **Draft Publication**: Verify that a draft can be published to Firestore and is then removed from local storage.
