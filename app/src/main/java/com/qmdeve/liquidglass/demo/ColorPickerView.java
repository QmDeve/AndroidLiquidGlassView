/**
 * Copyright 2025 QmDeve
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author QmDeve
 * @github https://github.com/QmDeve
 * @since 2025-11-01
 */

package com.qmdeve.liquidglass.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ColorPickerView extends View {

    public interface OnColorChangeListener {
        void onColorChanged(float r, float g, float b);
    }

    private Paint paint;
    private Bitmap bitmap;
    private RectF rect;
    private float selectorX, selectorY;
    private OnColorChangeListener listener;
    private int viewWidth, viewHeight;
    private boolean isReady = false;
    private final float[] selectedColor = new float[]{1f, 1f, 1f};
    private float cornerRadius;

    public ColorPickerView(Context context) {
        super(context);
        init(context);
    }

    public ColorPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ColorPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rect = new RectF();
        cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, context.getResources().getDisplayMetrics());
    }

    public void setOnColorChangeListener(OnColorChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        rect.set(0, 0, w, h);
        createPalette();
        selectorX = w - 1;
        selectorY = 0;
    }

    private void createPalette() {
        if (viewWidth <= 0 || viewHeight <= 0) return;

        bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);

        int[] colors = {
                0xFFFF0000,
                0xFFFFFF00,
                0xFF00FF00,
                0xFF00FFFF,
                0xFF0000FF,
                0xFFFF00FF,
                0xFFFF0000
        };
        Shader colorShader = new LinearGradient(0, 0, viewWidth, 0, colors, null, Shader.TileMode.CLAMP);
        paint.setShader(colorShader);
        c.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

        Paint overlay = new Paint(Paint.ANTI_ALIAS_FLAG);
        overlay.setShader(new LinearGradient(0, 0, 0, viewHeight,
                0x00FFFFFF,
                0xFFFFFFFF,
                Shader.TileMode.CLAMP));
        overlay.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        c.drawRoundRect(rect, cornerRadius, cornerRadius, overlay);

        isReady = true;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }

        Paint selector = new Paint(Paint.ANTI_ALIAS_FLAG);
        selector.setStyle(Paint.Style.STROKE);
        selector.setStrokeWidth(5);
        selector.setColor(0xFFFFFFFF);
        canvas.drawCircle(selectorX, selectorY, 20, selector);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isReady || bitmap == null) return false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                selectorX = Math.max(0, Math.min(event.getX(), viewWidth - 1));
                selectorY = Math.max(0, Math.min(event.getY(), viewHeight - 1));
                updateColor();
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void updateColor() {
        int color = bitmap.getPixel((int) selectorX, (int) selectorY);
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;

        selectedColor[0] = r;
        selectedColor[1] = g;
        selectedColor[2] = b;

        if (listener != null) {
            listener.onColorChanged(r, g, b);
        }
    }
}