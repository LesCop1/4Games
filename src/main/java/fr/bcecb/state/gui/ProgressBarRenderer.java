package fr.bcecb.state.gui;

import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Resources;
import fr.bcecb.util.TransformStack;

public class ProgressBarRenderer extends Renderer<ProgressBar> {
    public ProgressBarRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(ProgressBar progressBar) {
        return null;
    }

    @Override
    public void render(ProgressBar progressBar, float partialTick) {
        RenderEngine engine = renderManager.getRenderEngine();
        TransformStack transform = engine.getTransform();

        transform.pushTransform();
        {
            transform.translate(progressBar.getX(), progressBar.getY());

            transform.pushTransform();
            {
                transform.color(progressBar.getOutlineColor());
                engine.drawRoundedRect(null, 0, 0, progressBar.getWidth(), progressBar.getHeight(), Float.MAX_VALUE);
            }
            transform.popTransform();

            transform.translate(progressBar.getOffset(), progressBar.getOffset());

            if (progressBar.getValue() != 1.0f) {
                transform.pushTransform();
                {
                    transform.color(progressBar.getDefaultColor());
                    engine.drawRoundedRect(null, 0, 0,
                            progressBar.getWidth() - (2 * progressBar.getOffset()),
                            progressBar.getHeight() - (2 * progressBar.getOffset()), Float.MAX_VALUE);
                }
                transform.popTransform();
            }

            if (progressBar.getValue() != 0.0f) {
                transform.pushTransform();
                {
                    transform.color(progressBar.getCompletedColor());
                    engine.drawRoundedRect(null, 0, 0,
                            (progressBar.getWidth() - (2 * progressBar.getOffset())) * progressBar.getValue(),
                            progressBar.getHeight() - (2 * progressBar.getOffset()), Float.MAX_VALUE);
                }
                transform.popTransform();
            }

            transform.pushTransform();
            {
                transform.translate((progressBar.getWidth() - (2 * progressBar.getOffset())) / 2, (progressBar.getHeight() - (2 * progressBar.getOffset())) / 2);
                transform.color(progressBar.getTextColor());
                engine.drawCenteredText(Resources.DEFAULT_FONT, progressBar.printValue(), 0, 0, 0.5f);
            }
            transform.popTransform();
        }
        transform.popTransform();
    }
}
