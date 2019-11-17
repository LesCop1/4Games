package fr.bcecb.state.gui;

import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.TransformStack;

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
        TransformStack transform = engine.getTransform();

        float offset = slider.getHeight() / 4;

        transform.pushTransform();
        {
            transform.translate(slider.getX(), slider.getY());

            transform.pushTransform();
            {
                transform.color(slider.getOutsideColor());
                engine.drawRoundedRect(null, 0, offset, slider.getWidth(), slider.getHeight() - offset, Float.MAX_VALUE);
            }
            transform.popTransform();

            transform.pushTransform();
            {
                transform.color(slider.getButtonColor());
                engine.drawCircle(null, slider.getWidth() * slider.getValue(), 0, slider.getHeight() / 2);
            }
            transform.popTransform();
        }
        transform.popTransform();

    }
}
