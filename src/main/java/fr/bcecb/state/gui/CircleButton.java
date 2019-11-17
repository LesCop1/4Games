package fr.bcecb.state.gui;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public class CircleButton extends Button {
    private final float radius;

    public CircleButton(int id, float x, float y, float radius, boolean centered) {
        super(id, x, y, radius * 2, radius * 2, centered, (String) null);
        this.radius = radius;
    }

    public CircleButton(int id, float x, float y, float radius, boolean centered, ResourceHandle<Texture> texture) {
        super(id, x, y, radius * 2, radius * 2, centered, texture);
        this.radius = radius;
    }

    @Override
    public boolean checkBounds(float x, float y) {
        return super.checkBounds(x, y) && Math.pow((getX() + radius) - x, 2) + Math.pow((getY() + radius) - y, 2) < Math.pow(radius, 2);
    }

    public float getRadius() {
        return radius;
    }
}