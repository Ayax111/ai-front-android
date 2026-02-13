# AI Front (Android)

AI Front is an Android chat app designed to work with local AI models exposed through an OpenAI-compatible API (for example, LM Studio or `llama.cpp` server mode).

## Tech Stack
- Kotlin
- Jetpack Compose
- `ViewModel`-based state management
- `LocalModelEngine` abstraction for pluggable inference backends

## Project Structure
- `app/src/main/java/com/ayax/iafront/ChatScreen.kt`: main chat UI and options drawer
- `app/src/main/java/com/ayax/iafront/ChatViewModel.kt`: app state and chat workflow
- `app/src/main/java/com/ayax/iafront/ai/LocalModelEngine.kt`: model engine contract
- `app/src/main/java/com/ayax/iafront/ai/ApiLocalModelEngine.kt`: OpenAI-compatible HTTP client
- `app/src/main/java/com/ayax/iafront/data/ChatHistoryStore.kt`: local conversation persistence

## Current Features
- Local server URL configuration from the app
- Dynamic model discovery (`/v1/models`) and model selection
- Chat history saved on-device with rename/delete
- Markdown rendering for model responses
- Automatic light/dark theme based on system settings

## Run from VS Code (without Android Studio)
1. Install JDK 17.
2. Install Android SDK command-line tools (automated):

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\install-android-sdk.ps1
```

3. If you prefer manual installation, install at least:
   - `platform-tools`
   - `platforms;android-35`
   - `build-tools;35.0.0`
   - `emulator` (if using AVD)
4. Set `ANDROID_SDK_ROOT` (or `ANDROID_HOME`) and add SDK tools to `PATH`.
5. In this repository, run:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\setup-android-env.ps1
.\gradlew.bat assembleDebug
```

6. With a connected device (USB debugging) or running emulator:

```powershell
.\gradlew.bat installDebug
adb shell am start -n com.ayax.iafront/com.ayax.iafront.MainActivity
```

VS Code tasks are also available under `Terminal > Run Task`:
- `android: setup local.properties`
- `gradle: assembleDebug`
- `gradle: installDebug`
- `android: run debug app`
- `adb: logcat`

## GitHub Setup (Ayax111)
If the remote repository does not exist yet:

```powershell
git init
git add .
git commit -m "chore: bootstrap android local-ai chat app"
git branch -M main
git remote add origin https://github.com/Ayax111/ia-front-android.git
git push -u origin main
```

## Privacy and Safe Publishing
- Never commit a real `.env`; keep only `.env.example`.
- `local.properties`, keystores, and signing keys must stay ignored by `.gitignore`.
- Do not hardcode private IPs, local machine paths, API keys, or personal identifiers.
