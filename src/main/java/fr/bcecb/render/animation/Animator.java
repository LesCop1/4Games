package fr.bcecb.render.animation;

import fr.bcecb.render.RenderManager;
import org.joml.Matrix4f;

public abstract class Animator<T extends IAnimatable> {
    protected final RenderManager renderManager;

    public Animator(RenderManager renderManager) {
        this.renderManager = renderManager;
    }

    public abstract void animate(T object, Matrix4f transform, float partialTick);
}