package fr.bcecb.state.gui;

import com.google.common.base.MoreObjects;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.util.Constants;
import fr.bcecb.util.MathHelper;
import org.joml.Vector4f;

public class ProgressBar extends GuiElement {
    private float value = 0.00f;
    private final float increment = 0.01f;
    private final float offset = 2f;
    private final Vector4f outlineColor;
    private final Vector4f defaultColor;
    private final Vector4f completedColor;
    private final Vector4f textColor;

    public ProgressBar(int id, float x, float y, float width, float height, boolean centered) {
        this(id, x, y, width, height, centered, null, null, null, null);
    }

    public ProgressBar(int id, float x, float y, float width, float height, boolean centered, Vector4f outlineColor, Vector4f defaultColor, Vector4f completedColor, Vector4f textColor) {
        super(id, x - (centered ? (width / 2) : 0), y - (centered ? (height / 2) : 0), width, height);
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
        this.value = MathHelper.clamp(this.value + this.increment, 0.0f, 1.0f);
    }

    public void reset() {
        this.value = 0f;
    }

    public float getIncrement() {
        return increment;
    }

    public float getOffset() {
        return offset;
    }

    public Vector4f getOutlineColor() {
        return outlineColor;
    }

    public Vector4f getDefaultColor() {
        return defaultColor;
    }

    public Vector4f getCompletedColor() {
        return completedColor;
    }

    public Vector4f getTextColor() {
        return textColor;
    }
}
