package fr.bcecb.state.gui;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.RenderHelper;
import fr.bcecb.util.Transform;

public class RoundedButtonRenderer extends Renderer<RoundedButton> {
    public RoundedButtonRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(RoundedButton roundedButton) {
        if (roundedButton.isDisabled()) {
            return MoreObjects.firstNonNull(roundedButton.getDisabledTexture(), roundedButton.getTexture());
        } else if (roundedButton.isHovered()) {
            return MoreObjects.firstNonNull(roundedButton.getHoverTexture(), roundedButton.getTexture());
        } else {
            return roundedButton.getTexture();
        }
    }

    @Override
    public void render(RoundedButton roundedButton, float partialTick) {
        Transform transform = RenderHelper.pushTransform();
        {
            transform.translate(roundedButton.getX(), roundedButton.getY());
            renderManager.drawRoundedRect(getTexture(roundedButton), 0, 0, roundedButton.getWidth(), roundedButton.getHeight(), roundedButton.getRadius());

            if (!Strings.isNullOrEmpty(roundedButton.getTitle())) {
                RenderHelper.pushTransform();
                {
                    transform.translate(roundedButton.getWidth() / 2, roundedButton.getHeight() / 2);
                    transform.color(roundedButton.getTitleColor());

                    if (roundedButton.getTitleScale() != 0.0f) {
                        renderManager.getFontRenderer().drawString(roundedButton.getTitle(), 0, 0, roundedButton.getTitleScale(), true);
                    } else {
                        renderManager.getFontRenderer().drawStringBoxed(roundedButton.getTitle(), 0, 0, roundedButton.getWidth(), roundedButton.getHeight());
                    }
                }
                RenderHelper.popTransform();
            }
        }
        RenderHelper.popTransform();
    }
}
