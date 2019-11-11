package fr.bcecb.render.animation;

public abstract class Animation<T> {
    private final T startValue;
    private T lastValue;
    private T currentValue;

    public Animation(T startValue) {
        this.startValue = startValue;
        this.lastValue = startValue;
        this.currentValue = startValue;
    }

    public void reset() {
        this.lastValue = startValue;
    }

    public T getLastValue() {
        return lastValue;
    }

    public T getCurrentValue() {
        return currentValue;
    }

    public void update() {
        this.lastValue = this.currentValue;
        this.currentValue = getNextValue();
    }

    public abstract T getNextValue();
    public abstract T getInterpolatedValue(float partialTicks);
}