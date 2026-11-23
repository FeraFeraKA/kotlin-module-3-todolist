# TodoList

Android-приложение на Kotlin и Jetpack Compose. Показывает список задач из JSON, открывает экран задачи и позволяет изменить её статус.

Для сборки нужны JDK 17 и Android SDK Platform 36. Приложение поддерживает Android 7.0 (API 24) и выше.

```sh
./gradlew :todolist:assembleDebug
./gradlew :todolist:testDebugUnitTest
```

Для инструментальных тестов подключите устройство или запустите эмулятор Android.

```sh
./gradlew :todolist:connectedDebugAndroidTest
```
