package com.first.hdz.animation.explore;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.Random;

/**
 * created by hdz
 * on 2018/8/29
 */
public class DropParticle extends Particle {

    private static float getBaseRadius(float radius, Random random, float nextFloat) {
        float r = radius + radius * (random.nextFloat() - 0.5f) * 0.5f;
        r = nextFloat < 0.6 ? r : nextFloat < 0.8f ? r * 1.4f : r * 1.6f;
        return r;
    }

    private static float getHorizontalElement(Rect rect, Random random, float nextFloat, float horizontalMultiple) {
        float horizontal = rect.width() * (random.nextFloat() - 0.5f);
        horizontal = nextFloat < 0.2f ? horizontal : nextFloat < 0.8f ? horizontal * 0.6f : horizontal * 0.3f;
        return horizontal * horizontalMultiple;
    }

    private static float getVerticalElement(Rect rect, Random random, float nextFloat, float verticalMultiple) {
        float vertical = rect.height() * (random.nextFloat() * 0.5f + 0.5f);
        vertical = nextFloat < 0.2f ? vertical : nextFloat < 0.8f ? vertical * 1.2f : vertical * 1.4f;
        return vertical * verticalMultiple;
    }

    public DropParticle(Point point, int color, int radius, Rect rect, float endValue, Random random, float horizontalMultiple, float verticalMultiple) {
        this.color = color;
        this.alpha = 1;
        float nextFloat = random.nextFloat();
        this.baseRadius = getBaseRadius(radius, random, nextFloat);
        this.radius = this.baseRadius;
        this.horizontalElement = getHorizontalElement(rect, random, nextFloat, horizontalMultiple);
        this.verticalElement = getVerticalElement(rect, random, nextFloat, verticalMultiple);
        this.baseCx = point.x;
        this.baseCy = point.y;
        this.font = endValue / 10 * random.nextFloat();
        later = 0.4f * random.nextFloat();
    }

    @Override
    public void advance(float factor, float endValue) {
        float normalization = factor / endValue;
        if (normalization < this.font) {
            this.alpha = 1;
            return;
        }
        if (normalization > 1f - later) {
            this.alpha = 0;
            return;
        }
        this.alpha = 1;
        normalization = (normalization - this.font) / (1f - this.font - this.later);
        if (normalization >= 0.7f) {
            this.alpha = 1f - (normalization - 0.7f) / 0.3f;
        }
        float realValue = normalization * endValue;
        cx = this.baseCx + this.horizontalElement * realValue;
        cy = this.baseCy + this.verticalElement * realValue;
        radius = baseRadius + baseRadius / 6 * realValue;
    }
}
