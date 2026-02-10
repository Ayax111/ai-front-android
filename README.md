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

## Abrir en Android Studio
1. Abre esta carpeta como proyecto.
2. Deja que Android Studio sincronice Gradle.
3. Si hace falta, genera el wrapper de Gradle desde Android Studio o con `gradle wrapper`.

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
