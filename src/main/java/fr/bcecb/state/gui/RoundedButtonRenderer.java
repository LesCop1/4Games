package fr.bcecb.state.gui;

import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public class RoundedButtonRenderer extends Renderer<RoundedButton> {
    public RoundedButtonRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(RoundedButton roundedButton) {
        return roundedButton.isHovered() ? roundedButton.getOnHoverTexture() : roundedButton.getTexture();
    }

    @Override
    public void render(RoundedButton roundedButton, float partialTick) {
        RenderEngine engine = renderManager.getRenderEngine();

        engine.drawRoundedRect(getTexture(roundedButton), roundedButton.getX(), roundedButton.getY(), roundedButton.getWidth(), roundedButton.getHeight(), roundedButton.getRadius());
    }
}
