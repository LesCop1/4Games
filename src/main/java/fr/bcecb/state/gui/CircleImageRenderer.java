package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public class CircleImageRenderer extends Renderer<CircleImage> {
    public CircleImageRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(CircleImage circleImage) {
        return circleImage.getImage();
    }

    @Override
    public void render(CircleImage circleImage, float partialTick) {
        renderManager.getRenderEngine().drawTexturedCircle(getTexture(circleImage), circleImage.getX(), circleImage.getY(), circleImage.getRadius());
    }
}
