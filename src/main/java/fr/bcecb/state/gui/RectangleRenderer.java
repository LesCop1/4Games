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
            transform.translate(rect.getX(), rect.getY());
            transform.translate(rect.getWidth() / 2.0f, rect.getHeight() / 2.0f);
            transform.color(rect.getColor());
            engine.drawRect(null, 0, 0, rect.getWidth(), rect.getHeight(), true);
        }
        transform.popTransform();
    }
}
