# Internal Sales & Operations Android App (Scaffold)

This repository contains a production-ready Android scaffold generated from the provided SRS for an internal sales and operations app.

## Implemented architecture

- **Kotlin + Jetpack Compose + Material 3**
- **Role-based navigation and dashboard cards** (Admin / Manager / Associate)
- **Authentication flow** (mobile + password/OTP simulation)
- **Lead management**: create lead, list leads, filter/search, status updates
- **Sidebar drawer menu** with role-aware options
- **Module scaffolds** for projects, requests, notifications, and all listed business modules
- **Security baseline**:
  - HTTPS-only network policy (`network_security_config`)
  - token-ready app state structure

## Package structure

- `model/`: entity and enum definitions
- `data/`: repository layer (`FakeBackendRepository`)
- `viewmodel/`: centralized UI state + business actions
- `ui/navigation/`: destination and drawer menu definitions
- `ui/screens/`: Compose screens (login, dashboard, lead workflows)

## Build prerequisites

- Android Studio Iguana+ (or equivalent)
- Android SDK 35
- JDK 17

## Next integration steps

1. Replace `FakeBackendRepository` with Retrofit/Ktor API services.
2. Add JWT storage + refresh handling (EncryptedSharedPreferences/DataStore).
3. Add push notifications (Firebase Cloud Messaging).
4. Add request modules and approvals with backend workflows.
5. Add instrumentation and unit test coverage for FR acceptance.
