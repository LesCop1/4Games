package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Render;
import fr.bcecb.util.Transform;

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
        Transform transform = Render.pushTransform();
        {
            transform.translate(roundedButton.getX(), roundedButton.getY());
            renderManager.drawRoundedRect(getTexture(roundedButton), 0, 0, roundedButton.getWidth(), roundedButton.getHeight(), roundedButton.getRadius());
        }
        Render.popTransform();
    }
}
