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

package com.qmdeve.liquidglass.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.qmdeve.liquidglass.LiquidGlass;
import com.qmdeve.liquidglass.Config;
import com.qmdeve.liquidglass.util.Utils;

public class LiquidGlassView extends FrameLayout {

    private LiquidGlass glass;
    private ViewGroup customSource;
    private final Context context;
    private float cornerRadius = Utils.dp2px(getResources(), 40), refractionHeight = Utils.dp2px(getResources(), 20), refractionOffset = -Utils.dp2px(getResources(), 70), tintAlpha = 0.0f, tintColorRed = 1.0f, tintColorGreen = 1.0f, tintColorBlue = 1.0f, blurRadius = 0f, dispersion = 0.5f, downX, downY, startTx, startTy;
    private boolean draggable = true;

    public LiquidGlassView(Context context) {
        super(context);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        this.context = context;
        init();
    }

    public LiquidGlassView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public LiquidGlassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        setClipToPadding(false);
        setClipChildren(false);
    }

    /**
     * Bind sampling source
     *
     * @param source ViewGroup
     */
    public void bind(ViewGroup source) {
        this.customSource = source;
        if (glass != null && source != null) {
            glass.init(source);
        }
    }

    /**
     * Set the corner radius dp
     *
     * @param dp float
     */
    public void setCornerRadiusDp(float dp) {
        setCornerRadiusPx(Utils.dp2px(getResources(), dp));
        updateConfig();
    }

    /**
     * Set the corner radius px
     *
     * @param px float
     */
    public void setCornerRadiusPx(float px) {
        this.cornerRadius = px;
        updateConfig();
    }

    /**
     * Set the refraction height dp
     *
     * @param dp float
     */
    public void setRefractionHeightDp(float dp) {
        setRefractionHeightPx(Utils.dp2px(getResources(), dp));
        updateConfig();
    }

    /**
     * Set the refraction height px
     *
     * @param px float
     */
    public void setRefractionHeightPx(float px) {
        float minPx = Utils.dp2px(getResources(), 12);
        float maxPx = Utils.dp2px(getResources(), 50);
        this.refractionHeight = Math.max(minPx, Math.min(maxPx, px));
        updateConfig();
    }

    /**
     * Set the refraction offset dp
     * Positive value will be converted to negative
     *
     * @param dp float
     */
    public void setRefractionOffsetDp(float dp) {
        setRefractionOffsetPx(Utils.dp2px(getResources(), dp));
        updateConfig();
    }

    /**
     * Set the refraction offset px
     * Positive value will be converted to negative
     *
     * @param px float
     */
    public void setRefractionOffsetPx(float px) {
        float minPx = Utils.dp2px(getResources(), 20);
        float maxPx = Utils.dp2px(getResources(), 120);
        px = Math.max(minPx, Math.min(maxPx, px));
        this.refractionOffset = -px;
        updateConfig();
    }

    /**
     * Set the tint color (R)
     *
     * @param red float (0f-1f)
     */
    public void setTintColorRed(float red) {
        this.tintColorRed = red;
        updateConfig();
    }

    /**
     * Set the tint color (G)
     *
     * @param green float (0f-1f)
     */
    public void setTintColorGreen(float green) {
        this.tintColorGreen = green;
        updateConfig();
    }

    /**
     * Set the tint color (B)
     *
     * @param blue float (0f-1f)
     */
    public void setTintColorBlue(float blue) {
        this.tintColorBlue = blue;
        updateConfig();
    }

    /**
     * Set the tint Alpha
     *
     * @param alpha float (0f-1f)
     */
    public void setTintAlpha(float alpha) {
        this.tintAlpha = alpha;
        updateConfig();
    }

    /**
     * Set dispersion
     *
     * @param dispersion float (0f-1f)
     */
    public void setDispersion(float dispersion) {
        this.dispersion = Math.max(0f, Math.min(1f, dispersion));
        updateConfig();
    }

    /**
     * Set the blur radius
     *
     * @param radius float
     */
    public void setBlurRadius(float radius) {
        this.blurRadius = Math.max(0, Math.min(50, radius));
        updateConfig();
    }

    /**
     * Set whether the View is draggable or not
     *
     * @param enable boolean
     */
    public void setDraggable(boolean enable) {
        this.draggable = enable;
    }

    private void updateConfig() {
        if (glass == null) {
            rebuild();
            return;
        }

        int w = getWidth();
        int h = getHeight();
        if (w <= 0) w = Utils.getDeviceWidthPx(context);
        if (h <= 0) h = getResources().getDisplayMetrics().heightPixels;

        Config.CORNER_RADIUS_PX = cornerRadius;
        Config.REFRACTION_HEIGHT = refractionHeight;
        Config.REFRACTION_OFFSET = refractionOffset;
        Config.BLUR_RADIUS = blurRadius;
        Config.WIDTH = w;
        Config.HEIGHT = h;
        Config.DISPERSION = dispersion;
        Config.TINT_ALPHA = tintAlpha;
        Config.TINT_COLOR_BLUE = tintColorBlue;
        Config.TINT_COLOR_GREEN = tintColorGreen;
        Config.TINT_COLOR_RED = tintColorRed;

        glass.post(() -> glass.updateParameters());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(this::ensureGlass);
    }

    @Override
    protected void onDetachedFromWindow() {
        removeGlass();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            if (w > 0 && h > 0) {
                rebuild();
            }
        }
    }

    private void rebuild() {
        removeGlass();
        post(this::ensureGlass);
    }

    private void ensureGlass() {
        if (glass != null) return;

        int w = getWidth();
        int h = getHeight();
        if (w <= 0) w = Utils.getDeviceWidthPx(context);
        if (h <= 0) h = getResources().getDisplayMetrics().heightPixels;

        Config.configure(new Config.Overrides()
                .noFilter()
                .contrast(0f)
                .whitePoint(0f)
                .chromaMultiplier(1f)
                .blurRadius(blurRadius)
                .cornerRadius(cornerRadius)
                .refractionHeight(refractionHeight)
                .refractionOffset(refractionOffset)
                .tintAlpha(tintAlpha)
                .tintColorRed(tintColorRed)
                .tintColorGreen(tintColorGreen)
                .tintColorBlue(tintColorBlue)
                .dispersion(dispersion)
                .size(w, h)
        );

        glass = new LiquidGlass(getContext());

        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        addView(glass, lp);

        ViewGroup source = customSource;
        if (source == null && getParent() instanceof ViewGroup) {
            return;
        }
        glass.init(source);
    }

    private void removeGlass() {
        if (glass != null) {
            ViewGroup p = (ViewGroup) glass.getParent();
            if (p != null) p.removeView(glass);
            glass = null;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(android.view.MotionEvent e) {
        if (!draggable) return super.onTouchEvent(e);
        switch (e.getActionMasked()) {
            case android.view.MotionEvent.ACTION_DOWN:
                downX = e.getRawX();
                downY = e.getRawY();
                startTx = getTranslationX();
                startTy = getTranslationY();
                return true;
            case android.view.MotionEvent.ACTION_MOVE: {
                float dx = e.getRawX() - downX;
                float dy = e.getRawY() - downY;
                float tx = startTx + dx;
                float ty = startTy + dy;

                ViewGroup parent = (ViewGroup) getParent();
                if (parent != null) {
                    int pw = parent.getWidth(), ph = parent.getHeight();
                    int w = getWidth(), h = getHeight();
                    if (pw > 0 && ph > 0 && w > 0 && h > 0) {
                        float minX = -getLeft();
                        float maxX = pw - getLeft() - w;
                        float minY = -getTop();
                        float maxY = ph - getTop() - h;
                        if (tx < minX) tx = minX;
                        if (tx > maxX) tx = maxX;
                        if (ty < minY) ty = minY;
                        if (ty > maxY) ty = maxY;
                    }
                }
                setTranslationX(tx);
                setTranslationY(ty);
                return true;
            }
            case android.view.MotionEvent.ACTION_UP:
            case android.view.MotionEvent.ACTION_CANCEL:
                return true;
            default:
                return super.onTouchEvent(e);
        }
    }
}