package fr.bcecb.state.gui;

import org.joml.Vector4f;

public class RoundedRectangle extends Rectangle {
    private final float radius;

    public RoundedRectangle(int id, float x, float y, float width, float height, boolean centered, float radius) {
        this(id, x, y, width, height, centered, null, radius);
    }

    public RoundedRectangle(int id, float x, float y, float width, float height, boolean centered, Vector4f color, float radius) {
        super(id, x, y, width, height, centered, color);
        this.radius = radius;
    }

    @Override
    public void onUpdate() {

    }

    public float getRadius() {
        return radius;
    }
}
