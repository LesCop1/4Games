package fr.bcecb.render;

import fr.bcecb.resources.ResourceManager;
import fr.bcecb.state.gui.*;

import java.util.HashMap;
import java.util.Map;

public class RendererRegistry {
    private final ResourceManager resourceManager;
    private final Map<Class<? extends IRenderable>, Renderer> renderers = new HashMap<>();

    public RendererRegistry(RenderManager renderManager, ResourceManager resourceManager) {
        this.resourceManager = resourceManager;

        registerRenderer(ScreenState.class, new ScreenStateRenderer(renderManager));
        registerRenderer(Button.class, new ButtonRenderer(renderManager));
        registerRenderer(CircleButton.class, new CircleButtonRenderer(renderManager));
        registerRenderer(RoundedButton.class, new RoundedButtonRenderer(renderManager));
        registerRenderer(Image.class, new ImageRenderer(renderManager));
        registerRenderer(CircleImage.class, new CircleImageRenderer(renderManager));
        registerRenderer(RoundedImage.class, new RoundedImageRenderer(renderManager));
        registerRenderer(Text.class, new TextRenderer(renderManager));
        registerRenderer(Line.class, new LineRenderer(renderManager));
        registerRenderer(Rectangle.class, new RectangleRenderer(renderManager));
        registerRenderer(RoundedRectangle.class, new RoundedRectangleRenderer(renderManager));
        registerRenderer(Slider.class, new SliderRenderer(renderManager));
        registerRenderer(ProgressBar.class, new ProgressBarRenderer(renderManager));
    }

    private <T extends IRenderable> void registerRenderer(Class<T> clazz, Renderer renderer) {
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