# IA Front (Android)

Base inicial de una app Android tipo chat enfocada en usar modelos locales de IA.

## Stack
- Kotlin
- Jetpack Compose
- Arquitectura simple con `ViewModel`
- Abstraccion `LocalModelEngine` para conectar inferencia local despues

## Estructura
- `app/src/main/java/com/ayax/iafront/ChatScreen.kt`: UI de chat
- `app/src/main/java/com/ayax/iafront/ChatViewModel.kt`: estado y logica
- `app/src/main/java/com/ayax/iafront/ai/LocalModelEngine.kt`: contrato del motor local
- `app/src/main/java/com/ayax/iafront/ai/FakeLocalModelEngine.kt`: mock inicial

## Siguientes pasos para IA local real
1. Integrar `llama.cpp` Android (JNI) o ONNX Runtime / MediaPipe.
2. Cargar un modelo GGUF/ONNX desde almacenamiento interno.
3. Reemplazar `FakeLocalModelEngine` por un motor real.
4. Agregar streaming de tokens y cancelacion de generacion.

## Ejecutar desde VSCode (sin Android Studio)
1. Instala JDK 17.
2. Instala Android SDK command-line tools.
3. Instala paquetes minimos:
   - `platform-tools`
   - `platforms;android-35`
   - `build-tools;35.0.0`
4. Define `ANDROID_SDK_ROOT` (o `ANDROID_HOME`) y agrega `platform-tools` al `PATH`.
5. En este repo corre:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\setup-android-env.ps1
.\gradlew.bat assembleDebug
```

6. Con dispositivo conectado (depuracion USB) o emulador activo:

```powershell
.\gradlew.bat installDebug
adb shell am start -n com.ayax.iafront/com.ayax.iafront.MainActivity
```

Tambien tienes tareas en VSCode (`Terminal > Run Task`):
- `android: setup local.properties`
- `gradle: assembleDebug`
- `gradle: installDebug`
- `android: run debug app`
- `adb: logcat`

## GitHub (Ayax111)
Si aun no existe el remoto:

```powershell
git init
git add .
git commit -m "chore: bootstrap android local-ai chat app"
git branch -M main
git remote add origin https://github.com/Ayax111/ia-front-android.git
git push -u origin main
```

## Privacidad del repositorio
- No subas archivos `.env` reales; usa solo `.env.example`.
- `local.properties`, keystores y llaves de firma estan ignorados en `.gitignore`.
- Evita hardcodear IPs privadas, rutas de tu PC o tokens en el codigo.
