package fr.bcecb.state.gui;

import fr.bcecb.state.State;

public class GuiState extends State {
    protected GuiState(String name) {
        super(name);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void update() {

    }

    @Override
    public boolean shouldRenderBelow() {
        return false;
    }

    @Override
    public boolean shouldUpdateBelow() {
        return false;
    }
}
