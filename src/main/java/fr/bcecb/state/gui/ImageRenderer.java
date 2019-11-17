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

            if (image.keepRatio()) {
                float width = image.getWidth();
                float height = image.getHeight();
                float imageAspectRatio = width / height;

                Texture texture = renderManager.getResourceManager().getResourceOrDefault(getTexture(image), Resources.DEFAULT_TEXTURE);
                float imageWidth = texture.getWidth();
                float imageHeight = texture.getHeight();
                float textureAspectRatio = imageWidth / imageHeight;

                float aspectRatio = imageAspectRatio > textureAspectRatio ? width / imageWidth : height / imageHeight;

                transform.translate(width / 2.0f, height / 2.0f);
                transform.scale(aspectRatio, aspectRatio);

                renderManager.drawRect(getTexture(image), 0, 0, imageWidth, imageHeight, true);
            } else {
                renderManager.drawRect(getTexture(image), 0, 0, image.getWidth(), image.getHeight());
            }
        }
        Render.popTransform();
    }
}