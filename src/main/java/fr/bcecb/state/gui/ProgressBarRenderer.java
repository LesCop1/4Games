package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Render;
import fr.bcecb.util.Transform;

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
        Transform transform = Render.pushTransform();
        {
            transform.translate(progressBar.getX(), progressBar.getY());

            Render.pushTransform();
            {
                transform.color(progressBar.getOutlineColor());
                renderManager.drawRoundedRect(null, 0, 0, progressBar.getWidth(), progressBar.getHeight(), Float.MAX_VALUE);
            }
            Render.popTransform();

            transform.translate(progressBar.getOffset(), progressBar.getOffset());

            if (progressBar.getValue() != 1.0f) {
                Render.pushTransform();
                {
                    transform.color(progressBar.getDefaultColor());
                    renderManager.drawRoundedRect(null, 0, 0,
                            progressBar.getWidth() - (2 * progressBar.getOffset()),
                            progressBar.getHeight() - (2 * progressBar.getOffset()), Float.MAX_VALUE);
                }
                Render.popTransform();
            }

            if (progressBar.getValue() != 0.0f) {
                Render.pushTransform();
                {
                    transform.color(progressBar.getCompletedColor());
                    renderManager.drawRoundedRect(null, 0, 0,
                            (progressBar.getWidth() - (2 * progressBar.getOffset())) * progressBar.getValue(),
                            progressBar.getHeight() - (2 * progressBar.getOffset()), Float.MAX_VALUE);
                }
                Render.popTransform();
            }

            Render.pushTransform();
            {
                transform.translate((progressBar.getWidth() - (2 * progressBar.getOffset())) / 2, (progressBar.getHeight() - (2 * progressBar.getOffset())) / 2);
                transform.color(progressBar.getTextColor());
                renderManager.getFontRenderer().drawStringBoxed(progressBar.printValue(), 0, 0, progressBar.getWidth(), progressBar.getHeight());
            }
            Render.popTransform();
        }
        Render.popTransform();
    }
}
