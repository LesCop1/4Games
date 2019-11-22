package fr.bcecb.state.gui;

import fr.bcecb.state.StateManager;

public abstract class PopUpScreenState extends ScreenState {
    protected int backgroundWidth;
    protected int backgroundHeight;

    public PopUpScreenState(StateManager stateManager, String name) {
        super(stateManager, name);
    }

    public int getBackgroundWidth() {
        return backgroundWidth;
    }

    public int getBackgroundHeight() {
        return backgroundHeight;
    }
}
