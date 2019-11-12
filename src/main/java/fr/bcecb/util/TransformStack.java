package fr.bcecb.util;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

public class TransformStack extends Transform {
    private Transform[] transforms;

    private int current;

    public TransformStack(int stackSize) {
        if (stackSize < 1) {
            throw new IllegalArgumentException("stackSize must be >= 1");
        }

        this.transforms = new Transform[stackSize - 1];

        for (int i = 0; i < transforms.length; i++) {
            this.transforms[i] = new Transform();
        }
    }

    @CanIgnoreReturnValue
    public Transform clear() {
        this.current = 0;
        identity();
        return this;
    }

    @CanIgnoreReturnValue
    public TransformStack pushTransform() {
        if (current == transforms.length) {
            throw new IllegalStateException("Max transform stack size of " + (current + 1) + " reached");
        }

        this.transforms[current++].set(this);
        return this;
    }

    @CanIgnoreReturnValue
    public TransformStack popTransform() {
        set(transforms[--current]);
        return this;
    }

    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + current;
        for (int i = 0; i < current; i++) {
            result = prime * result + transforms[i].hashCode();
        }
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (obj instanceof TransformStack) {
            TransformStack other = (TransformStack) obj;
            if (current != other.current)
                return false;
            for (int i = 0; i < current; i++) {
                if (!transforms[i].equals(other.transforms[i]))
                    return false;
            }
        }
        return true;
    }
}