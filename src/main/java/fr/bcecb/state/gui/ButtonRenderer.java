package fr.bcecb.state.gui;

import com.google.common.base.Strings;
import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Constants;
import fr.bcecb.util.TransformStack;

public class ButtonRenderer extends Renderer<Button> {

    public ButtonRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(Button button) {
        return button.isHovered() ? button.getOnHoverTexture() : button.getTexture();
    }

    @Override
    public void render(Button button, float partialTick) {
        RenderEngine renderEngine = renderManager.getRenderEngine();
        TransformStack transform = renderEngine.getTransform();

        transform.pushTransform();
        {
            float hoverAnimationScale = button.isHovered() ? button.getHoverAnimation().getInterpolatedValue(partialTick) : 1.0f;

            transform.translate(button.getX(), button.getY());
            transform.translate(button.getWidth() / 2.0f, button.getHeight() / 2.0f);
            transform.scale(hoverAnimationScale, hoverAnimationScale);

            renderEngine.drawRect(getTexture(button), 0, 0, button.getWidth(), button.getHeight(), true);

            if (!Strings.isNullOrEmpty(button.getTitle())) {
                renderEngine.drawCenteredText(Constants.DEFAULT_FONT, button.getTitle(), 0, 0, button.getTitleScale());
            }
        }
        transform.popTransform();
    }
}
