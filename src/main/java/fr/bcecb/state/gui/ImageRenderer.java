package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Render;
import fr.bcecb.util.Resources;
import fr.bcecb.util.Transform;

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
        Transform transform = Render.pushTransform();
        {
            transform.translate(image.getX(), image.getY());
            transform.rotateZ((float) Math.toRadians(image.getRotation()));

            if (image.keepRatio()) {
                Texture texture = renderManager.getResourceManager().getResourceOrDefault(getTexture(image), Resources.DEFAULT_TEXTURE);
                float originalWidth = texture.getWidth();
                float originalHeight = texture.getHeight();
                float boundWidth = image.getWidth();
                float boundHeight = image.getHeight();
                float newWidth = originalWidth;
                float newHeight = originalHeight;

                if (originalWidth > boundWidth) {
                    newWidth = boundWidth;
                    newHeight = (newWidth * originalHeight) / originalWidth;
                }

                if (newHeight > boundHeight) {
                    newHeight = boundHeight;
                    newWidth = (newHeight * originalWidth) / originalHeight;
                }

                transform.translate((boundWidth - newWidth) / 2f, (boundHeight - newHeight) / 2f);
                renderManager.drawRect(getTexture(image), 0, 0, newWidth, newHeight);
            } else {
                renderManager.drawRect(getTexture(image), 0, 0, image.getWidth(), image.getHeight());
            }
        }
        Render.popTransform();
    }
}