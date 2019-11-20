package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.RenderHelper;
import fr.bcecb.util.Transform;

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
        float offset = slider.getHeight() / 4;

        Transform transform = RenderHelper.pushTransform();
        {
            transform.translate(slider.getX(), slider.getY());

            RenderHelper.pushTransform();
            {
                transform.color(slider.getOutsideColor());
                renderManager.drawRoundedRect(null, 0, offset, slider.getWidth(), slider.getHeight() - offset, Float.MAX_VALUE);
            }
            RenderHelper.popTransform();

            RenderHelper.pushTransform();
            {
                transform.color(slider.getButtonColor());
                renderManager.drawCircle(null, slider.getWidth() * slider.getValue(), 0, slider.getHeight() / 2);
            }
            RenderHelper.popTransform();
        }
        RenderHelper.popTransform();

    }
}
