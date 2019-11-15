package fr.bcecb.util;

import org.joml.Matrix4f;
import org.joml.Vector4f;

public class Transform {
    public final Matrix4f model = new Matrix4f();
    public final Vector4f color = new Vector4f(1.0f);

    public Transform() {
    }

    public Transform identity() {
        this.model.identity();
        this.color.set(1.0f);
        return this;
    }

    public Transform set(Transform transform) {
        this.model.set(transform.model);
        this.color.set(transform.color);
        return this;
    }

    public Transform translate(float x, float y) {
        this.model.translate(x, y, 0);
        return this;
    }

    public Transform rotateZ(float angle) {
        this.model.rotateZ((float) Math.toRadians(angle));
        return this;
    }

    public Transform scale(float x, float y) {
        this.model.scaleXY(x, y);
        return this;
    }

    public Transform color(float red, float green, float blue, float alpha) {
        this.color.mul(red, green, blue, alpha);
        return this;
    }

    public Transform color(Vector4f color) {
        this.color.mul(color);
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + model.hashCode();
        result = prime * result + color.hashCode();

        return result;
    }
}