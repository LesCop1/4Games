package fr.bcecb.state.gui;

import fr.bcecb.event.MouseEvent;
import fr.bcecb.util.Constants;
import org.joml.Vector4f;

public class Slider extends GuiElement {
    private float value = 0.0f;
    private Vector4f outsideColor = Constants.COLOR_GREY;
    private Vector4f buttonColor = Constants.COLOR_GREEN;

    public Slider(int id, float x, float y, float width, float height) {
        super(id, x, y, width, height);
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onClick(MouseEvent.Click event) {
        float xMinValue = getX() + (getHeight() * getValue());
        float yMinValue = getY();
        float xMaxValue = xMinValue + getHeight();
        float yMaxValue = yMinValue + getHeight();

        float xEvent = event.getX();
        float yEvent = event.getY();

        if (xMinValue < xEvent && xEvent < xMaxValue &&
            yMinValue < yEvent && yEvent < yMaxValue) {
            System.out.println("e");
        }
    }

    @Override
    public void onHover(MouseEvent.Move event) {

    }

    @Override
    public void onScroll(MouseEvent.Scroll event) {

    }

    public float getValue() {
        return value;
    }

    public Vector4f getOutsideColor() {
        return outsideColor;
    }

    public Vector4f getButtonColor() {
        return buttonColor;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
