package fr.bcecb.state.gui;

import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.TransformStack;

public class RectangleRenderer extends Renderer<Rectangle> {

    public RectangleRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(Rectangle rect) {
        return null;
    }

    @Override
    public void render(Rectangle rect, float partialTick) {
        RenderEngine engine = renderManager.getRenderEngine();
        TransformStack transform = engine.getTransform();

        transform.pushTransform();
        {
            transform.color(rect.getColor());
            engine.drawRect(null, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
        transform.popTransform();
    }
}
