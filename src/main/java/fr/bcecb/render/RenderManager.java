package fr.bcecb.render;

import fr.bcecb.resources.ResourceManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.ScreenStateRenderer;

import java.util.HashMap;
import java.util.Map;

public class RenderManager {
    private final RenderEngine renderEngine;
    private final ResourceManager resourceManager;
    private final Map<Class<? extends IRenderable>, Renderer<? extends IRenderable>> renderers = new HashMap<>();

    public RenderManager(RenderEngine renderEngine, ResourceManager resourceManager) {
        this.renderEngine = renderEngine;
        this.resourceManager = resourceManager;

        register(ScreenState.class, new ScreenStateRenderer(this));
        register(Button.class, new Button.ButtonRenderer(this));
        register(CircleButton.class, new CircleButton.CircleButtonRenderer(this));
    }

    private <T extends IRenderable> void register(Class<T> clazz, Renderer<T> renderer) {
        renderers.put(clazz, renderer);
    }

    public <T extends IRenderable, R extends Renderer<T>> R getRendererFor(T object) {
        return (R) getRendererFor(object.getClass());
    }

    private <T extends IRenderable, R extends Renderer<T>> R getRendererFor(Class<? extends IRenderable> clazz) {
        R renderer = (R) this.renderers.get(clazz);
        if (renderer == null && clazz != IRenderable.class) {
            renderer = this.getRendererFor((Class<? extends IRenderable>) clazz.getSuperclass());
            this.renderers.put(clazz, renderer);
        }

        return renderer;
    }

    public RenderEngine getRenderEngine() {
        return renderEngine;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }
}