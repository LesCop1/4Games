package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.RenderHelper;
import fr.bcecb.util.Resources;
import fr.bcecb.util.Transform;

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
        Transform transform = RenderHelper.pushTransform();
        {
            transform.translate(roundedImage.getX(), roundedImage.getY());

            if (roundedImage.keepRatio()) {
                Texture texture = renderManager.getResourceManager().getResourceOrDefault(getTexture(roundedImage), Resources.DEFAULT_TEXTURE);
                float originalWidth = texture.getWidth();
                float originalHeight = texture.getHeight();
                float boundWidth = roundedImage.getWidth();
                float boundHeight = roundedImage.getHeight();
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
                renderManager.drawRoundedRect(getTexture(roundedImage), 0, 0, newWidth, newHeight, roundedImage.getRadius());
            } else {
                renderManager.drawRoundedRect(getTexture(roundedImage), 0, 0, roundedImage.getWidth(), roundedImage.getHeight(), roundedImage.getRadius());
            }
        }
        RenderHelper.popTransform();
    }
}
