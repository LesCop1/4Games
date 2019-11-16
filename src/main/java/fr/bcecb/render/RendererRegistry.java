package fr.bcecb.render;

import fr.bcecb.resources.ResourceManager;
import fr.bcecb.state.gui.*;

import java.util.HashMap;
import java.util.Map;

public class RendererRegistry {
    private final ResourceManager resourceManager;
    private final Map<Class<? extends IRenderable>, Renderer<? extends IRenderable>> renderers = new HashMap<>();

    public RendererRegistry(RenderManager renderManager, ResourceManager resourceManager) {
        this.resourceManager = resourceManager;

        registerRenderer(ScreenState.class, new ScreenStateRenderer(renderManager));
        registerRenderer(CircleButton.class, new CircleButtonRenderer(renderManager));
        registerRenderer(Image.class, new ImageRenderer(renderManager));
        registerRenderer(CircleImage.class, new CircleImageRenderer(renderManager));
        registerRenderer(Text.class, new TextRenderer(renderManager));
        registerRenderer(Line.class, new LineRenderer(renderManager));
        registerRenderer(Button.class, new ButtonRenderer(renderManager));
    }

    private <T extends IRenderable> void registerRenderer(Class<T> clazz, Renderer<T> renderer) {
        renderers.put(clazz, renderer);
    }

    public <T extends IRenderable, R extends Renderer<T>> R getRendererFor(T object) {
        return (R) getRendererFor(object.getClass());
    }

    public <T extends IRenderable, R extends Renderer<T>> R getRendererFor(Class<? extends IRenderable> clazz) {
        R renderer = (R) this.renderers.get(clazz);
        if (renderer == null && clazz != IRenderable.class) {
            renderer = this.getRendererFor((Class<? extends IRenderable>) clazz.getSuperclass());
            this.renderers.put(clazz, renderer);
        }

        return renderer;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }
}