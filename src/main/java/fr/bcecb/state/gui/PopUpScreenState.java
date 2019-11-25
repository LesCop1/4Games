package fr.bcecb.state.gui;

import fr.bcecb.state.StateManager;
import org.joml.Vector4f;

public abstract class PopUpScreenState extends ScreenState {
    protected int backgroundWidth;
    protected int backgroundHeight;
    protected Vector4f backgroundColor;

    public PopUpScreenState(StateManager stateManager, String name) {
        super(stateManager, name);
    }

    public int getBackgroundWidth() {
        return backgroundWidth;
    }

    public int getBackgroundHeight() {
        return backgroundHeight;
    }

    public Vector4f getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackground(int width, int height, Vector4f color) {
        super.setBackgroundTexture(null);
        this.backgroundWidth = width;
        this.backgroundHeight = height;
        this.backgroundColor = color;
    }
}
