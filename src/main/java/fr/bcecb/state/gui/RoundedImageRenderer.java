package fr.bcecb.state.gui;

import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Resources;
import fr.bcecb.util.TransformStack;

public class RoundedImageRenderer extends Renderer<RoundedImage> {
    public RoundedImageRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(RoundedImage roundedImage) {
        return roundedImage.getImage();
    }

    @Override
    public void render(RoundedImage roundedImage, float partialTick) {
        RenderEngine engine = renderManager.getRenderEngine();
        TransformStack transform = engine.getTransform();

        transform.pushTransform();
        {
            transform.translate(roundedImage.getX(), roundedImage.getY());
            if (roundedImage.keepRatio()) {
                float width = roundedImage.getWidth();
                float height = roundedImage.getHeight();
                float imageAspectRatio = width / height;

                Texture texture = renderManager.getResourceManager().getResourceOrDefault(getTexture(roundedImage), Resources.DEFAULT_TEXTURE);
                float imageWidth = texture.getWidth();
                float imageHeight = texture.getHeight();
                float textureAspectRatio = imageWidth / imageHeight;

                float aspectRatio = imageAspectRatio > textureAspectRatio ? width / imageWidth : height / imageHeight;

                transform.translate(width / 2.0f, height / 2.0f);
                transform.scale(aspectRatio, aspectRatio);

                engine.drawRoundedRect(getTexture(roundedImage), 0, 0, imageWidth, imageHeight, roundedImage.getRadius());
            } else {
                engine.drawRoundedRect(getTexture(roundedImage), 0, 0, roundedImage.getWidth(), roundedImage.getHeight(), roundedImage.getRadius());
            }
        }
    }
}
