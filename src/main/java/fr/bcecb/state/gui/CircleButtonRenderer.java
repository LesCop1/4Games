package fr.bcecb.state.gui;

import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public class CircleButtonRenderer extends Renderer<CircleButton> {

    public CircleButtonRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(CircleButton button) {
        return button.getTexture();
    }

    @Override
    public void render(CircleButton button, float partialTick) {
        RenderEngine engine = renderManager.getRenderEngine();
        engine.drawCircle(getTexture(button), button.getX(), button.getY(), button.getRadius());
    }
}
