<div align="center">

## AndroidLiquidGlassView
**AndroidLiquidGlassView: An Android View library that adds realistic liquid refraction and distortion effects to your app's UI**

<br>
<br>

  <img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" alt="Apache"/>
  <img src="https://img.shields.io/badge/Java-8-orange" alt="Java 8"/>
  <img src="https://img.shields.io/badge/Android-13.0%2B-brightgreen.svg" alt="Android 5"/>
  <img src="https://img.shields.io/badge/targetSdk-36-green" alt="targetSdk"/>
  <img src="https://img.shields.io/badge/🚀-Feature-purple" alt="Feature"/>
  <img src="https://img.shields.io/badge/Version-v0.0.1-alpha01-blue" alt="Version"/>
  <img src="https://jitpack.io/v/QmDeve/AndroidLiquidGlassView.svg" alt="Jitpack"/>
  <img src="https://img.shields.io/github/stars/QmDeve/AndroidLiquidGlassView" alt="Stars"/>

<br>
<br>

[Telegram Group](https://t.me/QmDeves)

</div>

---

## Characteristic
 - **Realistic `liquid glass` effect - Physically-based `refraction` and `dispersion` effects**
 - **Highly customizable - supports adjustment of rounded corners, refraction parameters, tone, etc.**
 - **Android 13+ native support - using the latest `AGSL` technology**

---

## Requirements
 - **Android API 33 + (Android 13), to get the full glass effect**

---

## Screenshot

<img src="https://github.com/QmDeve/AndroidLiquidGlassView/blob/master/img/image.png?raw=true" alt="Stars"/>

---

## Demo experience
[Download Demo](https://github.com/QmDeve/AndroidLiquidGlassView/blob/master/app/release/app-release.apk)

---

## Quick integration
### 1. Add Maven：
**Add the following to `settings.gradle` in the project root directory:**
```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
       mavenCentral()
       maven { url 'https://jitpack.io' }
  }
}
```

### 2. Add Dependencies：
**Add the following to the module's `build.gradle`:**
```gradle
dependencies {
   implementation 'com.github.QmDeve:AndroidLiquidGlassView:v0.0.1-alpha01'
}
```

---

## How to use
### XML layout
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/root"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- content_container -->
    <FrameLayout
        android:id="@+id/content_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <ImageView
                android:id="@+id/images"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:scaleType="centerCrop"
                android:src="@drawable/image" />

        </RelativeLayout>
    </FrameLayout>

    <!-- LiquidGlassView -->
    <com.qmdeve.liquidglass.widget.LiquidGlassView
        android:id="@+id/liquidGlassView"
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_centerInParent="true" />
</RelativeLayout>
```

---

## Bind sampling source
```java
ViewGroup content = findViewById(R.id.content_container);
LiquidGlassView liquidGlassView = findViewById(R.id.liquidGlassView);

liquidGlassView.bind(content);
```

## API Reference
| Method                            | Description                               | Default Value |
|-----------------------------------|-------------------------------------------|---------------|
| `bind(ViewGroup source)`          | **Bind sampling source**                  | `-`           |
| `setCornerRadiusDp(float dp)`     | **Set the corner radius (dp)**            | `40dp`        |
| `setCornerRadiusPx(float px)`     | **Set the corner radius (px)**            | `-`           |
| `setRefractionHeightDp(float dp)` | **Set the refraction height (dp)**        | `20dp`        |
| `setRefractionHeightPx(float px)` | **Set the refraction height (px)**        | `-`           |
| `setRefractionOffsetDp(float dp)` | **Set refraction offset (dp)**            | `-70dp`       |
| `setRefractionOffsetPx(float px)` | **Set refraction offset (px)**            | `0`           |
| `setTintColorRed(float red)`      | **Set the red tone (0f-1f)**              | `1.0f`        |
| `setTintColorGreen(float green)`  | **Set the green tone (0f-1f)**            | `1.0f`        |
| `setTintColorBlue(float blue)`    | **Set the blue tone (0f-1f)**             | `1.0f`        |
| `setTintAlpha(float alpha)`       | **Set Tint transparency (0f-1f)**         | `0.0f`        |
| `setDispersion(float dispersion)` | **Set the dispersion effect (0f-1f)**     | `0.5f`        |
| `setBlurRadius(float radius)`     | **Set the blur radius**                   | `0f`          |
| `setDraggable(boolean enable)`    | **Enable/disable drag-and-drop function** | `true`        |

---

## Effect explaination
**On `Android 13` and later devices, the library renders the full `Liquid Glass` effect, including:**
 - **`Physics-based refraction effects`**
 - **`Adjustable blur effect`**
 - **`Dispersion effect`**
 - **`Custom tint overlay`**

**On devices below Android 13, the view will maintain a transparent background and will not render any effects**

## Notes for using library
**1.`Sampling source`：** Ensure that the bound sampling source view contains valid content

**2.`Compatibility`：** Full features are only supported on `Android 13+`

---

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=QmDeve/AndroidLiquidGlassView&type=date&legend=bottom-right)](https://www.star-history.com/#QmDeve/AndroidLiquidGlassView&type=date&legend=bottom-right)

---

### My other open-source library
 - **[QmBlurView](https://github.com/QmDeve/QmBlurView)**