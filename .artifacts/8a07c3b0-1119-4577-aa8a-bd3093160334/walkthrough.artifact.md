# Walkthrough - Experiment 8: Menus and WebView

I have successfully implemented **Experiment 8: Implement Menus and WebView** in the BookNest Android application while preserving all previous experiment features (Experiment 5 notifications and Experiment 6 basic views).

## 🚀 What Was Implemented / Verified

### 1. Android Options Menu (`res/menu/main_menu.xml`)
- **Present / Added**: Created a proper Android options menu resource containing:
  - **Home** (`action_home`)
  - **Browse Genres** (`action_browse`)
  - **Favorites** (`action_favorites`)
  - **Settings** (`action_settings`)
- **Integration**: Implemented `onCreateOptionsMenu` and `onOptionsItemSelected` across `MainActivity`, `BookDetailsActivity`, `GenreDetailsActivity`, and `UserSettingsActivity`.

### 2. In-App WebView (`WebViewActivity.kt` & `activity_webview.xml`)
- **Present / Added**: Replaced the external browser intent with a dedicated in-app reading screen.
- **Features**:
  - `INTERNET` permission in `AndroidManifest.xml`.
  - Secure JavaScript settings (`javaScriptEnabled = true`).
  - `WebViewClient` and `WebChromeClient` for smooth page loading and progress indicator tracking.
  - Robust back-navigation (`canGoBack()` history handling via `OnBackPressedDispatcher` and custom back button).

### 3. Profile & Settings Navigation
- **Preserved**: Tapping the profile picture / settings icon correctly opens `UserSettingsActivity` passing user name and USN intent extras.

### 4. Lifecycle Logging (`Log.d`)
- Added comprehensive lifecycle logging (`onCreate`, `onStart`, `onResume`, `onPause`, `onStop`, `onDestroy`) across all core activities (`MainActivity`, `LoginActivity`, `BookDetailsActivity`, `GenreDetailsActivity`, `UserSettingsActivity`, `WebViewActivity`) with distinct TAGs for lab demonstration.

---

## 📁 Modified / Created Files

1. **`app/src/main/AndroidManifest.xml`** — Registered `WebViewActivity`.
2. **`app/src/main/res/menu/main_menu.xml`** [NEW] — Options menu definitions.
3. **`app/src/main/res/layout/activity_webview.xml`** [NEW] — WebView layout with progress bar and toolbar.
4. **`app/src/main/java/com/example/exp5notification/ui/WebViewActivity.kt`** [NEW] — Dedicated in-app WebView reader activity.
5. **`app/src/main/java/com/example/exp5notification/MainActivity.kt`** — Added options menu handling and lifecycle logging.
6. **`app/src/main/java/com/example/exp5notification/ui/BookDetailsActivity.kt`** — Wired "Read Free" to `WebViewActivity`, added options menu and lifecycle logging.
7. **`app/src/main/java/com/example/exp5notification/ui/GenreDetailsActivity.kt`** — Added options menu and lifecycle logging.
8. **`app/src/main/java/com/example/exp5notification/ui/UserSettingsActivity.kt`** — Added options menu and lifecycle logging.
9. **`app/src/main/java/com/example/exp5notification/ui/LoginActivity.kt`** — Added lifecycle logging.

---

## ✅ Test Results
- **Gradle Build**: `app:assembleDebug` completed successfully (`BUILD SUCCESSFUL`).
- **Options Menu**: Verified menu items appear and navigate correctly.
- **WebView Reading**: Verified free book resources load correctly inside `WebViewActivity` with back-navigation handling.
- **Existing Features**: Experiment 5 notifications and Experiment 6 basic views remain fully functional without regression.
