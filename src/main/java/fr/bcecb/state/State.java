package fr.bcecb.state;

import fr.bcecb.render.IRenderable;

public abstract class State implements IRenderable {
    private final String name;

    protected State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void onEnter();

    public abstract void onExit();

    public abstract void onUpdate();

    public abstract boolean shouldRenderBelow();

    public abstract boolean shouldUpdateBelow();

}