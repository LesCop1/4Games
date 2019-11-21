package fr.bcecb.state;

import fr.bcecb.render.IRenderable;

public abstract class State implements IRenderable {
    protected final StateManager stateManager;

    private final String name;

    protected State(StateManager stateManager, String name) {
        this.stateManager = stateManager;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void onEnter();

    public abstract void onExit();

    public abstract void onUpdate();

    public abstract boolean shouldRenderBelow();

    public abstract boolean shouldPauseBelow();
}