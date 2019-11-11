package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public class ImageRenderer extends Renderer<Image> {

    public ImageRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(Image image) {
        return image.getImage();
    }

    @Override
    public void render(Image image, float partialTick) {
        if (image.keepRatio()) {
            float imageWidth = renderManager.getResourceManager().getResource(getTexture(image)).getWidth();
            float imageHeight = renderManager.getResourceManager().getResource(getTexture(image)).getHeight();

            float widthRatio = image.getWidth() / imageWidth;
            float heightRatio = image.getHeight() / imageHeight;
            float closestTo0Ratio = Math.abs(heightRatio) > Math.abs(widthRatio) ? widthRatio : heightRatio;
            float offsetW = image.getWidth() - (closestTo0Ratio * imageWidth);
            float offsetH = image.getHeight() - (closestTo0Ratio * imageHeight);

            renderManager.getRenderEngine().drawTexturedRect(getTexture(image), image.getX() + (offsetW / 2),
                    image.getY() + (offsetH / 2),
                    image.getX() + (offsetW / 2) + (closestTo0Ratio * imageWidth),
                    image.getY() + (offsetH / 2) + (closestTo0Ratio * imageHeight));

        } else {
            renderManager.getRenderEngine().drawTexturedRect(getTexture(image), image.getX(), image.getY(), image.getX() + image.getWidth(), image.getY() + image.getHeight());
        }
    }
}
