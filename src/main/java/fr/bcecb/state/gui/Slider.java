package fr.bcecb.state.gui;

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
