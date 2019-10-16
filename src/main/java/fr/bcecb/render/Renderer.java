package fr.bcecb.render;

public abstract class Renderer<T extends IRenderable> {
    public abstract void render(T object, double partialTick);
}
