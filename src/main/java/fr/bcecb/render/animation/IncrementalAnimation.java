package fr.bcecb.render.animation;

public abstract class IncrementalAnimation<T> extends Animation<T> {
    private long ticks;
    private long maxTicks;

    public IncrementalAnimation(T startValue, long maxTicks) {
        super(startValue);
        this.ticks = 0;
        this.maxTicks = maxTicks;
    }

    @Override
    public void reset() {
        super.reset();
        this.ticks = 0;
    }

    @Override
    public void update() {
        super.update();

        this.ticks = (ticks + 1) % maxTicks;
    }

    @Override
    public T getLastValue() {
        return getValue(ticks - 1);
    }

    @Override
    public T getCurrentValue() {
        return getValue(ticks);
    }

    @Override
    public T getNextValue() {
        return getValue(ticks + 1);
    }

    protected abstract T getValue(long ticks);
}
