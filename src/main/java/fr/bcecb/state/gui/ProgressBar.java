package fr.bcecb.state.gui;

import com.google.common.base.MoreObjects;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.util.Constants;
import org.joml.Vector4f;

public class ProgressBar extends GuiElement {
    private float value = 0.00f;
    private float speed;
    private float offset;
    private Vector4f outlineColor;
    private Vector4f defaultColor;
    private Vector4f completedColor;
    private Vector4f textColor;

    public ProgressBar(int id, float x, float y, float width, float height, float speed, float offset) {
        this(id, x, y, width, height, speed, offset, null, null, null, null);
    }

    public ProgressBar(int id, float x, float y, float width, float height, float speed, float offset, Vector4f outlineColor, Vector4f defaultColor, Vector4f completedColor, Vector4f textColor) {
        super(id, x, y, width, height);
        this.speed = speed;
        this.offset = offset;
        this.outlineColor = MoreObjects.firstNonNull(outlineColor, Constants.COLOR_GREY);
        this.defaultColor = MoreObjects.firstNonNull(defaultColor, Constants.COLOR_WHITE);
        this.completedColor = MoreObjects.firstNonNull(completedColor, Constants.COLOR_GREEN);
        this.textColor = MoreObjects.firstNonNull(textColor, Constants.COLOR_BLACK);
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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String printValue() {
        int value = (int) (getValue() * 100);
        return value + " %";
    }

    public void progress() {
        if (this.value < 1.0f) {
            this.value += 0.001f * this.speed;
        } else if (this.value > 1.0f) {
            this.value = Math.round(this.value);
        }
    }

    public void reset() {
        this.value = 0f;
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public Vector4f getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(Vector4f outlineColor) {
        this.outlineColor = outlineColor;
    }

    public Vector4f getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Vector4f defaultColor) {
        this.defaultColor = defaultColor;
    }

    public Vector4f getCompletedColor() {
        return completedColor;
    }

    public void setCompletedColor(Vector4f completedColor) {
        this.completedColor = completedColor;
    }

    public Vector4f getTextColor() {
        return textColor;
    }

    public void setTextColor(Vector4f textColor) {
        this.textColor = textColor;
    }
}
