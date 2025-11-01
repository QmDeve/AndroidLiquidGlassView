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

package com.qmdeve.liquidglass.impl;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RenderEffect;
import android.graphics.RenderNode;
import android.graphics.RuntimeShader;
import android.graphics.Shader;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.qmdeve.liquidglass.Config;
import com.qmdeve.liquidglass.R;

import org.intellij.lang.annotations.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public final class LiquidGlassimpl implements Impl {

    private final View host, target;
    private final RenderNode node;
    private RenderEffect cachedBlurEffect;
    private final int[] tp = new int[2];
    private final int[] hp = new int[2];
    private final RuntimeShader refractionShader, materialShader, dispersionShader, tintShader;
    private float lastCornerRadius, lastEccentricFactor, lastRefractionHeight, lastRefractionAmount, lastContrast, lastWhitePoint, lastChromaMultiplier, lastSigma, lastChromaticAberration, lastDepthEffect, lastBlurLevel, lastTintRed, lastTintGreen, lastTintBlue, lastTintAlpha;
    private boolean needsUpdate = true;

    public LiquidGlassimpl(View host, View target) {
        this.host = host;
        this.target = target;
        this.node = new RenderNode("AndroidLiquidGlassView");
        this.refractionShader = loadagsl(target.getResources(), R.raw.liquidglass_refraction_effect);
        this.materialShader = loadagsl(target.getResources(), R.raw.liquidglass_material_effect);
        this.dispersionShader = loadagsl(target.getResources(), R.raw.liquidglass_dispersion_effect);
        this.tintShader = loadagsl(target.getResources(), R.raw.liquidglass_tint_effect);

        lastCornerRadius = Float.NaN;
        lastEccentricFactor = Float.NaN;
        lastRefractionHeight = Float.NaN;
        lastRefractionAmount = Float.NaN;
        lastContrast = Float.NaN;
        lastWhitePoint = Float.NaN;
        lastChromaMultiplier = Float.NaN;
        lastSigma = Float.NaN;
        lastBlurLevel = Float.NaN;
        lastChromaticAberration = Float.NaN;
        lastDepthEffect = Float.NaN;
        lastTintRed = Float.NaN;
        lastTintGreen = Float.NaN;
        lastTintBlue = Float.NaN;
        lastTintAlpha = Float.NaN;

        host.post(this::applyRenderEffect);
    }

    @Override
    public void onSizeChanged(int w, int h) {
        node.setPosition(0, 0, w, h);
        record();
        applyRenderEffect();
    }

    @Override
    public void onPreDraw() {
        record();

        float cornerRadius = Config.CORNER_RADIUS_PX;
        float eccentricFactor = Config.ECCENTRIC_FACTOR;
        float refractionHeight = Config.REFRACTION_HEIGHT;
        float refractionAmount = Config.REFRACTION_OFFSET;
        float contrast = Config.CONTRAST;
        float whitePoint = Config.WHITE_POINT;
        float chromaMultiplier = Config.CHROMA_MULTIPLIER;
        float sigma = 0;
        float blurLevel = Config.BLUR_RADIUS;
        float chromaticAberration = Config.DISPERSION;
        float depthEffect = Config.DEPTH_EFFECT;
        float tintRed = Config.TINT_COLOR_RED;
        float tintGreen = Config.TINT_COLOR_GREEN;
        float tintBlue = Config.TINT_COLOR_BLUE;
        float tintAlpha = Config.TINT_ALPHA;

        boolean paramsChanged =
                lastCornerRadius != cornerRadius ||
                        lastEccentricFactor != eccentricFactor ||
                        lastRefractionHeight != refractionHeight ||
                        lastRefractionAmount != refractionAmount ||
                        lastContrast != contrast ||
                        lastWhitePoint != whitePoint ||
                        lastChromaMultiplier != chromaMultiplier ||
                        lastSigma != sigma ||
                        lastBlurLevel != blurLevel ||
                        lastChromaticAberration != chromaticAberration ||
                        lastDepthEffect != depthEffect ||
                        lastTintRed != tintRed ||
                        lastTintGreen != tintGreen ||
                        lastTintBlue != tintBlue ||
                        lastTintAlpha != tintAlpha ||
                        needsUpdate;

        if (paramsChanged) {
            lastCornerRadius = cornerRadius;
            lastEccentricFactor = eccentricFactor;
            lastRefractionHeight = refractionHeight;
            lastRefractionAmount = refractionAmount;
            lastContrast = contrast;
            lastWhitePoint = whitePoint;
            lastChromaMultiplier = chromaMultiplier;
            lastSigma = sigma;
            lastBlurLevel = blurLevel;
            lastChromaticAberration = chromaticAberration;
            lastDepthEffect = depthEffect;
            lastTintRed = tintRed;
            lastTintGreen = tintGreen;
            lastTintBlue = tintBlue;
            lastTintAlpha = tintAlpha;
            needsUpdate = false;
            applyRenderEffect();
        }
    }

    private void record() {
        int w = target.getWidth(), h = target.getHeight();
        if (w == 0 || h == 0) return;

        Canvas rec = node.beginRecording(w, h);
        target.getLocationInWindow(tp);
        host.getLocationInWindow(hp);
        rec.translate(-(hp[0] - tp[0]), -(hp[1] - tp[1]));
        target.draw(rec);
        node.endRecording();
    }

    @Override
    public void draw(Canvas canvas) {
        if (!canvas.isHardwareAccelerated()) {
            return;
        }
        canvas.drawRenderNode(node);
    }

    private void applyRenderEffect() {
        int width = target.getWidth();
        int height = target.getHeight();
        if (width == 0 || height == 0) return;

        float cornerRadiusPx = Config.CORNER_RADIUS_PX;
        float eccentricFactor = Config.ECCENTRIC_FACTOR;
        float refractionHeight = Config.REFRACTION_HEIGHT;
        float refractionAmount = Config.REFRACTION_OFFSET;
        float contrast = Config.CONTRAST;
        float whitePoint = Config.WHITE_POINT;
        float chromaMultiplier = Config.CHROMA_MULTIPLIER;
        float blurLevel = Config.BLUR_RADIUS;
        float chromaticAberration = Config.DISPERSION;
        float depthEffect = Config.DEPTH_EFFECT;
        float tintRed = Config.TINT_COLOR_RED;
        float tintGreen = Config.TINT_COLOR_GREEN;
        float tintBlue = Config.TINT_COLOR_BLUE;
        float tintAlpha = Config.TINT_ALPHA;
        float[] size = new float[]{Config.WIDTH, Config.HEIGHT};
        float[] offset = new float[]{0f, 0f};
        float[] cornerRadii = new float[]{
                cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx
        };

        RenderEffect contentEffect;

        boolean needNewBlur = cachedBlurEffect == null || Float.isNaN(lastSigma) || blurLevel != lastSigma;

        if (needNewBlur) {
            try {
                contentEffect = RenderEffect.createBlurEffect(blurLevel, blurLevel, Shader.TileMode.CLAMP);
                cachedBlurEffect = contentEffect;
                lastSigma = blurLevel;
            } catch (Exception e) {
                contentEffect = cachedBlurEffect;
            }
        } else {
            contentEffect = cachedBlurEffect;
        }

        refractionShader.setFloatUniform("size", size);
        refractionShader.setFloatUniform("cornerRadius", cornerRadiusPx);
        refractionShader.setFloatUniform("eccentricFactor", eccentricFactor);
        refractionShader.setFloatUniform("refractionHeight", refractionHeight);
        refractionShader.setFloatUniform("refractionAmount", refractionAmount);

        RenderEffect refraction = RenderEffect.createRuntimeShaderEffect(refractionShader, "image");
        RenderEffect refractionChain = (contentEffect != null)
                ? RenderEffect.createChainEffect(refraction, contentEffect)
                : refraction;

        materialShader.setFloatUniform("contrast", contrast);
        materialShader.setFloatUniform("whitePoint", whitePoint);
        materialShader.setFloatUniform("chromaMultiplier", chromaMultiplier);

        RenderEffect material = RenderEffect.createRuntimeShaderEffect(materialShader, "image");
        RenderEffect materialChain = RenderEffect.createChainEffect(material, refractionChain);

        dispersionShader.setFloatUniform("size", size);
        dispersionShader.setFloatUniform("offset", offset);
        dispersionShader.setFloatUniform("cornerRadii", cornerRadii);
        dispersionShader.setFloatUniform("refractionHeight", refractionHeight);
        dispersionShader.setFloatUniform("refractionAmount", refractionAmount);
        dispersionShader.setFloatUniform("depthEffect", depthEffect);
        dispersionShader.setFloatUniform("chromaticAberration", chromaticAberration);

        RenderEffect chromaEffect = RenderEffect.createRuntimeShaderEffect(dispersionShader, "content");

        tintShader.setFloatUniform("tintColor", new float[]{tintRed, tintGreen, tintBlue});
        tintShader.setFloatUniform("tintAlpha", tintAlpha);

        RenderEffect tintEffect = RenderEffect.createRuntimeShaderEffect(tintShader, "content");
        RenderEffect chainAfterChroma = RenderEffect.createChainEffect(tintEffect, chromaEffect);
        RenderEffect finalChain = RenderEffect.createChainEffect(chainAfterChroma, materialChain);

        node.setRenderEffect(finalChain);
    }

    private RuntimeShader loadagsl(Resources resources, int resourceId) {
        @Language("AGSL")
        String shaderCode = loadRaw(resources, resourceId);
        return new RuntimeShader(shaderCode);
    }

    private String loadRaw(Resources resources, int resourceId) {
        try {
            InputStream inputStream = resources.openRawResource(resourceId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            reader.close();
            inputStream.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error: " + resources.getResourceEntryName(resourceId), e);
        }
    }
}