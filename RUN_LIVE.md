# Run & Debug the Internal Sales & Ops Android App (Android Studio)

This app is fully runnable locally in Android Studio using the in-memory repository (`FakeBackendRepository`)—no backend is required for development/demo.

## 1) Required local setup

- Android Studio (latest stable)
- Android SDK Platform 35
- JDK 17
- Emulator (Android 8.0+) or real device with USB debugging

## 2) Verify environment from project root

```bash
bash scripts/check-android-env.sh
```

If Java is not 17, set it (example):

```bash
export JAVA_HOME=/root/.local/share/mise/installs/java/17.0.2
export PATH=$JAVA_HOME/bin:$PATH
```

## 3) Open project in Android Studio

1. Android Studio → **Open** → select this repository.
2. Wait for Gradle sync.
3. Set Gradle JDK to 17:
   - **File → Settings → Build, Execution, Deployment → Build Tools → Gradle**
   - **Gradle JDK = 17**
4. Ensure SDK is installed:
   - **Tools → SDK Manager** → Android 15 / API 35 (or the configured compile SDK).

## 4) Run the app

### Option A (from Android Studio)

- Select `app` run configuration.
- Select emulator/device.
- Click **Run ▶**.

### Option B (CLI)

```bash
./gradlew assembleDebug
./gradlew installDebug
```


### If `gradle/wrapper/gradle-wrapper.jar` is missing

Some code review systems do not allow binary files. If that jar is missing in your checkout, generate it once locally:

```bash
export JAVA_HOME=/path/to/jdk-17
export PATH=$JAVA_HOME/bin:$PATH
gradle wrapper --gradle-version 8.14.3
```

Then run:

```bash
./gradlew assembleDebug
```

## 5) Debug the app

1. Set breakpoints in:
   - `AppRoot.kt` (screen routing)
   - `AppViewModel.kt` (business actions/state updates)
   - `FakeBackendRepository.kt` (data operations)
2. Click **Debug 🐞** in Android Studio.
3. Trigger flows (login/create lead/requests) to hit breakpoints.

## 6) Demo credentials

Use any non-empty password/OTP with one of these mobile numbers:

- Admin: `9000000001`
- Manager: `9000000002`
- Associate: `9000000003`

## 7) Verify major modules after launch

- Sidebar navigation and role-based visibility
- Dashboard cards
- Create Lead / Get Agent Leads / status transitions
- Direct Team / Total Team / Add Associate
- Projects
- Material Request
- Vehicle Request
- Wallet + withdrawal request
- Bonanza / Mela updates
- Sales History
- Site Incharge
- Creatives
- Notifications
- Change Password / Privacy Policy

## 8) Common fixes

### `AAPT ... style/Theme.Material3.DayNight.NoActionBar not found`

Already fixed in this codebase by using `Theme.Material3.DayNight`.

### `What went wrong: 25.0.1`

Your Gradle daemon is using JDK 25. Switch Gradle JDK to 17 and re-sync.

### Android SDK not found

Set one variable:

```bash
export ANDROID_HOME=$HOME/Android/Sdk
# or
export ANDROID_SDK_ROOT=$HOME/Android/Sdk
```
