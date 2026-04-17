# Internal Sales & Operations Android App

This repository contains a complete Android implementation generated from the provided SRS for internal sales and operations workflows.

## Implemented modules

- Authentication (mobile + password/OTP)
- Role-based dashboard and sidebar
- Associate profile
- Team management (Direct Team, Total Team, Add Associate)
- Lead management (create, assign, list, search, status update)
- Projects list with pricing and availability
- Material request (submit + status tracking)
- Vehicle request (submit + approval status tracking)
- Wallet (balance, withdrawal request, transaction history)
- Bonanza incentives and eligibility display
- Mela/Event updates
- Sales history (individual/team summary)
- Site incharge directory with contact details
- Creatives library with share/download URLs
- Notifications feed
- Change password
- Privacy policy

## Technology stack

- Kotlin
- Jetpack Compose + Material 3
- MVVM with state management in `AppViewModel`
- In-memory repository for live demo (`FakeBackendRepository`)

## Local run/debug readiness

- Gradle wrapper included (`gradlew`, `gradlew.bat`, `gradle/wrapper/*`) for Android Studio import consistency
- Works with JDK 17 in Android Studio (set **Gradle JDK = 17**)
- Script available to validate local setup: `bash scripts/check-android-env.sh`

## Security baseline

- HTTPS-only network policy configured via `network_security_config.xml`
- Role-based module visibility

## Run and debug

See [RUN_LIVE.md](RUN_LIVE.md) for full Android Studio + CLI run/debug steps and demo credentials.


## Binary-safe note

If your code host strips binary files, regenerate `gradle/wrapper/gradle-wrapper.jar` locally with `gradle wrapper --gradle-version 8.14.3` before running `./gradlew`.
