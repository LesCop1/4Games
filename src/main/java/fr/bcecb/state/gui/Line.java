package fr.bcecb.state.gui;

import fr.bcecb.event.MouseEvent;
import fr.bcecb.util.Constants;
import org.joml.Vector4f;

public class Line extends GuiElement {
    private final float thickness;
    private Vector4f color;

    public Line(int id, float xStart, float yStart, float xEnd, float yEnd, float thickness) {
        this(id, xStart, yStart, xEnd, yEnd, thickness, Constants.COLOR_WHITE);
    }

    public Line(int id, float xStart, float yStart, float xEnd, float yEnd, float thickness, Vector4f color) {
        super(id, xStart, yStart, xEnd, yEnd);
        this.thickness = thickness;
        this.color = color;
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

    public float getThickness() {
        return thickness;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

}
