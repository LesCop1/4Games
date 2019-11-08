package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public class CircleImage extends Image {
    private float radius;

    public CircleImage(int id, ResourceHandle<Texture> image, float x, float y, float radius) {
        super(id, image, x, y, radius * 2, radius * 2, false);
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public static class CircleImageRenderer extends Renderer<CircleImage> {
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
}
