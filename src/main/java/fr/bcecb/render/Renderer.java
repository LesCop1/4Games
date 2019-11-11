package fr.bcecb.render;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import org.joml.Matrix4f;

public abstract class Renderer<T extends IRenderable> {
    protected final RenderManager renderManager;

    public Renderer(RenderManager renderManager) {
        this.renderManager = renderManager;
    }

    public abstract ResourceHandle<Texture> getTexture(T object);

    public abstract void render(T object, Matrix4f transform, float partialTick);
}
