package fr.bcecb.state.gui;

import fr.bcecb.event.MouseEvent;
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

    @Override
    public void onClick(MouseEvent.Click event) {

    }

    @Override
    public void onDrag(MouseEvent.Click clickEvent, MouseEvent.Move moveEvent) {

    }

    @Override
    public void onHover(MouseEvent.Move event) {

    }

    @Override
    public void onScroll(MouseEvent.Scroll event) {

    }

    public float getRadius() {
        return radius;
    }
}
