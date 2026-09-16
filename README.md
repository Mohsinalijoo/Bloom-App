# Bloom – PCOS Wellness App

Kotlin + Jetpack Compose Android app for PCOS daily tracking, featuring onboarding, a home dashboard, Insights, Learn, Settings, and AdMob monetization (App Open + Adaptive Banner).

---

## Setup Instructions

1. Open the project in **Android Studio** (Hedgehog 2023.1.1 or newer), JDK 17.
2. Sync Gradle. If you hit memory errors, set this in `gradle.properties`:

   ```properties
   org.gradle.jvmargs=-Xmx4096m -XX:MaxMetaspaceSize=1024m -Dfile.encoding=UTF-8
   android.useAndroidX=true
   kotlin.code.style=official
   android.nonTransitiveRClass=true
   ```

3. Ensure `namespace` and `applicationId` are `com.example.bloom_app` in `app/build.gradle.kts`.
4. Run on an emulator or device (**API 24+**) with internet access (required for ads).
5. Build APK:

   ```bash
   ./gradlew assembleDebug
   ```

   Output: `app/build/outputs/apk/debug/app-debug.apk`

---

## Architecture Used

- **Pattern:** MVVM + Jetpack Compose (Material 3) + Navigation Compose
- **Persistence:** Jetpack DataStore (onboarding flag, user name, tracking prefs, daily logs)
- **Async:** Kotlin Coroutines + Flow

```
com.example.bloom_app/
├── ads/          # AdConfig, AdAnalytics, AppOpenAdManager, BannerAdView
├── data/         # PreferencesManager, BloomRepository, domain models, PCOS Q&A
├── navigation/   # Splash → Onboarding → Home routing
└── ui/
    ├── theme/    # Colors, Typography, Theme
    ├── components/ & dialogs/   # Reusable composables + log dialogs
    ├── splash/   # Splash screen
    ├── onboarding/ # 4-step flow + ViewModel
    ├── home/     # Dashboard + MainScreen (bottom-nav container)
    └── screens/  # Insights, Learn, Settings
```

**Flow:**  
First launch → `Splash → Onboarding (4 steps) → Home`  
Returning launch → `Splash → Home` (+ App Open Ad if eligible)

---

## Ad Implementation Approach

- **SDK init:** `MobileAds.initialize()` in `BloomApplication.onCreate()`, wrapped in try/catch so ad issues never crash the app.
- **App Open Ad** (`AppOpenAdManager`):
  - Lifecycle-aware via `ProcessLifecycleOwner` + `ActivityLifecycleCallbacks`
  - First-time users are **never interrupted** (no ad during onboarding)
  - Returning users: ad shows on cold start / background→foreground when loaded
  - Preloads next ad after dismiss/failure; 4-hour load expiry
  - All ad calls posted to the **main thread**; guards against duplicate shows, no-fill, and offline states
  - App continues normally if no ad is available (non-blocking)
- **Banner Ad** (`BannerAdView`):
  - Adaptive anchored banner on Home, below content and above bottom nav
  - Does not overlap wellness content; failures logged silently
- **Analytics:** basic Logcat events via `AdAnalytics` (`app_open_ad_*`, `banner_ad_*`)

---

## Test Ad IDs Used

| Type         | Ad Unit ID |
|--------------|-----------------------------------------------|
| App ID       | `ca-app-pub-3940256099942544~3347511713` |
| App Open Ad  | `ca-app-pub-3940256099942544/9257395921` |
| Banner Ad    | `ca-app-pub-3940256099942544/9214589741` |

These are Google's official test IDs, configured in `ads/AdConfig.kt` and `AndroidManifest.xml`. Replace with real AdMob IDs before production release.

---

## Assumptions / Limitations

- Local-only app (no backend, auth, or cloud sync); clearing app data resets progress
- Test AdMob IDs only; test servers occasionally return "no fill" — retry 2–3 cold starts
- Reminder time preference is saved, but system notifications are not scheduled
- Learn section content is educational, **not medical advice**
- System fonts used by default (DM Serif/Inter optional via `res/font`)
- Insights chart is a simple Compose drawing (no chart library)
- No dark theme, no localization, no automated tests in this build
