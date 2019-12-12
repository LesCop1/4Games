package fr.bcecb.state.gui;

import fr.bcecb.state.StateManager;
import org.joml.Vector4f;

public abstract class PopUpScreenState extends ScreenState {
    protected float backgroundWidth;
    protected float backgroundHeight;
    protected Vector4f backgroundColor;

    public PopUpScreenState(StateManager stateManager, String name, float width, float height, Vector4f color) {
        super(stateManager, name);
        this.backgroundWidth = width;
        this.backgroundHeight = height;
        setBackground(color);
    }

    public float getBackgroundWidth() {
        return backgroundWidth;
    }

    public float getBackgroundHeight() {
        return backgroundHeight;
    }

    public Vector4f getBackgroundColor() {
        return backgroundColor;
    }

    private void setBackground(Vector4f color) {
        super.setBackgroundTexture(null);
        this.backgroundColor = color;
    }

    @Override
    public boolean shouldRenderBelow() {
        return true;
    }
}
