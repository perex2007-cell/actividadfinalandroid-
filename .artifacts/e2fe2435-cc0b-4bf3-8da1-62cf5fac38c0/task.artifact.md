# Checklist for Task Manager Implementation

- [x] Configure dependencies in Gradle (`libs.versions.toml`, root `build.gradle.kts`, app `build.gradle.kts`)
- [x] Create Hilt Application class and set up DI modules (`FirebaseModule`, `DatabaseModule`, `RepositoryModule`)
- [x] Define Domain layer models (`Task`, `TaskDraft`) and repository interfaces (`AuthRepository`, `TaskRepository`, `DraftRepository`)
- [x] Implement Use Cases for Auth, Task, and Draft operations
- [x] Implement Data layer Room local persistence (`TaskDraftEntity`, `TaskDraftDao`, `AppDatabase`)
- [x] Implement Data layer repository implementations (`AuthRepositoryImpl`, `TaskRepositoryImpl`, `DraftRepositoryImpl`) and `TaskMapper`
- [x] Create UI layer states and view models (`LoginViewModel`, `RegisterViewModel`, `TaskListViewModel`, `TaskFormViewModel`, `DraftsViewModel`)
- [x] Implement UI layer screens (`LoginScreen`, `RegisterScreen`, `TaskListScreen`, `TaskFormScreen`, `DraftsScreen`) and Components
- [x] Configure Navigation (`AppNavigation`, `Screen`)
- [x] Wire up `MainActivity` with Hilt and Navigation
- [x] Verify build and application structure
