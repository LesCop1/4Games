package fr.bcecb.render;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public abstract class Renderer<T extends IRenderable> {
    protected final RenderManager renderManager;

    public Renderer(RenderManager renderManager) {
        this.renderManager = renderManager;
    }

    public abstract ResourceHandle<Texture> getTexture(T object);

    public abstract void render(T object, float partialTick);
}
