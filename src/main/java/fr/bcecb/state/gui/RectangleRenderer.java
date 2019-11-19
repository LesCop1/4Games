package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.RenderHelper;
import fr.bcecb.util.Transform;

public class RectangleRenderer extends Renderer<Rectangle> {
    public RectangleRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(Rectangle rectangle) {
        return null;
    }

    @Override
    public void render(Rectangle rectangle, float partialTick) {
        Transform transform = RenderHelper.pushTransform();
        {
            transform.translate(rectangle.getX(), rectangle.getY());
            transform.color(rectangle.getColor());
            renderManager.drawRect(getTexture(rectangle), 0, 0, rectangle.getWidth(), rectangle.getHeight());
        }
        RenderHelper.popTransform();
    }
}
