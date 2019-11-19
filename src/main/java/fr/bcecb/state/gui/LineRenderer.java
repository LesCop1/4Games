package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.RenderHelper;
import fr.bcecb.util.Transform;

public class LineRenderer extends Renderer<Line> {

    public LineRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(Line rect) {
        return null;
    }

    @Override
    public void render(Line line, float partialTick) {
        Transform transform = RenderHelper.pushTransform();
        {
            transform.color(line.getColor());
            renderManager.drawLine(line.getX(), line.getY(), line.getWidth(), line.getHeight(), line.getThickness());
        }
        RenderHelper.popTransform();
    }
}
