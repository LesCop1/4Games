package fr.bcecb.state.gui;

import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public class SliderRenderer extends Renderer<Slider> {
    public SliderRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(Slider slider) {
        return null;
    }

    @Override
    public void render(Slider slider, float partialTick) {
        RenderEngine engine = renderManager.getRenderEngine();
        float quarter = slider.getHeight() / 4;
        engine.drawRoundedRect(null, slider.getX(), slider.getY() + quarter, slider.getWidth(), slider.getHeight() - (2 * quarter), Float.MAX_VALUE);
        engine.drawRoundedRect(null, slider.getX(), slider.getY(), slider.getHeight(), slider.getHeight(), Float.MAX_VALUE);
    }
}
