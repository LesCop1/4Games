package fr.bcecb.render;

import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiRenderer;
import fr.bcecb.state.gui.GuiState;

import java.util.HashMap;
import java.util.Map;

public class RenderManager {
    private static final Map<Class<?>, Renderer<? extends IRenderable>> RENDERERS = new HashMap<>();

    public static void init() {
        register(GuiState.class, new GuiRenderer());
        register(Button.class, new Button.ButtonRenderer());
    }

    @SuppressWarnings("unchecked")
    public static <T extends IRenderable> Renderer<T> getRendererFor(T object) {
        return (Renderer<T>) RENDERERS.entrySet().stream()
                .filter(e -> e.getKey().isAssignableFrom(object.getClass()))
                .map(Map.Entry::getValue)
                .findFirst().orElse(null);
    }

    private static <T extends IRenderable> void register(Class<T> clazz, Renderer<T> renderer) {
        RENDERERS.put(clazz, renderer);
    }

    private static <T extends IRenderable> void unregister(Class<T> clazz) {
        RENDERERS.remove(clazz);
    }
}