package fr.bcecb.render;

import fr.bcecb.resources.ResourceManager;

public abstract class Renderer<T extends IRenderable> {
    protected final RenderManager renderManager;

    public Renderer(RenderManager renderManager) {
        this.renderManager = renderManager;
    }

    public abstract void render(T object, double partialTick);
}
