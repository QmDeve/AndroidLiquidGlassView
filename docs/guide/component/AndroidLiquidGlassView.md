# AndroidLiquidGlassView

<div class="warning" style="padding: 10px; border-left: 4px solid #f0ad4e; background-color: #fcf8e3; margin: 10px 0;">
On devices below <strong>Android 13</strong>, the view will maintain a transparent background and will not render any effects
</div>

### XML Layout
```xml
<RelativeLayout
    android:id="@+id/root"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- Content Container -->
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

    <!-- Liquid Glass View -->
    <com.qmdeve.liquidglass.widget.LiquidGlassView
        android:id="@+id/liquidGlassView"
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_centerInParent="true" />
</RelativeLayout>
```

### Binding the Sampling Source
```java
ViewGroup content = findViewById(R.id.content_container);
LiquidGlassView liquidGlassView = findViewById(R.id.liquidGlassView);

// Bind the sampling source
liquidGlassView.bind(content);
```

### API Reference
| Method                                | Description                           | Default |
|-----------------------------------|------------------------------|--------|
| `bind(ViewGroup source)`          | **Bind the sampling source**                    | `-`    |
| `setCornerRadius(float px)`       | **Set corner radius (px) (0dp-99dp)**   | `40dp` |
| `setRefractionHeight(float px)`   | **Set refraction height (px) (12dp-50dp)**  | `20dp` |
| `setRefractionOffset(float px)`   | **Set refraction offset (px) (20dp-120dp)** | `70dp` |
| `setTintColorRed(float red)`      | **Set tint color (R) (0f-1f)**          | `1.0f` |
| `setTintColorGreen(float green)`  | **Set tint color (G) (0f-1f)**          | `1.0f` |
| `setTintColorBlue(float blue)`    | **Set tint color (B) (0f-1f)**          | `1.0f` |
| `setTintAlpha(float alpha)`       | **Set tint visibility (0f-1f)**         | `0.0f` |
| `setDispersion(float dispersion)` | **Set dispersion effect (0f-1f)**           | `-`    |
| `setBlurRadius(float radius)`     | **Set blur radius**                   | `0f`   |
| `setDraggableEnabled(boolean enable)`    | **Enable/Disable dragging**           | `false` |
| `setElasticEnabled(boolean enable)`    | **Enable/Disable elastic effect**         | `false` |
| `setTouchEffectEnabled(boolean enable)`    | **Enable/Disable touch effect**     | `false` |

---