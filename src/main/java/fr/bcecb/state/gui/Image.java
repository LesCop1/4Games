package fr.bcecb.state.gui;

import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public class Image extends GuiElement {
    private ResourceHandle<Texture> image;
    private boolean keepRatio;

    public Image(int id, ResourceHandle<Texture> image, float x, float y, float maxX, float maxY, boolean keepRatio) {
        this(id, image, x, y, maxX, maxY, keepRatio, false);
    }

    public Image(int id, ResourceHandle<Texture> image, float x, float y, float maxX, float maxY, boolean keepRatio, boolean centered) {
        super(id, x - (centered ? (maxX / 2) : 0), y - (centered ? (maxY / 2) : 0), maxX, maxY);
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

    @Override
    public void onClick(MouseEvent.Click event) {

    }

    @Override
    public void onHover(MouseEvent.Move event) {

    }

    @Override
    public void onScroll(MouseEvent.Scroll event) {

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
            if (image.keepRatio()) {
                float imageWidth = renderManager.getResourceManager().getResource(getTexture(image)).getWidth();
                float imageHeight = renderManager.getResourceManager().getResource(getTexture(image)).getHeight();

                float widthRatio = image.getWidth() / imageWidth;
                float heightRatio = image.getHeight() / imageHeight;
                float closestTo0Ratio = Math.abs(heightRatio) > Math.abs(widthRatio) ? widthRatio : heightRatio;
                float offsetW = image.getWidth() - (closestTo0Ratio * imageWidth);
                float offsetH = image.getHeight() - (closestTo0Ratio * imageHeight);

                renderManager.getRenderEngine().drawTexturedRect(image.getX() + (offsetW / 2),
                        image.getY() + (offsetH / 2),
                        image.getX() + (offsetW / 2) + (closestTo0Ratio * imageWidth),
                        image.getY() + (offsetH / 2) + (closestTo0Ratio * imageHeight), getTexture(image));

            } else {
                renderManager.getRenderEngine().drawTexturedRect(image.getX(), image.getY(), image.getX() + image.getWidth(), image.getY() + image.getHeight(), getTexture(image));
            }
        }
    }
}
