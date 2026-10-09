# Implementation Plan - Experiment 8: Menus and WebView

Implement Android Options Menu and dedicated WebView activity for reading books online in BookNest.

## User Review Required

> [!IMPORTANT]
> - **Read Online Feature**: Currently uses an external browser intent (`ACTION_VIEW`). We will implement a dedicated `WebViewActivity` (with Internet permission in `AndroidManifest.xml` and basic back-navigation handling) so users read free resources directly within the app.
> - **Menus**: No Android menus currently exist. We will add an Options Menu (`res/menu/main_menu.xml`) across main activities/screens containing items: **Home**, **Browse Books**, **Favorites**, and **Settings**, and wire them up correctly.
> - **Preservation**: The profile picture / settings button opening `UserSettingsActivity` will be preserved intact.

## Proposed Changes

### 1. Manifest & Permissions
#### [MODIFY] [AndroidManifest.xml](file:///C:/Users/santh/AndroidStudioProjects/exp5notification/app/src/main/AndroidManifest.xml)
- Verify `android.permission.INTERNET` is present (already present).
- Register new `WebViewActivity` in manifest.

### 2. Menu Resource
#### [NEW] [main_menu.xml](file:///C:/Users/santh/AndroidStudioProjects/exp5notification/app/src/main/res/menu/main_menu.xml)
- Create options menu resource with items:
  - Home (`R.id.action_home`)
  - Browse Books (`R.id.action_browse`)
  - Favorites (`R.id.action_favorites`)
  - Settings (`R.id.action_settings`)

### 3. WebView Activity
#### [NEW] [activity_webview.xml](file:///C:/Users/santh/AndroidStudioProjects/exp5notification/app/src/main/res/layout/activity_webview.xml)
- XML layout containing a toolbar/header with back button, book title TextView, and a `WebView`.
#### [NEW] [WebViewActivity.kt](file:///C:/Users/santh/AndroidStudioProjects/exp5notification/app/src/main/java/com/example/exp5notification/ui/WebViewActivity.kt)
- Activity handling `WebView` initialization, JavaScript enablement, `WebViewClient` for in-app navigation, and handling hardware/UI back button presses (`onBackPressed`).

### 4. Options Menu Integration
#### [MODIFY] [MainActivity.kt](file:///C:/Users/santh/AndroidStudioProjects/exp5notification/app/src/main/java/com/example/exp5notification/MainActivity.kt) / Activities
- Implement `onCreateOptionsMenu` and `onOptionsItemSelected` in activities where appropriate (e.g., `MainActivity`, `BookDetailsActivity`, `GenreDetailsActivity`, `UserSettingsActivity`) to provide the navigation menu.

### 5. Read Online Integration
#### [MODIFY] [BookDetailsActivity.kt](file:///C:/Users/santh/AndroidStudioProjects/exp5notification/app/src/main/java/com/example/exp5notification/ui/BookDetailsActivity.kt)
- Update `btnReadFree` click listener to launch `WebViewActivity` passing the `freeReadingUrl` and book title instead of opening an external browser intent.

## Verification Plan

### Automated Tests
- Build project using `gradle_build` (`app:assembleDebug`).

### Manual Verification
- Verify Options Menu appears and items navigate correctly (Home, Browse, Favorites, Settings).
- Verify tapping "Read Online" successfully opens the new in-app `WebViewActivity` loading the book URL with working back navigation.
- Verify profile settings button continues to open `UserSettingsActivity`.
