# Real Estate CRM - Full Stack

## Projects
- `app/` Native Android Kotlin app (MVVM, Compose, Retrofit, Room, Hilt, EncryptedSharedPreferences)
- `backend/` Node.js + Express + MongoDB API with JWT + RBAC
- `admin-web/` React admin panel

## Backend run
```bash
cd backend
npm install
npm run seed
npm run dev
```

## Admin panel run
```bash
cd admin-web
npm install
npm run dev
```

## Android run
1. Open repository in Android Studio.
2. Set Gradle JDK to 17.
3. Start backend on `http://10.0.2.2:5000` for emulator access.
4. Build/Run app.

## Android APK
### Debug APK
```bash
./gradlew assembleDebug
```
APK output:
`app/build/outputs/apk/debug/app-debug.apk`

### Release signed APK
1. Android Studio -> Build -> Generate Signed Bundle/APK.
2. Choose APK and provide keystore.
3. Output in `app/build/outputs/apk/release/`.

## API docs
- Postman collection: `backend/src/docs/postman_collection.json`

## Environment files
- `backend/.env`
- `backend/.env.example`


## Conflict verification
```bash
bash scripts/verify-no-conflicts.sh
```


## Automatic merge-conflict resolution
```bash
# Resolve known conflicted files using current branch content (ours)
bash scripts/resolve-merge-conflicts.sh origin main codex/develop-internal-sales-operations-android-app-frne8i ours

# Or prefer incoming main versions for conflicted files
bash scripts/resolve-merge-conflicts.sh origin main codex/develop-internal-sales-operations-android-app-frne8i theirs
```


### Merge troubleshooting
```bash
# Diagnose current merge blockers
bash scripts/merge-doctor.sh

# Retry merge; uses local base branch if fetch fails
bash scripts/resolve-merge-conflicts.sh origin main codex/develop-internal-sales-operations-android-app-frne8i ours
```
