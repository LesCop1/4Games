package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.RenderHelper;
import fr.bcecb.util.Transform;

public class RoundedRectangleRenderer extends Renderer<RoundedRectangle> {
    public RoundedRectangleRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(RoundedRectangle roundedRectangle) {
        return null;
    }

    @Override
    public void render(RoundedRectangle roundedRectangle, float partialTick) {
        Transform transform = RenderHelper.pushTransform();
        {
            transform.translate(roundedRectangle.getX(), roundedRectangle.getY());
            transform.color(roundedRectangle.getColor());
            renderManager.drawRoundedRect(null, 0, 0, roundedRectangle.getWidth(), roundedRectangle.getHeight(), roundedRectangle.getRadius());
        }
        RenderHelper.popTransform();
    }
}
