package fr.bcecb.state.gui;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public class CircleButton extends Button {
    private float radius;

    public CircleButton(int id, float x, float y, float radius) {
        this(id, x, y, radius, false);
    }

    public CircleButton(int id, float x, float y, float radius, boolean centered) {
        super(id, x, y, radius * 2, radius * 2, centered);
        this.radius = radius;
    }

    public CircleButton(int id, float x, float y, float radius, ResourceHandle<Texture> textureResourceHandle) {
        super(id, x, y, radius * 2, radius * 2, textureResourceHandle);
        this.radius = radius;
    }

    public CircleButton(int id, float x, float y, float radius, boolean centered, ResourceHandle<Texture> textureResourceHandle) {
        super(id, x, y, radius * 2, radius * 2, centered, textureResourceHandle);
        this.radius = radius;
    }

    @Override
    boolean checkBounds(float x, float y) {
        return super.checkBounds(x, y) && Math.pow((getX() + getRadius()) - x, 2) + Math.pow((getY() + getRadius()) - y, 2) < Math.pow(getRadius(), 2);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
