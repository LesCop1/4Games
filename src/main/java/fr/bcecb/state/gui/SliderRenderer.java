package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Render;
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

        Transform transform = Render.pushTransform();
        {
            transform.translate(slider.getX(), slider.getY());

            Render.pushTransform();
            {
                transform.color(slider.getOutsideColor());
                renderManager.drawRoundedRect(null, 0, offset, slider.getWidth(), slider.getHeight() - offset, Float.MAX_VALUE);
            }
            Render.popTransform();

            Render.pushTransform();
            {
                transform.color(slider.getButtonColor());
                renderManager.drawCircle(null, slider.getWidth() * slider.getValue(), 0, slider.getHeight() / 2);
            }
            Render.popTransform();
        }
        Render.popTransform();

    }
}
