package fr.bcecb.render.animation;

import org.joml.Math;

public class BounceAnimation extends IncrementalAnimation<Float> {
    private final float offset;
    private final float power;
    private final float speed;

    public BounceAnimation(int offset, int power, int speed) {
        super(0.0f, Long.MAX_VALUE);
        this.offset = offset;
        this.power = power;
        this.speed = speed;
    }

    @Override
    public Float getValue(long ticks) {
        return (float) (offset + (power * Math.sin(Math.toRadians((ticks - offset) * speed))));
    }

    @Override
    public Float getInterpolatedValue(float partialTicks) {
        return (1 - partialTicks) * getLastValue() + partialTicks * getCurrentValue();
    }
}
