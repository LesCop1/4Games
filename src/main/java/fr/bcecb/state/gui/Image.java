package fr.bcecb.state.gui;

import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public class Image extends GuiElement {
    private ResourceHandle<Texture> image;
    private boolean keepRatio;

    public Image(int id, ResourceHandle<Texture> image, float x, float y, float maxX, float maxY, boolean keepRatio) {
        super(id, x, y, maxX, maxY);
        this.image = image;
        this.keepRatio = keepRatio;
    }

    public ResourceHandle<Texture> getImage() {
        return image;
    }

    public void setImage(ResourceHandle<Texture> image) {
        this.image = image;
    }

    public boolean keepRatio() {
        return keepRatio;
    }

    public void setKeepRatio(boolean keepRatio) {
        this.keepRatio = keepRatio;
    }

    public static class ImageRenderer extends Renderer<Image> {

        public ImageRenderer(RenderManager renderManager) {
            super(renderManager);
        }

        @Override
        public ResourceHandle<Texture> getTexture(Image image) {
            return image.getImage();
        }

        @Override
        public void render(Image image, float partialTick) {
            RenderEngine renderEngine = renderManager.getRenderEngine();
            if (image.keepRatio()) {
                float imageWidth = renderManager.getResourceManager().getResource(getTexture(image)).getWidth();
                float imageHeight = renderManager.getResourceManager().getResource(getTexture(image)).getHeight();

                float windowWidth = Window.getCurrentWindow().getWidth();
                float windowHeight = Window.getCurrentWindow().getHeight();

                if (image.getWidth() == windowWidth && image.getHeight() == windowHeight) {
                    float widthRatio = windowWidth / imageWidth;
                    float heightRatio = windowHeight / imageHeight;

                    if (Math.abs(1 - heightRatio) > Math.abs(1 - widthRatio)) {
                        float offset = ((widthRatio * imageHeight) - windowHeight) / 2;
                        renderManager.getRenderEngine().drawTexturedRect(image.getImage(), 0, -offset,
                                widthRatio * imageWidth, windowHeight + offset);
                    } else {
                        float offset = ((heightRatio * imageWidth) - windowWidth) / 2;
                        renderManager.getRenderEngine().drawTexturedRect(image.getImage(), -offset, 0,
                                windowWidth + offset, heightRatio * imageHeight);
                    }
                } else {
                    float widthRatio = image.getWidth() / imageWidth;
                    float heightRatio = image.getHeight() / imageHeight;
                    float closestTo0Ratio = Math.abs(heightRatio) > Math.abs(widthRatio) ? widthRatio : heightRatio;
                    renderManager.getRenderEngine().drawTexturedRect(image.getImage(), image.getX(), image.getY(),
                            image.getX() + (closestTo0Ratio * imageWidth), image.getY() + (closestTo0Ratio * imageHeight));
                }
            } else {
                renderEngine.drawTexturedRect(getTexture(image), image.getX(), image.getY(), image.getX() + image.getWidth(), image.getY() + image.getHeight());
            }
        }
    }
}
