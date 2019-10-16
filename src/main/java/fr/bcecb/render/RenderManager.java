package fr.bcecb.render;

import fr.bcecb.resources.ResourceManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiRenderer;
import fr.bcecb.state.gui.GuiState;

import java.util.HashMap;
import java.util.Map;

public class RenderManager {
    private final ResourceManager resourceManager;
    private final Map<Class<?>, Renderer<? extends IRenderable>> renderers = new HashMap<>();

    public RenderManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;

        register(GuiState.class, new GuiRenderer(this));
        register(Button.class, new Button.ButtonRenderer(this));
    }

    @SuppressWarnings("unchecked")
    public <T extends IRenderable> Renderer<T> getRendererFor(T object) {
        return (Renderer<T>) renderers.entrySet().stream()
                .filter(e -> e.getKey().isAssignableFrom(object.getClass()))
                .map(Map.Entry::getValue)
                .findFirst().orElse(null);
    }

    private <T extends IRenderable> void register(Class<T> clazz, Renderer<T> renderer) {
        renderers.put(clazz, renderer);
    }

    private <T extends IRenderable> void unregister(Class<T> clazz) {
        renderers.remove(clazz);
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }
}